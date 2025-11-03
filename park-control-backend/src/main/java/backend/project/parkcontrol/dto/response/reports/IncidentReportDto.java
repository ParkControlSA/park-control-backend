package backend.project.parkcontrol.dto.response.reports;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IncidentReportDto {
    private Integer incidentId;
    private String branchName;
    private String vehiclePlate;
    private Integer incidentType;
    private String description;
    private String evidenceUrl;
    private Integer status;
    private LocalDateTime date;
    private String managerName;
}
