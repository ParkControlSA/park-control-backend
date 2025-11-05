package backend.project.parkcontrol.dto.request;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewTicketDto {
    private Integer id_branch;
    private String plate;
    //private String card;
    //private String qr;
    //private LocalDateTime entry_date;
    //private LocalDateTime exit_date;
    private Boolean is_4r;
    //private Integer status;
}
