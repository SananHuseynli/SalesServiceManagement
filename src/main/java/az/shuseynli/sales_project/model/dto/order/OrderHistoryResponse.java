package az.shuseynli.sales_project
.model.dto.order;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class OrderHistoryResponse {
   private Long id;
   private String status;
   private String notes;
   private LocalDateTime createdAt;
}