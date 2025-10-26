package backend.project.parkcontrol.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AffiliatedBusinessBranchDto {
    private Integer id;
    private Integer id_affiliated_business;
    private Integer id_branch;
}
