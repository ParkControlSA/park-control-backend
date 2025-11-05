package backend.project.parkcontrol.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewSubscriptionPlanDto {
    private String name;
    private Integer month_hours;
    private Integer daily_hours;
    private Double total_discount;
    private Double annual_discount;
}
