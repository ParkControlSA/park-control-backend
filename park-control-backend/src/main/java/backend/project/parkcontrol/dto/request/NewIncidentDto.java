package backend.project.parkcontrol.dto.request;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewIncidentDto {
    private Integer id_ticket;
    private Integer incident_type;
    private String description;
    private String evidence_url;
    private Integer id_user_manager;
    //private Integer status;
    //private LocalDateTime date;
}
