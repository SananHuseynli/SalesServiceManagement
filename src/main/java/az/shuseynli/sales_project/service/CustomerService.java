package az.shuseynli.sales_project
.service;

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
import org.springframework.data.domain.Page;

public interface CustomerService {
   CustomerResponse createCustomer(CustomerCreateRequest request);
   CustomerResponse updateCustomer(Long id, CustomerRequest request);
   void updateStatus(Long id, CustomerStatus status);
   CustomerResponse getCustomerById(Long id);
   Page<CustomerResponse> searchCustomers(SearchCustomer filter);
}