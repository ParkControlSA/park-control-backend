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

    @Query(value = "SELECT * FROM contract_history WHERE id_contract = ?1 AND DATE(date) = ?2", nativeQuery = true)
    List<ContractHistory> findByContractAndDate(Integer id_contract, java.sql.Date date);

}
