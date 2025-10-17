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
@Table(name = "contract")
public class Contract {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user", referencedColumnName = "id")
    private UserEntity user;

    @Column(name = "id_plan")
    private Integer id_plan;

    @Column(name = "is_4r")
    private Boolean is_4r;

    @Column(name = "license_plate")
    private String license_plate;

    @Column(name = "start_date")
    private LocalDateTime start_date;

    @Column(name = "end_date")
    private LocalDateTime end_date;

    @Column(name = "months")
    private Integer months;

    @Column(name = "is_anual")
    private Boolean is_anual;

    @Column(name = "active")
    private Boolean active;

    // relationships (kept as foreign key ids to respect original schema)
    // FK: id_user -> user(User)

}
