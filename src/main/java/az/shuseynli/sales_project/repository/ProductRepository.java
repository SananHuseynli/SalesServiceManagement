package az.shuseynli.sales_project
.repository;

import az.shuseynli.sales_project
.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
   boolean existsByBarcode(String barcode);

}
