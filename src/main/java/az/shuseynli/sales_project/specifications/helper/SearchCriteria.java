package az.shuseynli.sales_project
.specifications.helper;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchCriteria {
   private String key1;
   private String key2;
   private Object value1;
   private Object value2;
   private SearchOperations operation1;
   private SearchOperations operation2;
}