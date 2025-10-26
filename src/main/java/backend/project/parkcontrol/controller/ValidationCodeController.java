package backend.project.parkcontrol.controller;

import backend.project.parkcontrol.controller.api.ValidationCodeApi;
import backend.project.parkcontrol.dto.request.ValidateCodeDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.repository.entities.ValidationCode;
import backend.project.parkcontrol.service.ValidationCodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ValidationCodeController implements ValidationCodeApi {

    private final ValidationCodeService validationCodeService;

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> createValidationCode(ValidationCode dto) {
        log.info("POST /validationCodes");
        validationCodeService.createValidationCode(dto); // solo crea el código
        ResponseSuccessfullyDto resp = ResponseSuccessfullyDto.builder()
                .code(org.springframework.http.HttpStatus.CREATED)
                .message("Código de validación creado")
                .build();
        return new ResponseEntity<>(resp, resp.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getValidationCodeByUser(ValidateCodeDto dto) {
        log.info("POST /validationCodes/user");
        ResponseSuccessfullyDto resp = validationCodeService.getValidationCodeByUser(dto);
        return new ResponseEntity<>(resp, resp.getCode());
    }
}
