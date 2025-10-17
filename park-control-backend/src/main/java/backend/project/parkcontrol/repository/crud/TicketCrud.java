package backend.project.parkcontrol.repository.crud;

import backend.project.parkcontrol.repository.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TicketCrud extends JpaRepository<Ticket, Integer> {
    @Query(value = "select * from ticket where id_branch = ?", nativeQuery = true)
    List<Ticket> findById_branch(Integer id_branch);

}
