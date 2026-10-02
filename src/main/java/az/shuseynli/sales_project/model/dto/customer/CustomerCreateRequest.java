package az.shuseynli.sales_project
.model.dto.customer;

import az.shuseynli.sales_project
.exception.constants.ValidationConstants;
import az.shuseynli.sales_project
.exception.custom.validation.UniqueField;
import az.shuseynli.sales_project
.repository.CustomerRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CustomerCreateRequest {

   @NotBlank(message = ValidationConstants.CUSTOMER_NAME_NOT_BLANK)
   private String name;

   @NotBlank(message = ValidationConstants.CUSTOMER_SURNAME_NOT_BLANK)
   private String surname;

   @NotBlank(message = ValidationConstants.CUSTOMER_EMAIL_NOT_BLANK)
   @Email(message = ValidationConstants.CUSTOMER_EMAIL_INVALID)
   @UniqueField(repository = CustomerRepository.class,
           fieldName = "email",
           message = ValidationConstants.CUSTOMER_EMAIL_UNIQUE)
   private String email;

   @NotBlank(message = ValidationConstants.CUSTOMER_PHONE_NOT_BLANK)
   @Pattern(regexp = "^\\+994(50|51|55|70|77|99)\\d{7}$", message = ValidationConstants.CUSTOMER_PHONE_INVALID)
   @UniqueField(repository = CustomerRepository.class,
           fieldName = "phoneNumber",
           message = ValidationConstants.CUSTOMER_PHONE_UNIQUE)
   private String phoneNumber;

   @Valid
   @NotNull(message = ValidationConstants.CUSTOMER_PROFILE_NOT_NULL)
   private ProfileCreateRequest profile;

}