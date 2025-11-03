package backend.project.parkcontrol.dto.response.reports;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BranchIncomeReportDto {
    private Integer userId;
    private String userName;
    private Integer contractId;
    private String planName;
    private Boolean isActive;
    private Double totalConsumedHours;
    private Double totalIncludedHours;
    private Double availableBalance;
    private Double exceededHours;
}
