package backend.project.parkcontrol.service;

import backend.project.parkcontrol.dto.request.ValidateCodeDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.dto.response.UserInfoDto;
import backend.project.parkcontrol.exception.BusinessException;
import backend.project.parkcontrol.repository.entities.RoleEntity;
import backend.project.parkcontrol.repository.entities.UserEntity;
import backend.project.parkcontrol.repository.entities.ValidationCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;

import java.time.Duration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ValidationCodeServiceTest {

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private ValueOperations<String, Object> valueOps;

    @InjectMocks
    private ValidationCodeService service;

    @Test
    void createValidationCode_storesWithTTL() {
        // Arrange
        UserEntity user = new UserEntity();
        user.setId(1);

        ValidationCode validationCode = new ValidationCode();
        validationCode.setCode("123456");
        validationCode.setUser(user);

        when(redisTemplate.opsForValue()).thenReturn(valueOps);

        // Act
        service.createValidationCode(validationCode);

        // Assert
        verify(redisTemplate).opsForValue();
        verify(valueOps).set("validationCode:" + user.getId(), validationCode, Duration.ofMinutes(5));
    }

    @Test
    void getValidationCodeByUser_success() {
        // Arrange
        Integer userId = 2;
        String code = "654321";

        RoleEntity role = new RoleEntity();
        role.setRol("ADMIN");

        UserEntity user = new UserEntity();
        user.setId(userId);
        user.setUsername("john");
        user.setAuthentication(true);
        user.setRol(role);

        ValidationCode stored = new ValidationCode();
        stored.setCode(code);
        stored.setUser(user);

        ValidateCodeDto req = new ValidateCodeDto();
        req.setUserId(userId);
        req.setCode(code);

        when(redisTemplate.opsForValue()).thenReturn(valueOps);
        when(valueOps.get("validationCode:" + userId)).thenReturn(stored);

        // Act
        ResponseSuccessfullyDto resp = service.getValidationCodeByUser(req);

        // Assert
        assertThat(resp.getCode()).isEqualTo(HttpStatus.OK);
        assertThat(resp.getMessage()).isEqualTo("Inicio de sesión exitoso");
        assertThat(resp.getBody()).isInstanceOf(UserInfoDto.class);
        UserInfoDto body = (UserInfoDto) resp.getBody();
        assertThat(body.getUserId()).isEqualTo(userId);
        assertThat(body.getUsername()).isEqualTo("john");
        assertThat(body.getRole()).isEqualTo("ADMIN");
        assertThat(body.getAutentication()).isTrue();
        verify(redisTemplate).delete("validationCode:" + userId);
    }

    @Test
    void getValidationCodeByUser_notFound_throws() {
        // Arrange
        Integer userId = 3;
        ValidateCodeDto req = new ValidateCodeDto();
        req.setUserId(userId);
        req.setCode("111111");

        when(redisTemplate.opsForValue()).thenReturn(valueOps);
        when(valueOps.get("validationCode:" + userId)).thenReturn(null);

        // Act
        BusinessException ex = assertThrows(BusinessException.class, () -> service.getValidationCodeByUser(req));

        // Assert
        assertThat(ex.getCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(ex.getMessage()).isEqualTo("El usuario no cuenta con un código de validación o ha expirado");
        verify(redisTemplate, never()).delete(anyString());
    }

    @Test
    void getValidationCodeByUser_codeMismatch_throws() {
        // Arrange
        Integer userId = 4;

        UserEntity user = new UserEntity();
        user.setId(userId);

        ValidationCode stored = new ValidationCode();
        stored.setCode("000000");
        stored.setUser(user);

        ValidateCodeDto req = new ValidateCodeDto();
        req.setUserId(userId);
        req.setCode("999999");

        when(redisTemplate.opsForValue()).thenReturn(valueOps);
        when(valueOps.get("validationCode:" + userId)).thenReturn(stored);

        // Act
        BusinessException ex = assertThrows(BusinessException.class, () -> service.getValidationCodeByUser(req));

        // Assert
        assertThat(ex.getCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(ex.getMessage()).isEqualTo("El código no le pertenece al usuario que intenta iniciar sesión.");
        verify(redisTemplate, never()).delete(anyString());
    }
}


