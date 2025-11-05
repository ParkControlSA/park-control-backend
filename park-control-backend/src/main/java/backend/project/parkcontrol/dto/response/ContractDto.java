package backend.project.parkcontrol.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContractDto {
    private Integer id;
    private Integer id_user;
    private Integer id_plan;
    private Boolean is_4r;
    private String license_plate;
    private LocalDateTime start_date;
    private LocalDateTime end_date;
    private Integer months;
    private Boolean is_anual;
    private Boolean active;
}
