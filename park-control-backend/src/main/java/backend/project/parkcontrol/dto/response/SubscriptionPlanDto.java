package backend.project.parkcontrol.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionPlanDto {
    private Integer id;
    private String name;
    private Integer month_hours;
    private Integer daily_hours;
    private Double total_discount;
    private Double annual_discount;
}
