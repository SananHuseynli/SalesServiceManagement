package az.shuseynli.sales_project.model.dto.product;

import az.shuseynli.sales_project.model.enums.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductStockCheckResponse {
   private Integer stockQuantity;
   private ProductStatus status;
   private boolean isSalable;
}