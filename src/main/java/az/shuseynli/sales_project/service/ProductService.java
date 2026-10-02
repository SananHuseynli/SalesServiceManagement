package az.shuseynli.sales_project
.service;

import az.shuseynli.sales_project
.model.dto.product.*;
import az.shuseynli.sales_project
.model.enums.ProductStatus;
import org.springframework.data.domain.Page;

public interface ProductService {
   ProductResponse createProduct(ProductCreateRequest request);
   ProductResponse updateProduct(Long id, ProductUpdateRequest request);
   ProductResponse getProductById(Long id);
   void updateStatus(Long id, ProductStatus status);
   Page<ProductResponse> searchProducts(SearchProduct filter);

   ProductStockCheckResponse checkStockAndStatus(Long id, Integer requestedQuantity);
   void decreaseStockQuantity(Long id, Integer quantity);
   void increaseStockQuantity(Long id, Integer quantity);
}
