package backend.project.parkcontrol.repository.crud;

import backend.project.parkcontrol.repository.entities.ContractHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ContractHistoryCrud extends JpaRepository<ContractHistory, Integer> {
    @Query(value = "select * from contract_history where id_contract = ?", nativeQuery = true)
    List<ContractHistory> findById_contract(Integer id_contract);

}
