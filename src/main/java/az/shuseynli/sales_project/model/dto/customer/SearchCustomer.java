package az.shuseynli.sales_project
.model.dto.customer;

import az.shuseynli.sales_project
.model.enums.CustomerStatus;
import az.shuseynli.sales_project
.model.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchCustomer {

   // Customer Fields
   private String name;
   private String surname;
   private String email;
   private String phoneNumber;
   private CustomerStatus status;

   // Profile Fields
   private String pin;
   private String passport;
   private String country;
   private String city;
   private String zipCode;
   private Gender gender;
   private LocalDate dobFrom;
   private LocalDate dobTo;

   // Pagination Fields
   private Integer page;
   private Integer size;

}