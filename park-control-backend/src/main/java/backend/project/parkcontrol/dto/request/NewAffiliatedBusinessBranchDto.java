package backend.project.parkcontrol.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class NewAffiliatedBusinessBranchDto {
    private Integer id_affiliated_business;
    private Integer id_branch;
}
