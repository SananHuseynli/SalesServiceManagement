package az.shuseynli.sales_project.model.dto.product;

import az.shuseynli.sales_project.model.enums.ProductStatus;
import lombok.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchProduct {

   // Product
   private String name;
   private String description;
   private String barcode;
   private BigDecimal priceFrom;
   private BigDecimal priceTo;
   private Integer stockFrom;
   private Integer stockTo;
   private ProductStatus status;

   // Category
   private Long categoryId;
   private String categoryName;
   private Boolean categoryActive;

   // Pagination Fields
   private Integer page;
   private Integer size;

}