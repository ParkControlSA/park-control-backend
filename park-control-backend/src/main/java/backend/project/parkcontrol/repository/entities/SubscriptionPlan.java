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
@Table(name = "subscription_plan")
public class SubscriptionPlan {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "month_hours")
    private Integer month_hours;

    @Column(name = "daily_hours")
    private Integer daily_hours;

    @Column(name = "total_discount")
    private Double total_discount;

    @Column(name = "annual_discount")
    private Double annual_discount;

    // relationships (kept as foreign key ids to respect original schema)

}
