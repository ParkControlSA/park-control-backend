package backend.project.parkcontrol.dto.request;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateAffiliateCommerceDto {

    private String nombre;

    private Integer idUsuario;

    private Integer horasOtorgadas;

}
