package backend.project.parkcontrol.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketPaymentDto {
    private Integer id;
    private Integer id_ticket;
    private Double total_amount;
    private LocalDateTime date;
    private Integer payment_method;
}
