package backend.project.parkcontrol.repository.crud;

import backend.project.parkcontrol.repository.entities.TicketPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TicketPaymentCrud extends JpaRepository<TicketPayment, Integer> {
    @Query(value = "select * from ticket_payment where id_ticket = ?", nativeQuery = true)
    List<TicketPayment> findById_ticket(Integer id_ticket);

}
