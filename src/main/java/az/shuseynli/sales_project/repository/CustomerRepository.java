package az.shuseynli.sales_project
.repository;

import az.shuseynli.sales_project
.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CustomerRepository extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {

   boolean existsByEmail(String email);

   boolean existsByPhoneNumber(String phoneNumber);

}
