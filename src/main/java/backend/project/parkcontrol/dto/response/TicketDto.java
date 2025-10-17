package backend.project.parkcontrol.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class TicketDto {
    private Integer id;
    private Integer id_branch;
    private String plate;
    private String card;
    private String qr;
    private LocalDateTime entry_date;
    private LocalDateTime exit_date;
    private Boolean is_4r;
    private Integer status;
}
