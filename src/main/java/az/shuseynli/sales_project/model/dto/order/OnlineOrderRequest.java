package az.shuseynli.sales_project
.model.dto.order;

import az.shuseynli.sales_project
.exception.constants.ValidationConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;

@Data
public class OnlineOrderRequest {
   @NotNull(message = ValidationConstants.ORDER_CUSTOMER_NOT_NULL)
   private Long customerId;

   @NotEmpty(message = ValidationConstants.ORDER_PRODUCTS_NOT_EMPTY)
   private List<Long> productIds;

   @NotBlank(message = ValidationConstants.ORDER_IDEMPOTENCY_NOT_BLANK)
   private String idempotencyKey;
}