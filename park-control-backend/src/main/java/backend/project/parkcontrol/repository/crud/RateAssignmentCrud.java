package backend.project.parkcontrol.repository.crud;

import backend.project.parkcontrol.repository.entities.RateAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RateAssignmentCrud extends JpaRepository<RateAssignment, Integer> {
    @Query(value = "select * from rate_assignment where id_branch = ?", nativeQuery = true)
    List<RateAssignment> findById_branch(Integer id_branch);

    @Query(value = "select * from rate_assignment where id_branch = ? and is_active = ?", nativeQuery = true)
    List<RateAssignment> findById_branchIsActive(Integer id_branch, Boolean is_active);
}
