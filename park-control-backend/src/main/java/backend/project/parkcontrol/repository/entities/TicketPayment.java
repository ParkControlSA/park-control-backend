package backend.project.parkcontrol.repository.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.sql.Timestamp;
import java.sql.Time;
import java.sql.Date;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "ticket_payment")
public class TicketPayment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_ticket", referencedColumnName = "id")
    private Ticket ticket;

    @Column(name = "total_amount")
    private Double total_amount;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "payment_method")
    private Integer payment_method;

    // relationships (kept as foreign key ids to respect original schema)
    // FK: id_ticket -> ticket(Ticket)

}
