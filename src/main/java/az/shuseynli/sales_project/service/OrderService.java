package az.shuseynli.sales_project
.service;

import az.shuseynli.sales_project
.model.dto.order.*;
import org.springframework.data.domain.Page;

public interface OrderService {
   OrderResponse createOnlineOrder(OnlineOrderRequest request);
   OrderResponse createOfflineOrder(OfflineOrderRequest request);
   OrderFullResponse getOrderById(Long id);
   void cancelOrder(Long id);
   void shipOrder(Long id);
   void completeOrder(Long id);

   Page<OrderResponse> searchOrders(SearchOrder filter);
}