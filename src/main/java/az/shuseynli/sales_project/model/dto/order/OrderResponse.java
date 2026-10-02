package az.shuseynli.sales_project
.model.dto.order;

import az.shuseynli.sales_project
.model.enums.Channel;
import az.shuseynli.sales_project
.model.enums.OrderStatus;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderResponse implements Serializable {
   @Serial
   private static final long serialVersionUID = 1L;

   private Long id;
   private Channel channel;
   private BigDecimal totalAmount;
   private OrderStatus currentStatus;
   private LocalDateTime createdAt;
   private String idempotencyKey;
}