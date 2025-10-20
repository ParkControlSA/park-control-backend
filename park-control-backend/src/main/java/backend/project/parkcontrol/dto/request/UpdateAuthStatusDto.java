package backend.project.parkcontrol.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UpdateAuthStatusDto {

    private Boolean status;
}
