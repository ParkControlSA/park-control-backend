package backend.project.parkcontrol.dto.response;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RoleInfoDto {

    private Integer roleId;

    private String roleName;

}
