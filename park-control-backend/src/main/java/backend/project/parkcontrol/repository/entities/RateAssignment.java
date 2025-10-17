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
@Table(name = "rate_assignment")
public class RateAssignment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_branch", referencedColumnName = "id")
    private Branch branch;

    @Column(name = "hourly_rate")
    private Double hourly_rate;

    @Column(name = "is_active")
    private Boolean is_active;

    @Column(name = "insert_date")
    private LocalDateTime insert_date;

    @Column(name = "update_date")
    private LocalDateTime update_date;

    // relationships (kept as foreign key ids to respect original schema)
    // FK: id_branch -> branch(Branch)

}
