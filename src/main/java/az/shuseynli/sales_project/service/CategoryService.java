package az.shuseynli.sales_project
.service;

import az.shuseynli.sales_project
.model.dto.category.CategoryCreateRequest;
import az.shuseynli.sales_project
.model.dto.category.CategoryResponse;
import az.shuseynli.sales_project
.model.dto.category.CategoryUpdateRequest;

public interface CategoryService {
   CategoryResponse createCategory(CategoryCreateRequest request);
   CategoryResponse updateCategory(Long id, CategoryUpdateRequest request);
   CategoryResponse getCategoryById(Long id);
   void updateStatus(Long id, boolean isActive);
}