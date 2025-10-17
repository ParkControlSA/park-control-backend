package backend.project.parkcontrol.repository.crud;

import backend.project.parkcontrol.repository.entities.AffiliatedBusiness;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AffiliatedBusinessCrud extends JpaRepository<AffiliatedBusiness, Integer> {
    @Query(value = "select * from affiliated_business where id_user = ?", nativeQuery = true)
    List<AffiliatedBusiness> findById_user(Integer id_user);

}
