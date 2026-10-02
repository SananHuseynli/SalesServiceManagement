package az.shuseynli.sales_project
.model.dto.order;

import az.shuseynli.sales_project
.exception.constants.ValidationConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import java.util.List;

@Data
public class OfflineOrderRequest {
   private Long customerId;

   @NotEmpty(message = ValidationConstants.ORDER_PRODUCTS_NOT_EMPTY)
   private List<Long> productIds;

   @NotBlank(message = ValidationConstants.ORDER_IDEMPOTENCY_NOT_BLANK)
   private String idempotencyKey;

}