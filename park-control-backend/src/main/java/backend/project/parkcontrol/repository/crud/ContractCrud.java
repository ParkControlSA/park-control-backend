package backend.project.parkcontrol.repository.crud;

import backend.project.parkcontrol.repository.entities.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ContractCrud extends JpaRepository<Contract, Integer> {
    @Query(value = "select * from contract where id_user = ?", nativeQuery = true)
    List<Contract> findById_user(Integer id_user);

}
