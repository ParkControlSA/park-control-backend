package backend.project.parkcontrol.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BranchTemporaryPermitDto {
    private Integer id;
    private Integer id_temporary_permit;
    private Integer id_branch;
}
