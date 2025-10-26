package backend.project.parkcontrol.repository.crud;

import backend.project.parkcontrol.repository.entities.BranchManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BranchManagerCrud extends JpaRepository<BranchManager, Integer> {

    @Query(value = "SELECT * FROM branch_manager WHERE id_user = ?", nativeQuery = true)
    List<BranchManager> findById_user(Integer id_user);

    @Query(value = "SELECT * FROM branch_manager WHERE id_branch = ?", nativeQuery = true)
    List<BranchManager> findById_branch(Integer id_branch);

    @Query(value = "SELECT * FROM branch_manager WHERE id_branch = ? AND id_user = ?", nativeQuery = true)
    List<BranchManager> findById_branchId_user(Integer id_branch, Integer id_user);
}
