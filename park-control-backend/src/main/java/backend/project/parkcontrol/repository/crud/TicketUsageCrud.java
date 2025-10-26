package backend.project.parkcontrol.repository.crud;

import backend.project.parkcontrol.repository.entities.TicketUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TicketUsageCrud extends JpaRepository<TicketUsage, Integer> {
    @Query(value = "select * from ticket_usage where id_ticket = ?", nativeQuery = true)
    List<TicketUsage> findById_ticket(Integer id_ticket);

}
