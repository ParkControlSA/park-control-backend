package backend.project.parkcontrol.repository.crud;

import backend.project.parkcontrol.repository.entities.LicensePlateBlockRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LicensePlateBlockRequestCrud extends JpaRepository<LicensePlateBlockRequest, Integer> {
    @Query(value = "select * from license_plate_block_request where id_contract = ?", nativeQuery = true)
    List<LicensePlateBlockRequest> findById_contract(Integer id_contract);

    @Query(value = "select * from license_plate_block_request where id_assigned = ?", nativeQuery = true)
    List<LicensePlateBlockRequest> findById_assigned(Integer id_assigned);

}
