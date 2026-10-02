package az.shuseynli.sales_project.controller;

import az.shuseynli.sales_project
.model.dto.customer.CustomerCreateRequest;
import az.shuseynli.sales_project
.model.dto.customer.CustomerRequest;
import az.shuseynli.sales_project
.model.dto.customer.CustomerResponse;
import az.shuseynli.sales_project
.model.dto.customer.SearchCustomer;
import az.shuseynli.sales_project
.model.enums.CustomerStatus;
import az.shuseynli.sales_project
.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

   private final CustomerService customerService;

   @PostMapping
   public ResponseEntity<CustomerResponse> createCustomer(@Valid @RequestBody CustomerCreateRequest request) {
      CustomerResponse response = customerService.createCustomer(request);
      return ResponseEntity.ok().body(response);
   }

   @PutMapping("/{id}")
   public ResponseEntity<CustomerResponse> updateCustomer(
           @PathVariable Long id,
           @Valid @RequestBody CustomerRequest request) {
      CustomerResponse response = customerService.updateCustomer(id, request);
      return ResponseEntity.ok(response);
   }

   @PutMapping("/{id}/status")
   public ResponseEntity<Void> updateStatus(
           @PathVariable Long id,
           @RequestParam CustomerStatus status) {
      customerService.updateStatus(id, status);
      return ResponseEntity.ok().build();
   }

   @GetMapping("/{id}")
   public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable Long id) {
      CustomerResponse response = customerService.getCustomerById(id);
      return ResponseEntity.ok(response);
   }

   @GetMapping("/search")
   public ResponseEntity<Page<CustomerResponse>> searchCustomers(@ModelAttribute SearchCustomer filter) {
      Page<CustomerResponse> response = customerService.searchCustomers(filter);
      return ResponseEntity.ok(response);
   }

}