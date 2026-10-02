package az.shuseynli.sales_project
.service.imp;

import az.shuseynli.sales_project
.exception.constants.ErrorCodes;
import az.shuseynli.sales_project
.exception.custom.CustomException;
import az.shuseynli.sales_project
.mapper.ProductMapper;
import az.shuseynli.sales_project
.model.dto.product.*;
import az.shuseynli.sales_project
.model.entity.Category;
import az.shuseynli.sales_project
.model.entity.Product;
import az.shuseynli.sales_project
.model.enums.ProductStatus;
import az.shuseynli.sales_project
.repository.CategoryRepository;
import az.shuseynli.sales_project
.repository.ProductRepository;
import az.shuseynli.sales_project
.service.ProductService;
import az.shuseynli.sales_project
.specifications.ProductSpecification;
import az.shuseynli.sales_project
.util.PageableCheckUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static az.shuseynli.sales_project
.util.PageableCheckUtil.getPageable;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

   private final ProductRepository productRepository;
   private final CategoryRepository categoryRepository;
   private final ProductMapper productMapper;

   @Override
   @Transactional
   public ProductResponse createProduct(ProductCreateRequest request) {
      Category category = categoryRepository.findById(request.getCategoryId())
              .orElseThrow(() -> new CustomException(ErrorCodes.CATEGORY_NOT_FOUND, request.getCategoryId()));

      Product product = productMapper.toEntity(request);
      product.setCategory(category);

      return productMapper.toResponse(productRepository.save(product));
   }

   @Override
   @Transactional
   public ProductResponse updateProduct(Long id, ProductUpdateRequest request) {
      Product product = productRepository.findById(id)
              .orElseThrow(() -> new CustomException(ErrorCodes.PRODUCT_NOT_FOUND, id));

      productMapper.updateEntityFromRequest(request, product);

      if (request.getCategoryId() != null) {
         Category category = categoryRepository.findById(request.getCategoryId())
                 .orElseThrow(() -> new CustomException(ErrorCodes.CATEGORY_NOT_FOUND, request.getCategoryId()));
         product.setCategory(category);
      }

      return productMapper.toResponse(productRepository.save(product));
   }

   @Override
   @Transactional(readOnly = true)
   public ProductResponse getProductById(Long id) {
      return productRepository.findById(id)
              .map(productMapper::toResponse)
              .orElseThrow(() -> new CustomException(ErrorCodes.PRODUCT_NOT_FOUND, id));
   }

   @Override
   @Transactional
   public void updateStatus(Long id, ProductStatus status) {
      Product product = productRepository.findById(id)
              .orElseThrow(() -> new CustomException(ErrorCodes.PRODUCT_NOT_FOUND, id));
      product.setStatus(status);
      productRepository.save(product);
   }

   @Override
   @Transactional(readOnly = true)
   public Page<ProductResponse> searchProducts(SearchProduct filter) {
      ProductSpecification spec = new ProductSpecification(filter);
      Pageable pageable = getPageable(filter.getPage(), filter.getSize());

      Page<Product> productPage = productRepository.findAll(spec, pageable);
      return productPage.map(productMapper::toResponse);
   }

   @Override
   @Transactional(readOnly = true)
   public ProductStockCheckResponse checkStockAndStatus(Long id, Integer requestedQuantity) {
      Product product = productRepository.findById(id)
              .orElseThrow(() -> new CustomException(ErrorCodes.PRODUCT_NOT_FOUND, id));

      boolean isSalable = product.getStatus() == ProductStatus.ACTIVE && product.getStockQuantity() >= requestedQuantity;

      return new ProductStockCheckResponse(product.getStockQuantity(), product.getStatus(), isSalable);
   }

   @Override
   @Transactional
   public void decreaseStockQuantity(Long id, Integer quantity) {
      Product product = productRepository.findById(id)
              .orElseThrow(() -> new CustomException(ErrorCodes.PRODUCT_NOT_FOUND, id));

      if (product.getStockQuantity() < quantity) {
         throw new CustomException(ErrorCodes.INSUFFICIENT_STOCK, id);
      }

      int updatedStock = product.getStockQuantity() - quantity;
      product.setStockQuantity(updatedStock);

      if (updatedStock == 0) {
         product.setStatus(ProductStatus.OUT_OF_STOCK);
      }

      productRepository.save(product);
   }

   @Override
   @Transactional
   public void increaseStockQuantity(Long id, Integer quantity) {
      Product product = productRepository.findById(id)
              .orElseThrow(() -> new CustomException(ErrorCodes.PRODUCT_NOT_FOUND, id));

      int updatedStock = product.getStockQuantity() + quantity;
      product.setStockQuantity(updatedStock);

      if (product.getStatus() == ProductStatus.OUT_OF_STOCK && updatedStock > 0) {
         product.setStatus(ProductStatus.ACTIVE);
      }

      productRepository.save(product);
   }




}