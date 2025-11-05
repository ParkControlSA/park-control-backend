package backend.project.parkcontrol.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RateAssignmentDto {
    private Integer id;
    private Integer id_branch;
    private Double hourly_rate;
    private Boolean is_active;
    private LocalDateTime insert_date;
    private LocalDateTime update_date;
}
