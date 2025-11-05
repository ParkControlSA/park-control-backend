package backend.project.parkcontrol.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContractPaymentDto {
    private Integer id;
    private Integer id_contract;
    private Double subtotal;
    private Double monthly_discount;
    private Double annual_discount;
    private Double total;
    private LocalDateTime date;
    private Integer payment_method;
}
