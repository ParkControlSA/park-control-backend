package backend.project.parkcontrol.controller.api;

import backend.project.parkcontrol.dto.request.ValidateCodeDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.repository.entities.ValidationCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/validationCodes")
public interface ValidationCodeApi {

    @PostMapping
    ResponseEntity<ResponseSuccessfullyDto> createValidationCode(@RequestBody ValidationCode dto);

    @GetMapping("/user")
    ResponseEntity<ResponseSuccessfullyDto> getValidationCodeByUser(@RequestBody ValidateCodeDto dto);
}
