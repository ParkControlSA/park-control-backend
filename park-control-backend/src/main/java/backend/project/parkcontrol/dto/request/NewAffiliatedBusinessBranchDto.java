package backend.project.parkcontrol.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewAffiliatedBusinessBranchDto {
    private Integer id_affiliated_business;
    private Integer id_branch;
}
