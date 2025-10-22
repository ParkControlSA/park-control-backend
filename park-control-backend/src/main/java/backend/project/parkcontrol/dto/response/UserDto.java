package backend.project.parkcontrol.dto.response;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserDto {

    private Integer id;

    private String nombre;

    private String email;

    private String username;

    private String password;

    private String telefono;

    private Integer rol;

}
