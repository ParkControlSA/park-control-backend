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

    @Query(value = "select * from ticket where plate = ? and status = ?", nativeQuery = true)
    List<Ticket> findByPlateStatus(String plate, Integer status);

    @Query(value = "select * from ticket where card = ?", nativeQuery = true)
    List<Ticket> findByCard(String card);

    @Query(value = "select * from ticket where qr = ?", nativeQuery = true)
    List<Ticket> findByQr(String qr);

    @Query(value = "select * from ticket where plate = ? order by id desc", nativeQuery = true)
    List<Ticket> findByPlate(String plate);
}
