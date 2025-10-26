package backend.project.parkcontrol.repository.crud;

import backend.project.parkcontrol.repository.entities.ContractPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ContractPaymentCrud extends JpaRepository<ContractPayment, Integer> {
    @Query(value = "select * from contract_payment where id_contract = ?", nativeQuery = true)
    List<ContractPayment> findById_contract(Integer id_contract);

}
