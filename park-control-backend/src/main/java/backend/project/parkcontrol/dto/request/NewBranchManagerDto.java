package backend.project.parkcontrol.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class NewBranchManagerDto {
    private Integer id_user;
    private Integer id_branch;
}
