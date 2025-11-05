package backend.project.parkcontrol.dto.request;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewRateAssignmentDto {
    private Integer id_branch;
    private Double hourly_rate;
    //private Boolean is_active;
    //private LocalDateTime insert_date;
    //private LocalDateTime update_date;
}
