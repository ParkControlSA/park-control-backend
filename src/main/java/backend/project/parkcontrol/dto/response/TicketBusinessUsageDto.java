package backend.project.parkcontrol.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TicketBusinessUsageDto {
    private Integer id;
    private Integer id_ticket_usage;
    private Integer id_affiliated_business;
    private Integer granted_hours;
}
