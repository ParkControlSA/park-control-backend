package backend.project.parkcontrol.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewTicketBusinessUsageDto {
    private Integer id_ticket_usage;
    private Integer id_affiliated_business;
    private Integer granted_hours;
}
