package az.shuseynli.sales_project.model.dto.product;

import az.shuseynli.sales_project.model.enums.ProductStatus;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductResponse {
   private Long id;
   private String name;
   private String description;
   private String barcode;
   private BigDecimal price;
   private Integer stockQuantity;
   private ProductStatus status;
   private Long categoryId;
}