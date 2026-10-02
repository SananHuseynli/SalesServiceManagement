package az.shuseynli.sales_project.model.dto.product;

import az.shuseynli.sales_project.exception.constants.ValidationConstants;
import az.shuseynli.sales_project.exception.custom.validation.UniqueField;
import az.shuseynli.sales_project.model.enums.ProductStatus;
import az.shuseynli.sales_project.repository.ProductRepository;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductCreateRequest {
   @NotBlank(message = ValidationConstants.PRODUCT_NAME_NOT_BLANK)
   private String name;

   private String description;

   @NotBlank(message = ValidationConstants.PRODUCT_BARCODE_NOT_BLANK)
   @UniqueField(repository = ProductRepository.class, fieldName = "barcode", message = ValidationConstants.PRODUCT_BARCODE_UNIQUE)
   private String barcode;

   @NotNull(message = ValidationConstants.PRODUCT_PRICE_NOT_NULL)
   @DecimalMin(value = "0.01", message = ValidationConstants.PRODUCT_PRICE_MIN)
   private BigDecimal price;

   @NotNull(message = ValidationConstants.PRODUCT_STOCK_NOT_NULL)
   @Min(value = 0, message = ValidationConstants.PRODUCT_STOCK_MIN)
   private Integer stockQuantity;

   @NotNull(message = ValidationConstants.PRODUCT_STATUS_NOT_NULL)
   private ProductStatus status;

   @NotNull(message = ValidationConstants.PRODUCT_CATEGORY_ID_NOT_NULL)
   private Long categoryId;
}