package backend.project.parkcontrol.repository.crud;

import backend.project.parkcontrol.repository.entities.BranchTemporaryPermit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BranchTemporaryPermitCrud extends JpaRepository<BranchTemporaryPermit, Integer> {
    @Query(value = "select * from branch_temporary_permit where id_temporary_permit = ?", nativeQuery = true)
    List<BranchTemporaryPermit> findById_temporary_permit(Integer id_temporary_permit);

    @Query(value = "select * from branch_temporary_permit where id_branch = ?", nativeQuery = true)
    List<BranchTemporaryPermit> findById_branch(Integer id_branch);

}
