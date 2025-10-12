package backend.project.parkcontrol.controller.api;

import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/rol")
public interface RolApi {



    @GetMapping("/all")
    ResponseEntity<ResponseSuccessfullyDto> getAllRoles();

}
