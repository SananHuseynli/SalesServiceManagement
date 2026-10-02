package az.shuseynli.sales_project
.model.dto.category;

import az.shuseynli.sales_project
.exception.constants.ValidationConstants;
import az.shuseynli.sales_project
.model.dto.product.ProductCreateRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.util.List;

@Data
public class CategoryCreateRequest {
   @NotBlank(message = ValidationConstants.CATEGORY_NAME_NOT_BLANK)
   private String name;

   private String description;
   private boolean isActive = true;

   @Valid
   private List<ProductCreateRequest> products;
}