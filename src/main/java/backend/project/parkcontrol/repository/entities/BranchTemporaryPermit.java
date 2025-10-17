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
@Table(name = "branch_temporary_permit")
public class BranchTemporaryPermit {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_temporary_permit", referencedColumnName = "id")
    private TemporaryContractPermit temporaryContractPermit;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_branch", referencedColumnName = "id")
    private Branch branch;

    // relationships (kept as foreign key ids to respect original schema)
    // FK: id_temporary_permit -> temporary_contract_permit(TemporaryContractPermit)
    // FK: id_branch -> branch(Branch)

}
