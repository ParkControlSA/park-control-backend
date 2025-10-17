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
@Table(name = "contract_payment")
public class ContractPayment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_contract", referencedColumnName = "id")
    private Contract contract;

    @Column(name = "subtotal")
    private Double subtotal;

    @Column(name = "monthly_discount")
    private Double monthly_discount;

    @Column(name = "annual_discount")
    private Double annual_discount;

    @Column(name = "total")
    private Double total;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "payment_method")
    private Integer payment_method;

    // relationships (kept as foreign key ids to respect original schema)
    // FK: id_contract -> contract(Contract)

}
