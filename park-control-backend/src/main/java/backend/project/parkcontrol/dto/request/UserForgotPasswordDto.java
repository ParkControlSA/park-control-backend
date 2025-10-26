package backend.project.parkcontrol.dto.request;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserForgotPasswordDto {

    private String username;

}
