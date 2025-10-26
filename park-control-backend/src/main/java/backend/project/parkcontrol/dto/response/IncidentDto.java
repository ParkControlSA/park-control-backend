package backend.project.parkcontrol.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
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
