package az.shuseynli.sales_project
.exception.constants;

public final class ValidationConstants {

   private ValidationConstants() {
   }

   // Customer validation
   public static final String CUSTOMER_NAME_NOT_BLANK = "{validation.customer.name.notblank}";
   public static final String CUSTOMER_SURNAME_NOT_BLANK = "{validation.customer.surname.notblank}";
   public static final String CUSTOMER_EMAIL_NOT_BLANK = "{validation.customer.email.notblank}";
   public static final String CUSTOMER_EMAIL_INVALID = "{validation.customer.email.invalid}";
   public static final String CUSTOMER_PHONE_NOT_BLANK = "{validation.customer.phone.notblank}";
   public static final String CUSTOMER_PHONE_INVALID = "{validation.customer.phone.invalid}";
   public static final String CUSTOMER_STATUS_NOT_NULL = "{validation.customer.status.notnull}";

   // Profile validation
   public static final String PROFILE_PIN_NOT_BLANK = "{validation.profile.pin.notblank}";
   public static final String PROFILE_PIN_INVALID = "{validation.profile.pin.invalid}";
   public static final String PROFILE_GENDER_NOT_NULL = "{validation.profile.gender.notnull}";
   public static final String CUSTOMER_PROFILE_NOT_NULL = "{validation.customer.profile.notnull}";

   // Custom validation
   public static final String CUSTOMER_EMAIL_UNIQUE = "{validation.customer.email.unique}";
   public static final String CUSTOMER_PHONE_UNIQUE = "{validation.customer.phone.unique}";
   public static final String PROFILE_PIN_UNIQUE = "{validation.profile.pin.unique}";

   // Product validation
   public static final String PRODUCT_NAME_NOT_BLANK = "{validation.product.name.notblank}";
   public static final String PRODUCT_BARCODE_NOT_BLANK = "{validation.product.barcode.notblank}";
   public static final String PRODUCT_BARCODE_UNIQUE = "{validation.product.barcode.unique}";
   public static final String PRODUCT_PRICE_NOT_NULL = "{validation.product.price.notnull}";
   public static final String PRODUCT_PRICE_MIN = "{validation.product.price.min}";
   public static final String PRODUCT_STOCK_NOT_NULL = "{validation.product.stock.notnull}";
   public static final String PRODUCT_STOCK_MIN = "{validation.product.stock.min}";
   public static final String PRODUCT_STATUS_NOT_NULL = "{validation.product.status.notnull}";

   // Category validation
   public static final String CATEGORY_NAME_NOT_BLANK = "{validation.category.name.notblank}";
   public static final String PRODUCT_CATEGORY_ID_NOT_NULL = "{validation.product.category_id.notnull}";

   // Order validation
   public static final String ORDER_IDEMPOTENCY_NOT_BLANK = "{validation.order.idempotency.notblank}";
   public static final String ORDER_CUSTOMER_NOT_NULL = "{validation.order.customer.notnull}";
   public static final String ORDER_PRODUCTS_NOT_EMPTY = "{validation.order.products.notempty}";

   // Payment validation
   public static final String PAYMENT_ORDER_ID_NOT_NULL = "{validation.payment.order_id.notnull}";
   public static final String PAYMENT_METHOD_NOT_NULL = "{validation.payment.method.notnull}";
   public static final String PAYMENT_IDEMPOTENCY_NOT_BLANK = "{validation.payment.idempotency.notblank}";
}