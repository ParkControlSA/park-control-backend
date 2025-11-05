package backend.project.parkcontrol.service;

import backend.project.parkcontrol.dto.request.ValidateCodeDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.dto.response.UserInfoDto;
import backend.project.parkcontrol.exception.BusinessException;
import backend.project.parkcontrol.repository.entities.UserEntity;
import backend.project.parkcontrol.repository.entities.ValidationCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Slf4j
@RequiredArgsConstructor
@Service
public class ValidationCodeService {

    private final RedisTemplate<String, Object> redisTemplate;

    private static final Duration CODE_TTL = Duration.ofMinutes(5);

    public void createValidationCode(ValidationCode validationCode) {
        String key = "validationCode:" + validationCode.getUser().getId();
        redisTemplate.opsForValue().set(key, validationCode, CODE_TTL);
        log.info("Código de validación guardado en Redis para usuario {}", validationCode.getUser().getId());
    }

    public ResponseSuccessfullyDto getValidationCodeByUser(ValidateCodeDto validateCodeDto) {
        String key = "validationCode:" + validateCodeDto.getUserId();
        ValidationCode validationCode = (ValidationCode) redisTemplate.opsForValue().get(key);

        if (validationCode == null) {
            throw new BusinessException(HttpStatus.NOT_FOUND, "El usuario no cuenta con un código de validación o ha expirado");
        }

        if (!validateCodeDto.getCode().equals(validationCode.getCode())) {
            throw new BusinessException(HttpStatus.UNAUTHORIZED, "El código no le pertenece al usuario que intenta iniciar sesión.");
        }

        UserEntity user = validationCode.getUser();

        UserInfoDto userInfoDto = UserInfoDto.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .role(user.getRol().getRol())
                .autentication(user.getAuthentication())
                .build();

        // Eliminar el código luego de usarlo (opcional)
        redisTemplate.delete(key);

        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("Inicio de sesión exitoso")
                .body(userInfoDto)
                .build();
    }
}
