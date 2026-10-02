package az.shuseynli.sales_project
.exception.constants;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCodes {

   CUSTOMER_NOT_FOUND("error.customer.not_found", HttpStatus.NOT_FOUND),
   PRODUCT_OUT_OF_STOCK("error.product.out_of_stock", HttpStatus.BAD_REQUEST),
   VALIDATION_ERROR("error.validation", HttpStatus.BAD_REQUEST),
   INTERNAL_SERVER_ERROR("error.internal_server", HttpStatus.INTERNAL_SERVER_ERROR),

   PRODUCT_NOT_FOUND("error.product.not_found", HttpStatus.NOT_FOUND),
   CATEGORY_NOT_FOUND("error.category.not_found", HttpStatus.NOT_FOUND),

   INSUFFICIENT_STOCK("error.product.insufficient_stock", HttpStatus.BAD_REQUEST),

   PRODUCT_NOT_AVAILABLE("error.product.not_available", HttpStatus.BAD_REQUEST),

   ORDER_NOT_FOUND("error.order.not_found", HttpStatus.NOT_FOUND),
   ORDER_ALREADY_PAID("error.order.already_paid", HttpStatus.BAD_REQUEST),
   STRATEGY_NOT_FOUND("error.payment.strategy_not_found", HttpStatus.NOT_FOUND),
   PAYMENT_FAILED("error.payment.failed", HttpStatus.BAD_REQUEST),

   INVALID_STATUS_TRANSITION("error.order.invalid_status_transition", HttpStatus.BAD_REQUEST),
   ;


   private final String message;
   private final HttpStatus httpStatus;


   ErrorCodes(String message, HttpStatus httpStatus) {
      this.message = message;
      this.httpStatus = httpStatus;
   }

}
