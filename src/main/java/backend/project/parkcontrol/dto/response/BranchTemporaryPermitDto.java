package backend.project.parkcontrol.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BranchTemporaryPermitDto {
    private Integer id;
    private Integer id_temporary_permit;
    private Integer id_branch;
}
