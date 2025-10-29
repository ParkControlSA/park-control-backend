package backend.project.parkcontrol.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class NewTicketPaymentDto {
    private Integer id_ticket;
    //private Double total_amount;
    //private LocalDateTime date;
    private Integer payment_method;
}
