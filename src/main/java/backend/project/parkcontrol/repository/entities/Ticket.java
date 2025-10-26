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
@Table(name = "ticket")
public class Ticket {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_branch", referencedColumnName = "id")
    private Branch branch;

    @Column(name = "plate")
    private String plate;

    @Column(name = "card")
    private String card;

    @Column(name = "qr")
    private String qr;

    @Column(name = "entry_date")
    private LocalDateTime entry_date;

    @Column(name = "exit_date")
    private LocalDateTime exit_date;

    @Column(name = "is_4r")
    private Boolean is_4r;

    @Column(name = "status")
    private Integer status;

    // relationships (kept as foreign key ids to respect original schema)
    // FK: id_branch -> branch(Branch)

}
