package backend.project.parkcontrol.dto.request;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAuthStatusDto {

    private Boolean status;
}
