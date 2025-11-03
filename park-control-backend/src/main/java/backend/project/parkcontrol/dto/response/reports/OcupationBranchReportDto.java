package backend.project.parkcontrol.dto.response.reports;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OcupationBranchReportDto {
    private Integer id;
    private String branchName;
    private Integer twoRCapacity;
    private Integer twoROcupation;
    private double twoRPercent;
    private Integer fourRCapacity;
    private Integer fourROcupation;
    private double fourRPercent;
}
