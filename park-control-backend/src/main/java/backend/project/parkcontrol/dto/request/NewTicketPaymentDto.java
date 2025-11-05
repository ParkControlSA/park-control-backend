package backend.project.parkcontrol.dto.request;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewTicketPaymentDto {
    private Integer id_ticket;
    //private Double total_amount;
    //private LocalDateTime date;
    private Integer payment_method;
}
