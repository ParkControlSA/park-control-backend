package backend.project.parkcontrol.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TicketUsageDto {
    private Integer id;
    private Integer id_ticket;
    private Integer granted_hours;
    private Integer consumed_plan_hours;
    private Integer exceeded_hours;
    private Integer total_hours;
    private Double hourly_rate;
    private Double customer_amount;
}
