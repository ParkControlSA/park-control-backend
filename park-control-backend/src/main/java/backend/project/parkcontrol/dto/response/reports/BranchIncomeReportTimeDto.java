package backend.project.parkcontrol.dto.response.reports;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BranchIncomeReportTimeDto {
    private String branchName;
    private LocalDate initDate;
    private LocalDate lastDate;
    private Integer typeReport;
    private Double totalIncome;
    private Double nonSubscriberIncome;
    private Double subscriberIncome;
    private Integer totalTransactions;
}
