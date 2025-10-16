package backend.project.parkcontrol.controller;


import backend.project.parkcontrol.controller.api.RolApi;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.service.RolService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class RoleController implements RolApi {


    private final RolService roleService;

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getAllRoles() {
        log.info("GET role/all");
        ResponseSuccessfullyDto responseSuccessfullyDto = roleService.getAllRoles();
        return ResponseEntity.ok(responseSuccessfullyDto);
    }
}
