package backend.project.parkcontrol.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewBranchTemporaryPermitDto {
    private Integer id_temporary_permit;
    private Integer id_branch;
}
