package az.shuseynli.sales_project
.model.dto.order;

import lombok.Data;

@Data
public class OrderCustomerResponse {
   private Long id;
   private String name;
   private String surname;
   private String email;
   private String phoneNumber;
}