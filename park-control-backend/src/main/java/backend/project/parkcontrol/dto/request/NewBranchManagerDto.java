package backend.project.parkcontrol.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewBranchManagerDto {
    private Integer id_user;
    private Integer id_branch;
}
