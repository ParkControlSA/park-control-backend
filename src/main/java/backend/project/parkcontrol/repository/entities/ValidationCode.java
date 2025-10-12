package backend.project.parkcontrol.repository.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Table(name = "validation_code")
@Entity
public class ValidationCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String code;

    @Column(name = "expiration_time")
    private Date expirationTime;

    @Column(name = "is_used")
    private Boolean isUsed;

    @Column(name = "attemps")
    private Integer attempts;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user",referencedColumnName = "id")
    private UserEntity user;

}
