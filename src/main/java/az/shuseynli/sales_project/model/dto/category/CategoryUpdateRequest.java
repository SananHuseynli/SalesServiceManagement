package az.shuseynli.sales_project
.model.dto.category;

import lombok.Data;

@Data
public class CategoryUpdateRequest {
   private String name;
   private String description;
   private Boolean isActive;
}