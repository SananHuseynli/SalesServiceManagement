package az.shuseynli.sales_project
.exception.custom.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import java.lang.reflect.Method;

@RequiredArgsConstructor
public class UniqueValidator implements ConstraintValidator<UniqueField, Object> {

   private final ApplicationContext applicationContext;
   private Class<?> repositoryClass;
   private String fieldName;

   @Override
   public void initialize(UniqueField constraintAnnotation) {
      this.repositoryClass = constraintAnnotation.repository();
      this.fieldName = constraintAnnotation.fieldName();
   }

   @Override
   public boolean isValid(Object value, ConstraintValidatorContext context) {
      if (value == null) {
         return true;
      }

      try {
         Object repository = applicationContext.getBean(repositoryClass);

         String methodName = "existsBy" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);

         Method method = repositoryClass.getMethod(methodName, value.getClass());

         Boolean exists = (Boolean) method.invoke(repository, value);

         return !exists;

      } catch (Exception e) {
         throw new RuntimeException("Unique validation failed for field: " + fieldName +
                 ". Ensure existsBy" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1) +
                 " method is defined in repository.", e);
      }
   }

}