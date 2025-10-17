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
@Table(name = "ticket_business_usage")
public class TicketBusinessUsage {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_ticket_usage", referencedColumnName = "id")
    private TicketUsage ticketUsage;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_affiliated_business", referencedColumnName = "id")
    private AffiliatedBusiness affiliatedBusiness;

    @Column(name = "granted_hours")
    private Integer granted_hours;

    // relationships (kept as foreign key ids to respect original schema)
    // FK: id_ticket_usage -> ticket_usage(TicketUsage)
    // FK: id_affiliated_business -> affiliated_business(AffiliatedBusiness)

}
