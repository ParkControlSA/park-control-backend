package backend.project.parkcontrol.repository.crud;

import backend.project.parkcontrol.repository.entities.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BranchCrud extends JpaRepository<Branch, Integer> {
}
