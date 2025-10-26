package backend.project.parkcontrol.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class NewBranchTemporaryPermitDto {
    private Integer id_temporary_permit;
    private Integer id_branch;
}
