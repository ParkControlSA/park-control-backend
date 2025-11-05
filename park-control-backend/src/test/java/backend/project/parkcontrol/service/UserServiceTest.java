package backend.project.parkcontrol.service;

import backend.project.parkcontrol.dto.request.*;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.dto.response.UserInfoDto;
import backend.project.parkcontrol.exception.BusinessException;
import backend.project.parkcontrol.repository.crud.UserCrud;
import backend.project.parkcontrol.repository.entities.RoleEntity;
import backend.project.parkcontrol.repository.entities.UserEntity;
import backend.project.parkcontrol.utils.GeneralUtils;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock private UserCrud userCrud;
    @Mock private RolService rolService;
    @Mock private GeneralUtils utils;
    @Mock private EmailService emailService;
    @Mock private ValidationCodeService validationCodeService;
    @Mock private ValidationCodeRedisService validationCodeRedisService;

    @InjectMocks private UserService userService;

    private static final EasyRandom GENERATOR = new EasyRandom();

    // =============================================================
    // CREATE USER
    // =============================================================
    @Test
    void createUser_Success() {
        RoleEntity role = GENERATOR.nextObject(RoleEntity.class);
        NewUserDto dto = GENERATOR.nextObject(NewUserDto.class);
        String hashed = "hashed123";

        UserEntity saved = new UserEntity();
        saved.setId(10);
        saved.setRol(role);

        when(rolService.getRoleById(dto.getRol())).thenReturn(role);
        when(utils.hashPassword(dto.getPassword())).thenReturn(hashed);
        when(userCrud.save(any(UserEntity.class))).thenReturn(saved);

        ResponseSuccessfullyDto response = userService.createUser(dto);

        assertThat(response.getCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getMessage()).isEqualTo("El usuario fué creado correctamente");
        assertThat(((Map<?, ?>) response.getBody()).get("id")).isEqualTo(10);
        verify(userCrud).save(any(UserEntity.class));
    }

    @Test
    void createUser_WhenSaveFails_ThrowsBusinessException() {
        NewUserDto dto = GENERATOR.nextObject(NewUserDto.class);
        RoleEntity role = GENERATOR.nextObject(RoleEntity.class);
        when(rolService.getRoleById(dto.getRol())).thenReturn(role);
        when(utils.hashPassword(dto.getPassword())).thenReturn("hash");
        when(userCrud.save(any(UserEntity.class))).thenThrow(new RuntimeException("DB error"));

        BusinessException ex = assertThrows(BusinessException.class, () -> userService.createUser(dto));
        assertThat(ex.getCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(ex.getMessage()).isEqualTo("Error al intentar guardar al usuario.");
    }

    // =============================================================
    // UPDATE USER
    // =============================================================
    @Test
    void updateUser_Success() {
        UserUpdateDto dto = GENERATOR.nextObject(UserUpdateDto.class);
        UserEntity user = GENERATOR.nextObject(UserEntity.class);
        RoleEntity role = GENERATOR.nextObject(RoleEntity.class);

        when(userCrud.findById(dto.getId())).thenReturn(Optional.of(user));
        when(rolService.getRoleById(dto.getRol())).thenReturn(role);
        when(userCrud.save(any(UserEntity.class))).thenReturn(user);

        ResponseSuccessfullyDto response = userService.updateUser(dto);
        assertThat(response.getCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getMessage()).isEqualTo("Usuario actualizado correctamente");
        verify(userCrud).save(user);
    }

    // =============================================================
    // DELETE USER
    // =============================================================
    @Test
    void deleteUser_Success() {
        UserEntity user = GENERATOR.nextObject(UserEntity.class);
        when(userCrud.findById(anyInt())).thenReturn(Optional.of(user));

        ResponseSuccessfullyDto response = userService.deleteUser(1);
        assertThat(response.getCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getMessage()).isEqualTo("Usuario eliminado correctamente");
        verify(userCrud).delete(user);
    }

    // =============================================================
    // GET BY ID
    // =============================================================
    @Test
    void getUserById_Success() {
        UserEntity user = GENERATOR.nextObject(UserEntity.class);
        when(userCrud.findById(1)).thenReturn(Optional.of(user));

        UserEntity result = userService.getUserById(1);
        assertThat(result).isEqualTo(user);
    }

    @Test
    void getUserById_WhenNotFound_ThrowsException() {
        when(userCrud.findById(1)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> userService.getUserById(1));
    }

    @Test
    void getUserByIdResponse_Success() {
        UserEntity user = GENERATOR.nextObject(UserEntity.class);
        when(userCrud.findById(1)).thenReturn(Optional.of(user));

        ResponseSuccessfullyDto response = userService.getUserByIdResponse(1);
        assertThat(response.getCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(response.getMessage()).isEqualTo("Usuario encontrado con éxito");
        assertThat(response.getBody()).isEqualTo(user);
    }

    // =============================================================
    // GET ALL USERS
    // =============================================================
    @Test
    void getAllUsersListResponse_Success() {
        List<UserEntity> list = List.of(GENERATOR.nextObject(UserEntity.class));
        when(userCrud.findAll()).thenReturn(list);

        ResponseSuccessfullyDto response = userService.getAllUsersListResponse();
        assertThat(response.getCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(response.getMessage()).isEqualTo("Usuarios encontrados con éxito");
        assertThat(response.getBody()).isEqualTo(list);
    }

    // =============================================================
    // GET BY ROLE
    // =============================================================
    @Test
    void getUsersByRolId_Success() {
        List<UserEntity> list = List.of(GENERATOR.nextObject(UserEntity.class));
        when(userCrud.findById_role(1)).thenReturn(Optional.of(list));

        List<UserEntity> result = userService.getUsersByRolId(1);
        assertThat(result).isEqualTo(list);
    }

    @Test
    void getUsersByRolId_WhenEmptyOptional_ThrowsNoSuchElement() {
        when(userCrud.findById_role(1)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> userService.getUsersByRolId(1));
    }

    // =============================================================
    // LOGIN
    // =============================================================
    @Test
    void login_SuccessWithout2FA() {
        LoginDto login = GENERATOR.nextObject(LoginDto.class);
        UserEntity user = GENERATOR.nextObject(UserEntity.class);
        RoleEntity role = GENERATOR.nextObject(RoleEntity.class);
        user.setAuthentication(false);
        user.setRol(role);

        when(userCrud.getUserByUsername(login.getUsername())).thenReturn(Optional.of(user));
        when(utils.validatePassword(anyString(), anyString())).thenReturn(true);

        ResponseSuccessfullyDto response = userService.login(login);
        assertThat(response.getCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getMessage()).isEqualTo("Inicio de sesión exitoso");
    }

    @Test
    void login_SuccessWith2FA() {
        LoginDto login = GENERATOR.nextObject(LoginDto.class);
        UserEntity user = GENERATOR.nextObject(UserEntity.class);
        user.setAuthentication(true);
        user.setRol(GENERATOR.nextObject(RoleEntity.class));

        when(userCrud.getUserByUsername(login.getUsername())).thenReturn(Optional.of(user));
        when(utils.validatePassword(anyString(), anyString())).thenReturn(true);
        when(utils.generateVerificationCode()).thenReturn("654321");

        ResponseSuccessfullyDto response = userService.login(login);
        assertThat(response.getMessage()).contains("correo electrónico");
        verify(emailService).sendEmail(anyString(), anyString(), anyString());
        verify(validationCodeRedisService).saveCode(anyInt(), anyString(), anyLong());
    }

    @Test
    void login_UserNotFound_ThrowsBusinessException() {
        LoginDto login = GENERATOR.nextObject(LoginDto.class);
        when(userCrud.getUserByUsername(anyString())).thenReturn(Optional.empty());
        BusinessException ex = assertThrows(BusinessException.class, () -> userService.login(login));
        assertThat(ex.getMessage()).isEqualTo("Credenciales incorrectas");
    }

    @Test
    void login_InvalidPassword_ThrowsBusinessException() {
        LoginDto login = GENERATOR.nextObject(LoginDto.class);
        UserEntity user = GENERATOR.nextObject(UserEntity.class);
        when(userCrud.getUserByUsername(anyString())).thenReturn(Optional.of(user));
        when(utils.validatePassword(anyString(), anyString())).thenReturn(false);

        BusinessException ex = assertThrows(BusinessException.class, () -> userService.login(login));
        assertThat(ex.getMessage()).isEqualTo("Crendenciales incorrectas");
    }

    // =============================================================
    // SEND CODE
    // =============================================================
    @Test
    void sendCodeToUser_Success() {
        UserEntity user = GENERATOR.nextObject(UserEntity.class);
        when(utils.generateVerificationCode()).thenReturn("123456");

        userService.sendCodeToUser(user);
        verify(emailService).sendEmail(eq(user.getEmail()), anyString(), contains("123456"));
        verify(validationCodeRedisService).saveCode(anyInt(), anyString(), anyLong());
    }

    // =============================================================
    // VALIDATE CODE
    // =============================================================
    @Test
    void validateCode_Success() {
        ValidateCodeDto dto = new ValidateCodeDto();
        dto.setUserId(1);
        dto.setCode("111111");

        when(validationCodeRedisService.validateAndDelete(1, "111111")).thenReturn(true);
        ResponseSuccessfullyDto response = userService.validateCode(dto);

        assertThat(response.getCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getMessage()).isEqualTo("Código validado correctamente");
        verify(validationCodeRedisService).validateAndDelete(1, "111111");
    }

    @Test
    void validateCode_Invalid_ThrowsBusinessException() {
        ValidateCodeDto dto = new ValidateCodeDto();
        dto.setUserId(1);
        dto.setCode("000000");

        when(validationCodeRedisService.validateAndDelete(1, "000000")).thenReturn(false);
        BusinessException ex = assertThrows(BusinessException.class, () -> userService.validateCode(dto));
        assertThat(ex.getMessage()).isEqualTo("Código inválido o expirado");
    }

    // =============================================================
    // UPDATE AUTHENTICATION STATUS
    // =============================================================
    @Test
    void updateAuthenticationStatus_EnableSuccess() {
        UserEntity user = GENERATOR.nextObject(UserEntity.class);
        when(userCrud.findById(1)).thenReturn(Optional.of(user));
        when(userCrud.save(any())).thenReturn(user);

        ResponseSuccessfullyDto res = userService.updateAuthenticationStatus(1, true);
        assertThat(res.getMessage()).contains("activado");
    }

    @Test
    void updateAuthenticationStatus_SaveFails_ThrowsException() {
        UserEntity user = GENERATOR.nextObject(UserEntity.class);
        when(userCrud.findById(1)).thenReturn(Optional.of(user));
        when(userCrud.save(any())).thenThrow(new RuntimeException("DB error"));

        BusinessException ex = assertThrows(BusinessException.class, () -> userService.updateAuthenticationStatus(1, true));
        assertThat(ex.getMessage()).isEqualTo("Error al actualizar los permisos de autenticación en 2 pasos.");
    }
}
