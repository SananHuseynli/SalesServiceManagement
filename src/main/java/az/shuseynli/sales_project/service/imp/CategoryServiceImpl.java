package az.shuseynli.sales_project
.service.imp;

import az.shuseynli.sales_project
.exception.custom.CustomException;
import az.shuseynli.sales_project
.exception.constants.ErrorCodes;
import az.shuseynli.sales_project
.mapper.CategoryMapper;
import az.shuseynli.sales_project
.mapper.ProductMapper;
import az.shuseynli.sales_project
.model.dto.category.*;
import az.shuseynli.sales_project
.model.entity.Category;
import az.shuseynli.sales_project
.model.entity.Product;
import az.shuseynli.sales_project
.repository.CategoryRepository;
import az.shuseynli.sales_project
.repository.ProductRepository;
import az.shuseynli.sales_project
.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

   private final CategoryRepository categoryRepository;
   private final ProductRepository productRepository;
   private final CategoryMapper categoryMapper;
   private final ProductMapper productMapper;

   @Override
   @Transactional
   public CategoryResponse createCategory(CategoryCreateRequest request) {

      Category category = categoryMapper.toEntity(request);
      Category savedCategory = categoryRepository.save(category);

      if (!CollectionUtils.isEmpty(request.getProducts())) {
         request.getProducts().forEach(prodRequest -> {
            Product product = productMapper.toEntity(prodRequest);
            product.setCategory(savedCategory);
            productRepository.save(product);
            savedCategory.getProducts().add(product);
         });
      }

      return categoryMapper.toResponse(savedCategory);
   }

   @Override
   @Transactional
   public CategoryResponse updateCategory(Long id, CategoryUpdateRequest request) {
      Category category = categoryRepository.findById(id)
              .orElseThrow(() -> new CustomException(ErrorCodes.CATEGORY_NOT_FOUND, id));

      categoryMapper.updateEntityFromRequest(request, category);
      return categoryMapper.toResponse(categoryRepository.save(category));
   }

   @Override
   @Transactional(readOnly = true)
   public CategoryResponse getCategoryById(Long id) {
      return categoryRepository.findById(id)
              .map(categoryMapper::toResponse)
              .orElseThrow(() -> new CustomException(ErrorCodes.CATEGORY_NOT_FOUND, id));
   }

   @Override
   @Transactional
   public void updateStatus(Long id, boolean isActive) {
      Category category = categoryRepository.findById(id)
              .orElseThrow(() -> new CustomException(ErrorCodes.CATEGORY_NOT_FOUND, id));
      category.setActive(isActive);
      categoryRepository.save(category);
   }
}