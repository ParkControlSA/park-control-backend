package backend.project.parkcontrol.dto.request;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserUpdateDto {

    private Integer id;

    private String nombre;

    private String email;

    private String username;

    private String telefono;

    private Integer rol;

}

