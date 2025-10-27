package backend.project.parkcontrol.repository.crud;

import backend.project.parkcontrol.repository.entities.TicketBusinessUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TicketBusinessUsageCrud extends JpaRepository<TicketBusinessUsage, Integer> {
    @Query(value = "select * from ticket_business_usage where id_ticket_usage = ?", nativeQuery = true)
    List<TicketBusinessUsage> findById_ticket_usage(Integer id_ticket_usage);

    @Query(value = "select * from ticket_business_usage where id_affiliated_business = ?", nativeQuery = true)
    List<TicketBusinessUsage> findById_affiliated_business(Integer id_affiliated_business);

    @Query(value = "select * from ticket_business_usage where id_affiliated_business = ? and id_ticket_usage = ?", nativeQuery = true)
    List<TicketBusinessUsage> findById_affiliated_businessId_ticket_usage(Integer id_affiliated_business, Integer id_ticket_usage);
}
