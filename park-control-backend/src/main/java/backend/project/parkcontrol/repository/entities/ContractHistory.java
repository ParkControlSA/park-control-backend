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
@Table(name = "contract_history")
public class ContractHistory {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_contract", referencedColumnName = "id")
    private Contract contract;

    @Column(name = "included_hours")
    private Integer included_hours;

    @Column(name = "consumed_hours")
    private Integer consumed_hours;

    @Column(name = "date")
    private LocalDateTime date;

    // relationships (kept as foreign key ids to respect original schema)
    // FK: id_contract -> contract(Contract)

}
