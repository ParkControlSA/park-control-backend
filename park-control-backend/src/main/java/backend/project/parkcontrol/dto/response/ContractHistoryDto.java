package backend.project.parkcontrol.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContractHistoryDto {
    private Integer id;
    private Integer id_contract;
    private Integer included_hours;
    private Integer consumed_hours;
    private LocalDateTime date;
}
