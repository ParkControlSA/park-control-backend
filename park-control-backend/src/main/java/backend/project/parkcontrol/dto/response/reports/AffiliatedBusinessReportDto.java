package backend.project.parkcontrol.dto.response.reports;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AffiliatedBusinessReportDto {
    private Integer businessId;
    private String businessName;
    private String branchName;
    private Integer ticketsBenefited;
    private Double totalFreeHours;
    private Double totalToLiquidate;
}
