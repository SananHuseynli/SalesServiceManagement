package az.shuseynli.sales_project
.repository;

import az.shuseynli.sales_project
.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrderRepository extends JpaRepository<Order,Long>, JpaSpecificationExecutor<Order> {
}
