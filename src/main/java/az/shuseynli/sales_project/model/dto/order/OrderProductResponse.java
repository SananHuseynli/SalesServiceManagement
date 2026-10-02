package az.shuseynli.sales_project
.model.dto.order;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class OrderProductResponse {
   private Long id;
   private String name;
   private String barcode;
   private BigDecimal price;
}