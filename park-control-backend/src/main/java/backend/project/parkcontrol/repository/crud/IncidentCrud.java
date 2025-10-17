package backend.project.parkcontrol.repository.crud;

import backend.project.parkcontrol.repository.entities.Incident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IncidentCrud extends JpaRepository<Incident, Integer> {
    @Query(value = "select * from incident where id_ticket = ?", nativeQuery = true)
    List<Incident> findById_ticket(Integer id_ticket);

    @Query(value = "select * from incident where id_user_manager = ?", nativeQuery = true)
    List<Incident> findById_user_manager(Integer id_user_manager);

}
