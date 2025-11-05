package backend.project.parkcontrol.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IncidentDto {
    private Integer id;
    private Integer id_ticket;
    private Integer incident_type;
    private String description;
    private String evidence_url;
    private Integer id_user_manager;
    private Integer status;
    private LocalDateTime date;
}
