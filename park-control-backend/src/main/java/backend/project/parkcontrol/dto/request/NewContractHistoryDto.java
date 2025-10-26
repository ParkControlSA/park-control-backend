package backend.project.parkcontrol.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class NewContractHistoryDto {
    private Integer id_contract;
    //private Integer included_hours;
    //private Integer consumed_hours;
    //private LocalDateTime date;
}
