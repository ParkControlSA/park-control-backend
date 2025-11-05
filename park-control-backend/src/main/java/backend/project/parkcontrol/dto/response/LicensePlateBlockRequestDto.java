package backend.project.parkcontrol.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LicensePlateBlockRequestDto {
    private Integer id;
    private Boolean is_4r;
    private Integer id_contract;
    //private Integer id_assigned;
    //private String old_plate;
    private String new_plate;
    private String evidence_url;
    private String note;
    //private LocalDateTime creation_date;
    //private Integer status;
}
