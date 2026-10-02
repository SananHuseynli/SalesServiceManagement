package az.shuseynli.sales_project
.model.dto.order;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderFullResponse {
   private Long id;
   private String channel;
   private BigDecimal totalAmount;
   private String currentStatus;
   private LocalDateTime createdAt;
   private LocalDateTime updatedAt;
   private String idempotencyKey;
   private OrderCustomerResponse customer;
   private List<OrderProductResponse> products;
   private OrderPaymentResponse payment;
   private List<OrderHistoryResponse> histories;
}