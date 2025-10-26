package backend.project.parkcontrol.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BranchManagerDto {
    private Integer id;
    private Integer id_user;
    private Integer id_branch;
}
