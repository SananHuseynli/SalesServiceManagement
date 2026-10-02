package az.shuseynli.sales_project
.model.dto.customer;

import az.shuseynli.sales_project
.model.enums.CustomerStatus;
import lombok.Data;

@Data
public class CustomerRequest {
   private String name;
   private String surname;
   private String email;
   private String phoneNumber;
   private CustomerStatus status;
   private ProfileRequest profile;
}