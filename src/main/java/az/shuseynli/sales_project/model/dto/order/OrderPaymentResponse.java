package az.shuseynli.sales_project
.model.dto.order;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderPaymentResponse {
   private Long id;
   private BigDecimal amount;
   private String method;
   private String status;
   private LocalDateTime paymentDate;
   private String transactionId;
   private String branchId;
   private String cashRegisterId;
}