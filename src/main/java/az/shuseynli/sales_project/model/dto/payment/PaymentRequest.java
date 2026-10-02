package az.shuseynli.sales_project
.model.dto.payment;

import az.shuseynli.sales_project
.exception.constants.ValidationConstants;
import az.shuseynli.sales_project
.model.enums.PaymentMethod;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PaymentRequest {

   @NotNull(message = ValidationConstants.PAYMENT_ORDER_ID_NOT_NULL)
   private Long orderId;

   @NotNull(message = ValidationConstants.PAYMENT_METHOD_NOT_NULL)
   private PaymentMethod paymentMethod;

   @NotBlank(message = ValidationConstants.PAYMENT_IDEMPOTENCY_NOT_BLANK)
   private String idempotencyKey;

   private String branchId;
   private String cashRegisterId;
}