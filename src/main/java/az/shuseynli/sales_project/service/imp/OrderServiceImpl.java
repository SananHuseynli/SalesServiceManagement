package az.shuseynli.sales_project
.service.imp;

import az.shuseynli.sales_project
.exception.custom.CustomException;
import az.shuseynli.sales_project
.exception.constants.ErrorCodes;
import az.shuseynli.sales_project
.mapper.OrderMapper;
import az.shuseynli.sales_project
.model.dto.order.*;
import az.shuseynli.sales_project
.model.dto.product.ProductStockCheckResponse;
import az.shuseynli.sales_project
.model.entity.Customer;
import az.shuseynli.sales_project
.model.entity.Order;
import az.shuseynli.sales_project
.model.entity.Product;
import az.shuseynli.sales_project
.model.enums.Channel;
import az.shuseynli.sales_project
.model.enums.OrderStatus;
import az.shuseynli.sales_project
.repository.CustomerRepository;
import az.shuseynli.sales_project
.repository.OrderRepository;
import az.shuseynli.sales_project
.repository.ProductRepository;
import az.shuseynli.sales_project
.service.HistoryService;
import az.shuseynli.sales_project
.service.OrderService;
import az.shuseynli.sales_project
.service.ProductService;
import az.shuseynli.sales_project
.service.RedisService;
import az.shuseynli.sales_project
.specifications.OrderSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static az.shuseynli.sales_project
.util.PageableCheckUtil.getPageable;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

   private final OrderRepository orderRepository;
   private final CustomerRepository customerRepository;
   private final ProductRepository productRepository;
   private final ProductService productService;
   private final HistoryService historyService;
   private final RedisService redisService;
   private final OrderMapper orderMapper;

   @Override
   @Transactional
   public OrderResponse createOnlineOrder(OnlineOrderRequest request) {

      if (redisService.exists(request.getIdempotencyKey())) {
         return (OrderResponse) redisService.get(request.getIdempotencyKey());
      }

      Customer customer = customerRepository.findById(request.getCustomerId())
              .orElseThrow(() -> new CustomException(ErrorCodes.CUSTOMER_NOT_FOUND, request.getCustomerId()));

      return processOrderCreation(Channel.ONLINE, customer, request.getProductIds(), request.getIdempotencyKey());
   }

   @Override
   @Transactional
   public OrderResponse createOfflineOrder(OfflineOrderRequest request) {

      if (redisService.exists(request.getIdempotencyKey())) {
         return (OrderResponse) redisService.get(request.getIdempotencyKey());
      }

      Customer customer = null;
      if (request.getCustomerId() != null) {
         customer = customerRepository.findById(request.getCustomerId())
                 .orElseThrow(() -> new CustomException(ErrorCodes.CUSTOMER_NOT_FOUND, request.getCustomerId()));
      }

      return processOrderCreation(Channel.OFFLINE, customer, request.getProductIds(), request.getIdempotencyKey());
   }

   private OrderResponse processOrderCreation(Channel channel, Customer customer, List<Long> productIds, String idempotencyKey) {
      Map<Long, Long> productQuantities = productIds.stream()
              .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

      for (Map.Entry<Long, Long> entry : productQuantities.entrySet()) {
         ProductStockCheckResponse check = productService.checkStockAndStatus(entry.getKey(), entry.getValue().intValue());
         if (!check.isSalable()) {
            throw new CustomException(ErrorCodes.PRODUCT_NOT_AVAILABLE, entry.getKey());
         }
      }

      for (Map.Entry<Long, Long> entry : productQuantities.entrySet()) {
         productService.decreaseStockQuantity(entry.getKey(), entry.getValue().intValue());
      }

      List<Product> products = productRepository.findAllById(productQuantities.keySet());
      BigDecimal totalAmount = BigDecimal.ZERO;
      for (Product product : products) {
         int qty = productQuantities.get(product.getId()).intValue();
         totalAmount = totalAmount.add(product.getPrice().multiply(BigDecimal.valueOf(qty)));
      }

      Order order = new Order();
      order.setChannel(channel);
      order.setTotalAmount(totalAmount);
      order.setCurrentStatus(OrderStatus.CREATED);
      order.setCustomer(customer);
      order.setProducts(new LinkedHashSet<>(products));
      order.setIdempotencyKey(idempotencyKey);

      Order savedOrder = orderRepository.save(order);

      historyService.saveHistory(savedOrder, "Sifariş uğurla formalaşdırıldı (İlkin qeydiyyat).");

      OrderResponse response = orderMapper.toResponse(savedOrder);
      redisService.saveWithTimeout(idempotencyKey, response, 24);

      return response;
   }

   @Override
   @Transactional(readOnly = true)
   public OrderFullResponse getOrderById(Long id) {
      Order order = orderRepository.findById(id)
              .orElseThrow(() -> new CustomException(ErrorCodes.ORDER_NOT_FOUND, id));
      return orderMapper.toFullResponse(order);
   }

   @Override
   @Transactional
   public void cancelOrder(Long id) {
      Order order = orderRepository.findById(id)
              .orElseThrow(() -> new CustomException(ErrorCodes.ORDER_NOT_FOUND, id));

      if (order.getCurrentStatus() == OrderStatus.CANCELLED || order.getCurrentStatus() == OrderStatus.COMPLETED) {
         throw new CustomException(ErrorCodes.INVALID_STATUS_TRANSITION, id);
      }

      Map<Long, Integer> productCounts = new HashMap<>();
      for (Product product : order.getProducts()) {
         productCounts.put(product.getId(), productCounts.getOrDefault(product.getId(), 0) + 1);
      }

      for (Map.Entry<Long, Integer> entry : productCounts.entrySet()) {
         productService.increaseStockQuantity(entry.getKey(), entry.getValue());
      }

      order.setCurrentStatus(OrderStatus.CANCELLED);
      Order savedOrder = orderRepository.save(order);

      historyService.saveHistory(savedOrder, "Sifariş istifadəçi/sistem tərəfindən ləğv edildi. Məhsullar anbara geri qaytarıldı.");
   }

   @Override
   @Transactional
   public void shipOrder(Long id) {
      Order order = orderRepository.findById(id)
              .orElseThrow(() -> new CustomException(ErrorCodes.ORDER_NOT_FOUND, id));

      if (order.getCurrentStatus() != OrderStatus.PAID && order.getCurrentStatus() != OrderStatus.PROCESSING) {
         throw new CustomException(ErrorCodes.INVALID_STATUS_TRANSITION, id);
      }

      order.setCurrentStatus(OrderStatus.DELIVERING);
      Order savedOrder = orderRepository.save(order);

      historyService.saveHistory(savedOrder, "Sifariş logistika/kuryer xidmətinə təhvil verildi. Daşınma mərhələsindədir.");
   }

   @Override
   @Transactional
   public void completeOrder(Long id) {
      Order order = orderRepository.findById(id)
              .orElseThrow(() -> new CustomException(ErrorCodes.ORDER_NOT_FOUND, id));

      if (order.getCurrentStatus() != OrderStatus.DELIVERING) {
         throw new CustomException(ErrorCodes.INVALID_STATUS_TRANSITION, id);
      }

      order.setCurrentStatus(OrderStatus.COMPLETED);
      Order savedOrder = orderRepository.save(order);

      historyService.saveHistory(savedOrder, "Sifariş müştəriyə təhvil verildi və uğurla tamamlandı.");
   }

   @Override
   @Transactional(readOnly = true)
   public Page<OrderResponse> searchOrders(SearchOrder filter) {
      OrderSpecification spec = new OrderSpecification(filter);

      Pageable pageable = getPageable(filter.getPage(),  filter.getSize());

      Page<Order> orderPage = orderRepository.findAll(spec, pageable);

      return orderPage.map(orderMapper::toResponse);
   }


}