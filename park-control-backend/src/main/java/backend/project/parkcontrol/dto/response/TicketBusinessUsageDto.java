package backend.project.parkcontrol.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketBusinessUsageDto {
    private Integer id;
    private Integer id_ticket_usage;
    private Integer id_affiliated_business;
    private Integer granted_hours;
}
