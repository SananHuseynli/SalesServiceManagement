package az.shuseynli.sales_project
.repository;

import az.shuseynli.sales_project
.model.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<History,Long> {
}
