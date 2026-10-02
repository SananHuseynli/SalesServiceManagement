package az.shuseynli.sales_project
.model.dto.category;

import az.shuseynli.sales_project
.model.dto.product.ProductResponse;
import lombok.Data;
import java.util.List;

@Data
public class CategoryResponse {
   private Long id;
   private String name;
   private String description;
   private boolean isActive;
   private List<ProductResponse> products;
}