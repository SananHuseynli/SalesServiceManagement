package az.shuseynli.sales_project
.service.imp;

import az.shuseynli.sales_project
.exception.custom.CustomException;
import az.shuseynli.sales_project
.exception.constants.ErrorCodes;
import az.shuseynli.sales_project
.model.dto.payment.PaymentRequest;
import az.shuseynli.sales_project
.model.entity.Order;
import az.shuseynli.sales_project
.model.entity.Payment;
import az.shuseynli.sales_project
.model.enums.OrderStatus;
import az.shuseynli.sales_project
.model.enums.PaymentStatus;
import az.shuseynli.sales_project
.repository.OrderRepository;
import az.shuseynli.sales_project
.repository.PaymentRepository;
import az.shuseynli.sales_project
.service.HistoryService;
import az.shuseynli.sales_project
.service.PaymentService;
import az.shuseynli.sales_project
.service.RedisService;
import az.shuseynli.sales_project
.service.payment_strategy.PaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

   private final OrderRepository orderRepository;
   private final PaymentRepository paymentRepository;
   private final HistoryService historyService;
   private final RedisService redisService;
   private final List<PaymentStrategy> paymentStrategies;

   @Override
   @Transactional
   public void executePayment(PaymentRequest request) {

      Order order = orderRepository.findById(request.getOrderId())
              .orElseThrow(() -> new CustomException(ErrorCodes.ORDER_NOT_FOUND, request.getOrderId()));

      if (order.getCurrentStatus() == OrderStatus.PAID) {
         throw new CustomException(ErrorCodes.ORDER_ALREADY_PAID, request.getOrderId());
      }

      PaymentStrategy strategy = paymentStrategies.stream()
              .filter(s -> s.getSupportedMethod() == request.getPaymentMethod())
              .findFirst()
              .orElseThrow(() -> new CustomException(ErrorCodes.STRATEGY_NOT_FOUND));

      Payment payment = new Payment();
      payment.setAmount(order.getTotalAmount());
      payment.setMethod(request.getPaymentMethod());
      payment.setStatus(PaymentStatus.PENDING);
      payment.setTransactionId(request.getIdempotencyKey());
      payment.setBranchId(request.getBranchId());
      payment.setCashRegisterId(request.getCashRegisterId());

      Payment savedPayment = paymentRepository.save(payment);
      order.setPayment(savedPayment);

      try {
         strategy.process(savedPayment);

         savedPayment.setStatus(PaymentStatus.COMPLETED);
         order.setCurrentStatus(OrderStatus.PAID);
         orderRepository.save(order);

         historyService.saveHistory(order, "Sifariş ödənişi uğurla tamamlandı. Üsul: " + request.getPaymentMethod());

         redisService.delete(request.getIdempotencyKey());

      } catch (Exception e) {
         savedPayment.setStatus(PaymentStatus.FAILED);
         order.setCurrentStatus(OrderStatus.PENDING_PAYMENT);
         orderRepository.save(order);

         historyService.saveHistory(order, "Sifariş ödənişi uğursuz oldu: " + e.getMessage());
         throw new CustomException(ErrorCodes.PAYMENT_FAILED);
      }
   }
}