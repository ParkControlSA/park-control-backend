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
@Table(name = "incident")
public class Incident {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_ticket", referencedColumnName = "id")
    private Ticket ticket;

    @Column(name = "incident_type")
    private Integer incident_type;

    @Column(name = "description")
    private String description;

    @Column(name = "evidence_url")
    private String evidence_url;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user_manager", referencedColumnName = "id")
    private UserEntity user;

    @Column(name = "status")
    private Integer status;

    @Column(name = "date")
    private LocalDateTime date;

    // relationships (kept as foreign key ids to respect original schema)
    // FK: id_ticket -> ticket(Ticket)
    // FK: id_user_manager -> user(User)

}
