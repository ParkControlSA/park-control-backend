package backend.project.parkcontrol.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BranchManagerDto {
    private Integer id;
    private Integer id_user;
    private Integer id_branch;
}
