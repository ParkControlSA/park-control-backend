package backend.project.parkcontrol.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AffiliatedBusinessDto {
    private Integer id;
    private String business_name;
    private Integer granted_hours;
    private Integer id_user;
}
