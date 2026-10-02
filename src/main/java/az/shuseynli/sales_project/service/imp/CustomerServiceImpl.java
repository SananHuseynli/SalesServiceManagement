package az.shuseynli.sales_project
.service.imp;

import az.shuseynli.sales_project
.exception.constants.ErrorCodes;
import az.shuseynli.sales_project
.exception.custom.CustomException;
import az.shuseynli.sales_project
.mapper.CustomerMapper;
import az.shuseynli.sales_project
.model.dto.customer.CustomerCreateRequest;
import az.shuseynli.sales_project
.model.dto.customer.CustomerRequest;
import az.shuseynli.sales_project
.model.dto.customer.CustomerResponse;
import az.shuseynli.sales_project
.model.dto.customer.SearchCustomer;
import az.shuseynli.sales_project
.model.entity.Customer;
import az.shuseynli.sales_project
.model.entity.Profile;
import az.shuseynli.sales_project
.model.enums.CustomerStatus;
import az.shuseynli.sales_project
.repository.CustomerRepository;
import az.shuseynli.sales_project
.repository.ProfileRepository;
import az.shuseynli.sales_project
.service.CustomerService;
import az.shuseynli.sales_project
.specifications.CustomerSpecification;
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
public class CustomerServiceImpl implements CustomerService {

   private final CustomerRepository customerRepository;
   private final ProfileRepository profileRepository;
   private final CustomerMapper customerMapper;

   @Override
   @Transactional
   public CustomerResponse createCustomer(CustomerCreateRequest request) {
      Customer customer = customerMapper.toEntity(request);
      customer.setStatus(CustomerStatus.ACTIVE);

      if (customer.getProfile() != null) {
         Profile savedProfile = profileRepository.save(customer.getProfile());
         customer.setProfile(savedProfile);
      }

      Customer savedCustomer = customerRepository.save(customer);
      return customerMapper.toResponse(savedCustomer);
   }

   @Override
   @Transactional
   public CustomerResponse updateCustomer(Long id, CustomerRequest request) {
      Customer customer = customerRepository.findById(id)
              .orElseThrow(() -> new CustomException(ErrorCodes.CUSTOMER_NOT_FOUND, id));

      customerMapper.updateEntityFromRequest(request, customer);

      Customer updatedCustomer = customerRepository.save(customer);
      return customerMapper.toResponse(updatedCustomer);
   }

   @Override
   @Transactional
   public void updateStatus(Long id, CustomerStatus status) {
      Customer customer = customerRepository.findById(id)
              .orElseThrow(() -> new CustomException(ErrorCodes.CUSTOMER_NOT_FOUND, id));

      customer.setStatus(status);
      customerRepository.save(customer);
   }

   @Override
   @Transactional(readOnly = true)
   public CustomerResponse getCustomerById(Long id) {
      Customer customer = customerRepository.findById(id)
              .orElseThrow(() -> new CustomException(ErrorCodes.CUSTOMER_NOT_FOUND, id));

      return customerMapper.toResponse(customer);
   }

   @Override
   @Transactional(readOnly = true)
   public Page<CustomerResponse> searchCustomers(SearchCustomer filter) {

      CustomerSpecification spec = new CustomerSpecification(filter);

      Pageable pageable = getPageable(filter.getPage(), filter.getSize());

      Page<Customer> customerPage = customerRepository.findAll(spec, pageable);

      return customerPage.map(customerMapper::toResponse);
   }


}