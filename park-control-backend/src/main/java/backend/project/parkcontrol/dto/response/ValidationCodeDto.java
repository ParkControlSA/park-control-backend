package backend.project.parkcontrol.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@Builder
public class ValidationCodeDto {
    private Integer id;
    private String code;
    private LocalTime expiration_time;
    private Boolean is_used;
    private Integer attemps;
    private Integer id_user;
}
