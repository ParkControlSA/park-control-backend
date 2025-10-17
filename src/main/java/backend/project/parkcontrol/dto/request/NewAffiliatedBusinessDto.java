package backend.project.parkcontrol.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class NewAffiliatedBusinessDto {
    private String business_name;
    private Integer granted_hours;
    private Integer id_user;
}
