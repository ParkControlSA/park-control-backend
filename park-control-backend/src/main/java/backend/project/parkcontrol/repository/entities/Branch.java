package backend.project.parkcontrol.repository.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.sql.Timestamp;
import java.sql.Time;
import java.sql.Date;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "branch")
public class Branch {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "opening_time")
    private LocalTime opening_time;

    @Column(name = "closing_time")
    private LocalTime closing_time;

    @Column(name = "2r_capacity")
    private Integer capacity_2r;

    @Column(name = "4r_capacity")
    private Integer capacity_4r;

    @Column(name = "2r_ocupation")
    private Integer ocupation_2r;

    @Column(name = "4r_ocupation")
    private Integer ocupation_4r;

    // relationships (kept as foreign key ids to respect original schema)

}
