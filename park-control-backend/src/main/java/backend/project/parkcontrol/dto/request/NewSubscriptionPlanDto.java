package backend.project.parkcontrol.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class NewSubscriptionPlanDto {
    private String name;
    private Integer month_hours;
    private Integer daily_hours;
    private Double total_discount;
    private Double annual_discount;
}
