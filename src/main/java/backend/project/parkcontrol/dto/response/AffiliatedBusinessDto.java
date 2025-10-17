package backend.project.parkcontrol.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AffiliatedBusinessDto {
    private Integer id;
    private String business_name;
    private Integer granted_hours;
    private Integer id_user;
}
