package backend.project.parkcontrol.repository.crud;

import backend.project.parkcontrol.repository.entities.AffiliatedBusinessBranch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AffiliatedBusinessBranchCrud extends JpaRepository<AffiliatedBusinessBranch, Integer> {
    @Query(value = "select * from affiliated_business_branch where id_affiliated_business = ?", nativeQuery = true)
    List<AffiliatedBusinessBranch> findById_affiliated_business(Integer id_affiliated_business);

    @Query(value = "select * from affiliated_business_branch where id_branch = ?", nativeQuery = true)
    List<AffiliatedBusinessBranch> findById_branch(Integer id_branch);

}
