package backend.project.parkcontrol.controller;

import backend.project.parkcontrol.controller.api.ValidationCodeApi;
import backend.project.parkcontrol.dto.request.ValidateCodeDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.repository.entities.ValidationCode;
import backend.project.parkcontrol.service.ValidationCodeRedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ValidationCodeController implements ValidationCodeApi {

    private final ValidationCodeRedisService validationCodeRedisService;

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> createValidationCode(ValidationCode dto) {
        log.info("POST /validationCodes");

        if (dto.getUser() == null || dto.getUser().getId() == null) {
            return new ResponseEntity<>(
                    ResponseSuccessfullyDto.builder()
                            .code(HttpStatus.BAD_REQUEST)
                            .message("El usuario es obligatorio para generar el código")
                            .build(),
                    HttpStatus.BAD_REQUEST
            );
        }

        // Guardar el código con TTL de 5 minutos (puedes ajustar)
        validationCodeRedisService.saveCode(dto.getUser().getId(), dto.getCode(), 5);

        ResponseSuccessfullyDto resp = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.CREATED)
                .message("Código de validación almacenado en Redis")
                .build();

        return new ResponseEntity<>(resp, resp.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getValidationCodeByUser(ValidateCodeDto dto) {
        log.info("POST /validationCodes/user");

        String storedCode = validationCodeRedisService.getCode(dto.getUserId());

        if (storedCode == null) {
            return new ResponseEntity<>(
                    ResponseSuccessfullyDto.builder()
                            .code(HttpStatus.NOT_FOUND)
                            .message("El código de validación expiró o no existe.")
                            .build(),
                    HttpStatus.NOT_FOUND
            );
        }

        if (!storedCode.equals(dto.getCode())) {
            return new ResponseEntity<>(
                    ResponseSuccessfullyDto.builder()
                            .code(HttpStatus.UNAUTHORIZED)
                            .message("El código no coincide.")
                            .build(),
                    HttpStatus.UNAUTHORIZED
            );
        }

        // Si el código es correcto, se elimina
        validationCodeRedisService.deleteCode(dto.getUserId());

        ResponseSuccessfullyDto resp = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("Código validado correctamente. Inicio de sesión exitoso.")
                .build();

        return new ResponseEntity<>(resp, resp.getCode());
    }
}
