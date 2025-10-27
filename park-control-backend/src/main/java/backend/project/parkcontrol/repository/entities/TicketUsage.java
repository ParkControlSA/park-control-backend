package backend.project.parkcontrol.repository.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.sql.Timestamp;
import java.sql.Time;
import java.sql.Date;

@Getter
@Setter
@Entity
@Table(name = "ticket_usage")
public class TicketUsage {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_ticket", referencedColumnName = "id")
    private Ticket ticket;

    @Column(name = "granted_hours")
    private Integer granted_hours;

    @Column(name = "consumed_plan_hours")
    private Integer consumed_plan_hours;

    @Column(name = "consumed_hours")
    private Integer consumed_hours;

    @Column(name = "exceeded_hours")
    private Integer exceeded_hours;

    @Column(name = "total_hours")
    private Integer total_hours;

    @Column(name = "hourly_rate")
    private Double hourly_rate;

    @Column(name = "customer_amount")
    private Double customer_amount;

    // relationships (kept as foreign key ids to respect original schema)
    // FK: id_ticket -> ticket(Ticket)

}
