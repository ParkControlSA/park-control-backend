package backend.project.parkcontrol.dto.response;

import lombok.*;

import java.time.LocalTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BranchDto {
    private Integer id;
    private String name;
    private String address;
    private LocalTime opening_time;
    private LocalTime closing_time;
    private Integer capacity_2r;
    private Integer capacity_4r;
    private Integer ocupation_2r;
    private Integer ocupation_4r;
}
