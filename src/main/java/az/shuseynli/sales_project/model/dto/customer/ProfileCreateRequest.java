package az.shuseynli.sales_project
.model.dto.customer;

import az.shuseynli.sales_project
.exception.constants.ValidationConstants;
import az.shuseynli.sales_project
.exception.custom.validation.UniqueField;
import az.shuseynli.sales_project
.model.enums.Gender;
import az.shuseynli.sales_project
.repository.ProfileRepository;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;
import lombok.Data;

@Data
public class ProfileCreateRequest {

   private LocalDate dateOfBirth;

   @NotNull(message = ValidationConstants.PROFILE_GENDER_NOT_NULL)
   private Gender gender;

   private String country;
   private String city;
   private String fullAddress;
   private String zipCode;

   @NotBlank(message = ValidationConstants.PROFILE_PIN_NOT_BLANK)
   @Pattern(regexp = "^[A-Z0-9]{7}$", message = ValidationConstants.PROFILE_PIN_INVALID)
   @UniqueField(repository = ProfileRepository.class,
           fieldName = "pin",
           message = ValidationConstants.PROFILE_PIN_UNIQUE)
   private String pin;

   private String passport;

}