package az.shuseynli.sales_project.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import org.springdoc.core.customizers.GlobalOperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

   @Bean
   public OpenAPI customOpenAPI() {

      String description = """
                    ### Sales Management System REST API Documentation
      
                    This API handles end-to-end transaction processing for both Online and Offline sales channels.
      
                    **Core Features & Architecture:**
                    * **Order Lifecycle Management:** Validates stock, manages status transitions, decreases inventory levels dynamically, and triggers automated history logs.
                    * **Idempotency Guarantee (Redis):** Uses the X-Idempotency-Key header mechanism to prevent duplicate orders and ensure transactional safety.
                    * **Payment Strategy Pattern:** Implements polymorphic processing decoupled via the Strategy design pattern, supporting CREDIT_CARD, CASH, and BANK_TRANSFER.
                    * **Audit Logs (History Module):** Sequentially tracks and persists status changes for every order modification.
      
                    **Database Table Relations:**
                    * **Order (Central Entity):** Main entity maintaining a Many-to-One relationship with **Customer**, a Many-to-Many mapping with **Product** (via order_product), a One-to-Many mapping with **History**, and a One-to-One association with **Payment**.
                    * **Customer:** Maintains a One-to-One relationship with **Profile** and a One-to-Many relationship with **Order**.
                    * **Product:** Maintains a Many-to-One relationship with **Category** and a Many-to-Many relationship with **Order**.
      
                    **Dynamic Filtering (3 Search Specifications):**
                    The application utilizes 3 specialized JPA Specifications to handle complex, multi-join query filtering across relational structures:
                    1. **OrderSpecification:** Executes deep inner joins across **Order**, **Customer**, **Product**, **Payment**, and **History** tables based on the SearchOrder criteria.
                    2. **ProductSpecification:** Executes dynamic queries joining the **Product** and **Category** tables.
                    3. **CustomerSpecification:** Filters **Customer** records linked dynamically with underlying **Profile** attributes.
      
                    **Primary Specification Search Endpoints:**
                    * GET /api/v1/orders/search - Paginated dynamic filtering utilizing OrderSpecification.
                    * GET /api/v1/products/search - Paginated dynamic filtering utilizing ProductSpecification.
                    * GET /api/v1/customer/search - Paginated dynamic filtering utilizing CustomerSpecification.
                    """;

      return new OpenAPI()
              .info(new Info()
                      .title("Sales MS34 Documentation")
                      .version("1.0.0")
                      .description(description)
                      .contact(new Contact()
                              .name("ShLF")
                              .email("samxalferzeliyev7@gmail.com")));
   }

   @Bean
   public GlobalOperationCustomizer acceptLanguageHeaderCustomizer() {
      return (operation, handlerMethod) -> {
         operation.addParametersItem(new Parameter()
                 .in("header")
                 .name("Accept-Language")
                 .description("Dil seçimi (məs: az, en, ru)")
                 .required(false)
                 .schema(new StringSchema()._default("az")));
         return operation;
      };
   }

}