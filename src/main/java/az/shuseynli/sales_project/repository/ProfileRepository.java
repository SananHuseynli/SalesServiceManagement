package az.shuseynli.sales_project
.repository;

import az.shuseynli.sales_project
.model.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

   boolean existsByPin(String pin);

}
