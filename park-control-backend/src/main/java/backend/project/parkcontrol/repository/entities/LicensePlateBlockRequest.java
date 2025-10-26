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
@Table(name = "license_plate_block_request")
public class LicensePlateBlockRequest {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "is_4r")
    private Boolean is_4r;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_contract", referencedColumnName = "id")
    private Contract contract;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_assigned", referencedColumnName = "id")
    private UserEntity user;

    @Column(name = "old_plate")
    private String old_plate;

    @Column(name = "new_plate")
    private String new_plate;

    @Column(name = "evidence_url")
    private String evidence_url;

    @Column(name = "note")
    private String note;

    @Column(name = "creation_date")
    private LocalDateTime creation_date;

    @Column(name = "status")
    private Integer status;

    // relationships (kept as foreign key ids to respect original schema)
    // FK: id_contract -> contract(Contract)
    // FK: id_assigned -> user(User)

}
