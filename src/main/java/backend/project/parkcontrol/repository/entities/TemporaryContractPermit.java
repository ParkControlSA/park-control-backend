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
@Table(name = "temporary_contract_permit")
public class TemporaryContractPermit {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_contract", referencedColumnName = "id")
    private Contract contract;

    @Column(name = "temporary_plate")
    private String temporary_plate;

    @Column(name = "start_date")
    private LocalDateTime start_date;

    @Column(name = "end_date")
    private LocalDateTime end_date;

    @Column(name = "max_uses")
    private Integer max_uses;

    @Column(name = "used_count")
    private Integer used_count;

    @Column(name = "remaining_count")
    private Integer remaining_count;

    @Column(name = "is_4r")
    private Boolean is_4r;

    @Column(name = "status")
    private Integer status;

    @Column(name = "observations")
    private String observations;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_assigned", referencedColumnName = "id")
    private UserEntity encargado;

    // relationships (kept as foreign key ids to respect original schema)
    // FK: id_contract -> contract(Contract)
    // FK: id_assigned -> user(User)

}
