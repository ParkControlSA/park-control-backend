package backend.project.parkcontrol.dto.request;

import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewBranchDto {
    private String name;
    private String address;
    private LocalTime opening_time;
    private LocalTime closing_time;
    private Integer capacity_2r;
    private Integer capacity_4r;
}
