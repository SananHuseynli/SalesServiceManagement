package az.shuseynli.sales_project
.repository;

import az.shuseynli.sales_project
.model.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
