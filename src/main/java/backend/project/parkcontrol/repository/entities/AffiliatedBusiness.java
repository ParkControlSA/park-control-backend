package backend.project.parkcontrol.repository.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.apache.catalina.User;

import java.sql.Timestamp;
import java.sql.Time;
import java.sql.Date;

@Getter
@Setter
@Entity
@Table(name = "affiliated_business")
public class AffiliatedBusiness {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "business_name")
    private String business_name;

    @Column(name = "granted_hours")
    private Integer granted_hours;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user", referencedColumnName = "id")
    private UserEntity user;

    // relationships (kept as foreign key ids to respect original schema)
    // FK: id_user -> user(User)

}
