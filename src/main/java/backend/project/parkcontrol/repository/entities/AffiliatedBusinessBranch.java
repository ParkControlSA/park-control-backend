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
@Table(name = "affiliated_business_branch")
public class AffiliatedBusinessBranch {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_affiliated_business", referencedColumnName = "id")
    private AffiliatedBusiness affiliatedBusiness;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_branch", referencedColumnName = "id")
    private Branch branch;

    // relationships (kept as foreign key ids to respect original schema)
    // FK: id_affiliated_business -> affiliated_business(AffiliatedBusiness)
    // FK: id_branch -> branch(Branch)

}
