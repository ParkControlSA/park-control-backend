package backend.project.parkcontrol.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class NewContractPaymentDto {
    private Integer id_contract;
    private Double subtotal;
    private Double monthly_discount;
    private Double annual_discount;
    private Double total;
    private LocalDateTime date;
    private Integer payment_method;
}
