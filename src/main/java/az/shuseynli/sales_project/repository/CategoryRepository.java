package az.shuseynli.sales_project
.repository;

import az.shuseynli.sales_project
.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
