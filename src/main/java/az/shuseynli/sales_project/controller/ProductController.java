package az.shuseynli.sales_project.controller;

import az.shuseynli.sales_project.model.dto.product.*;
import az.shuseynli.sales_project
.model.enums.ProductStatus;
import az.shuseynli.sales_project
.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/products")
@RequiredArgsConstructor
public class ProductController {

   private final ProductService productService;

   @PostMapping
   public ResponseEntity<az.shuseynli.sales_project
.model.dto.product.ProductResponse> createProduct(@Valid @RequestBody ProductCreateRequest request) {
      return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(request));
   }

   @PutMapping("/{id}")
   public ResponseEntity<az.shuseynli.sales_project
.model.dto.product.ProductResponse> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductUpdateRequest request) {
      return ResponseEntity.ok(productService.updateProduct(id, request));
   }

   @GetMapping("/{id}")
   public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
      return ResponseEntity.ok(productService.getProductById(id));
   }

   @PatchMapping("/{id}/status")
   public ResponseEntity<Void> updateStatus(@PathVariable Long id, @RequestParam ProductStatus status) {
      productService.updateStatus(id, status);
      return ResponseEntity.noContent().build();
   }

   @GetMapping("/search")
   public ResponseEntity<Page<ProductResponse>> searchProducts(@ModelAttribute SearchProduct filter) {
      Page<ProductResponse> response = productService.searchProducts(filter);
      return ResponseEntity.ok(response);
   }

}