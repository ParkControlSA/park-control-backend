package backend.project.parkcontrol.service;

import backend.project.parkcontrol.dto.request.ValidateCodeDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.dto.response.UserInfoDto;
import backend.project.parkcontrol.exception.BusinessException;
import backend.project.parkcontrol.repository.crud.ValidationCodeCrud;
import backend.project.parkcontrol.repository.entities.RoleEntity;
import backend.project.parkcontrol.repository.entities.UserEntity;
import backend.project.parkcontrol.repository.entities.ValidationCode;
import backend.project.parkcontrol.utils.GeneralUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ValidationCodeServiceTest {

    @Mock
    private ValidationCodeCrud validationCodeCrud;

    @Mock
    private GeneralUtils generalUtils;

    @InjectMocks
    private ValidationCodeService service;

    @Test
    void createValidationCode_insertsWhenAbsent() {
        UserEntity user = new UserEntity();
        user.setId(1);

        ValidationCode validationCode = new ValidationCode();
        validationCode.setCode("123456");
        validationCode.setUser(user);
        Date exp = new Date(System.currentTimeMillis() + 5 * 60 * 1000);
        validationCode.setExpirationTime(exp);

        when(validationCodeCrud.getByUser(user.getId())).thenReturn(Optional.empty());

        service.createValidationCode(validationCode);

        ArgumentCaptor<ValidationCode> captor = ArgumentCaptor.forClass(ValidationCode.class);
        verify(validationCodeCrud).save(captor.capture());
        ValidationCode saved = captor.getValue();
        assertThat(saved.getCode()).isEqualTo("123456");
        assertThat(saved.getUser()).isEqualTo(user);
        assertThat(saved.getAttempts()).isEqualTo(0);
        assertThat(saved.getIsUsed()).isEqualTo(Boolean.FALSE);
        assertThat(saved.getExpirationTime()).isEqualTo(validationCode.getExpirationTime());
    }

    @Test
    void createValidationCode_updatesWhenPresent() {
        UserEntity user = new UserEntity();
        user.setId(2);

        ValidationCode existing = new ValidationCode();
        existing.setUser(user);
        existing.setCode("old");
        existing.setAttempts(2);
        existing.setIsUsed(Boolean.TRUE);
        existing.setExpirationTime(new Date());

        ValidationCode newData = new ValidationCode();
        newData.setUser(user);
        newData.setCode("new");
        Date newExp = new Date(System.currentTimeMillis() + 10 * 60 * 1000);
        newData.setExpirationTime(newExp);

        when(validationCodeCrud.getByUser(user.getId())).thenReturn(Optional.of(existing));

        service.createValidationCode(newData);

        ArgumentCaptor<ValidationCode> captor = ArgumentCaptor.forClass(ValidationCode.class);
        verify(validationCodeCrud).save(captor.capture());
        ValidationCode saved = captor.getValue();
        assertThat(saved.getCode()).isEqualTo("new");
        assertThat(saved.getUser()).isEqualTo(user);
        assertThat(saved.getAttempts()).isEqualTo(0);
        assertThat(saved.getIsUsed()).isEqualTo(Boolean.FALSE);
        assertThat(saved.getExpirationTime()).isEqualTo(newData.getExpirationTime());
    }

    @Test
    void getValidationCodeByUser_success() {
        Integer userId = 3;
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

        when(validationCodeCrud.getByUser(userId)).thenReturn(Optional.of(stored));

        ResponseSuccessfullyDto resp = service.getValidationCodeByUser(req);

        assertThat(resp.getCode()).isEqualTo(HttpStatus.OK);
        assertThat(resp.getMessage()).isEqualTo("Inicio de sesión exitoso");
        assertThat(resp.getBody()).isInstanceOf(UserInfoDto.class);
        UserInfoDto body = (UserInfoDto) resp.getBody();
        assertThat(body.getUserId()).isEqualTo(userId);
        assertThat(body.getUsername()).isEqualTo("john");
        assertThat(body.getRole()).isEqualTo("ADMIN");
        assertThat(body.getAutentication()).isTrue();
    }

    @Test
    void getValidationCodeByUser_notFound_throws() {
        Integer userId = 4;
        ValidateCodeDto req = new ValidateCodeDto();
        req.setUserId(userId);
        req.setCode("111111");

        when(validationCodeCrud.getByUser(userId)).thenReturn(Optional.empty());

        BusinessException ex = assertThrows(BusinessException.class, () -> service.getValidationCodeByUser(req));

        assertThat(ex.getCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(ex.getMessage()).isEqualTo("El usuario no cuenta con un código de validación");
    }

    @Test
    void getValidationCodeByUser_codeMismatch_throws() {
        Integer userId = 5;

        UserEntity user = new UserEntity();
        user.setId(userId);

        ValidationCode stored = new ValidationCode();
        stored.setCode("000000");
        stored.setUser(user);

        ValidateCodeDto req = new ValidateCodeDto();
        req.setUserId(userId);
        req.setCode("999999");

        when(validationCodeCrud.getByUser(userId)).thenReturn(Optional.of(stored));

        BusinessException ex = assertThrows(BusinessException.class, () -> service.getValidationCodeByUser(req));

        assertThat(ex.getCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(ex.getMessage()).isEqualTo("El código no le pertenece al usuario que intenta iniciar sesión.");
    }
}


