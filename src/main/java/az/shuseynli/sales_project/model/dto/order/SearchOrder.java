package az.shuseynli.sales_project
.model.dto.order;

import az.shuseynli.sales_project
.model.enums.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchOrder {
   // Order Fields
   private Channel channel;
   private BigDecimal totalAmountFrom;
   private BigDecimal totalAmountTo;
   private OrderStatus currentStatus;
   private String idempotencyKey;

   // Customer Fields
   private Long customerId;
   private String customerName;
   private String customerSurname;
   private String customerEmail;
   private String customerPhoneNumber;

   // Product Fields
   private Long productId;
   private String productName;
   private String productBarcode;

   // Payment Fields
   private PaymentMethod paymentMethod;
   private PaymentStatus paymentStatus;
   private String transactionId;
   private String branchId;
   private String cashRegisterId;

   // History Fields
   private OrderStatus historyStatus;
   private String historyNotes;

   // Pagination Fields
   private Integer page;
   private Integer size;

}