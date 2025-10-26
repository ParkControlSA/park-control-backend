package backend.project.parkcontrol.repository.crud;

import backend.project.parkcontrol.repository.entities.TemporaryContractPermit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TemporaryContractPermitCrud extends JpaRepository<TemporaryContractPermit, Integer> {
    @Query(value = "select * from temporary_contract_permit where id_contract = ?", nativeQuery = true)
    List<TemporaryContractPermit> findById_contract(Integer id_contract);

    @Query(value = "select * from temporary_contract_permit where id_assigned = ?", nativeQuery = true)
    List<TemporaryContractPermit> findById_assigned(Integer id_assigned);

}
