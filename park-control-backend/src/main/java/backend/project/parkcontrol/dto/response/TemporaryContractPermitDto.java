package backend.project.parkcontrol.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TemporaryContractPermitDto {
    private Integer id;
    private Integer id_contract;
    private String temporary_plate;
    private LocalDateTime start_date;
    private LocalDateTime end_date;
    private Integer max_uses;
    private Integer used_count;
    private Integer remaining_count;
    private Boolean is_4r;
    private Integer status;
    private String observations;
    private Integer id_assigned;
}
