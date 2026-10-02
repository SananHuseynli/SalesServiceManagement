package az.shuseynli.sales_project
.model.dto.customer;

import az.shuseynli.sales_project
.model.enums.Gender;
import java.time.LocalDate;
import lombok.Data;

@Data
public class ProfileRequest {
   private LocalDate dateOfBirth;
   private Gender gender;
   private String country;
   private String city;
   private String fullAddress;
   private String zipCode;
   private String pin;
   private String passport;
}