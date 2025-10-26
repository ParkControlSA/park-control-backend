package backend.project.parkcontrol.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewAffiliatedBusinessDto {
    private String business_name;
    private Integer granted_hours;
    private Integer id_user;
}
