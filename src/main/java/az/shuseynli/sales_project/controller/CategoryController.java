package az.shuseynli.sales_project.controller;

import az.shuseynli.sales_project
.model.dto.category.*;
import az.shuseynli.sales_project
.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/categories")
@RequiredArgsConstructor
public class CategoryController {

   private final CategoryService categoryService;

   @PostMapping
   public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CategoryCreateRequest request) {
      return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.createCategory(request));
   }

   @PutMapping("/{id}")
   public ResponseEntity<CategoryResponse> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryUpdateRequest request) {
      return ResponseEntity.ok(categoryService.updateCategory(id, request));
   }

   @GetMapping("/{id}")
   public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Long id) {
      return ResponseEntity.ok(categoryService.getCategoryById(id));
   }

   @PatchMapping("/{id}/status")
   public ResponseEntity<Void> updateStatus(@PathVariable Long id, @RequestParam boolean isActive) {
      categoryService.updateStatus(id, isActive);
      return ResponseEntity.noContent().build();
   }
}