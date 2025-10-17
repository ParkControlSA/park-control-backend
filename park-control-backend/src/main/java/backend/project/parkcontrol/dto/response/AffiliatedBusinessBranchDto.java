package backend.project.parkcontrol.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AffiliatedBusinessBranchDto {
    private Integer id;
    private Integer id_affiliated_business;
    private Integer id_branch;
}
