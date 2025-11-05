package backend.project.parkcontrol.dto.request;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ValidateCodeDto {

    private String code;

    private Integer userId;

}
