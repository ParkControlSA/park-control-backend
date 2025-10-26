package backend.project.parkcontrol.dto.request;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ValidateCodeDto {

    private String code;

    private Integer userId;

}
