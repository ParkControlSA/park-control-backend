package backend.project.parkcontrol.controllers;

import backend.project.parkcontrol.controller.UserController;
import backend.project.parkcontrol.dto.request.*;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.service.UserService;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController controller;

    private static final EasyRandom GENERATOR = new EasyRandom();

    @Test
    void createUser_shouldReturnCreated() {
        NewUserDto dto = GENERATOR.nextObject(NewUserDto.class);
        ResponseSuccessfullyDto expected = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.CREATED)
                .message("Usuario creado con éxito")
                .build();

        when(userService.createUser(any(NewUserDto.class))).thenReturn(expected);

        ResponseEntity<ResponseSuccessfullyDto> response = controller.createUser(dto);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().getMessage()).isEqualTo("Usuario creado con éxito");
        verify(userService).createUser(dto);
    }

    @Test
    void updateUser_shouldReturnOk() {
        UserUpdateDto dto = GENERATOR.nextObject(UserUpdateDto.class);
        ResponseSuccessfullyDto expected = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("Usuario actualizado correctamente")
                .build();

        when(userService.updateUser(any(UserUpdateDto.class))).thenReturn(expected);

        ResponseEntity<ResponseSuccessfullyDto> response = controller.updateUser(dto);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getMessage()).isEqualTo("Usuario actualizado correctamente");
        verify(userService).updateUser(dto);
    }

    @Test
    void deleteUser_shouldReturnOk() {
        Integer id = 10;
        ResponseSuccessfullyDto expected = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("Usuario eliminado con éxito")
                .build();

        when(userService.deleteUser(id)).thenReturn(expected);

        ResponseEntity<ResponseSuccessfullyDto> response = controller.deleteUser(id);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getMessage()).isEqualTo("Usuario eliminado con éxito");
        verify(userService).deleteUser(id);
    }

    @Test
    void getUserById_shouldReturnFound() {
        Integer id = 5;
        ResponseSuccessfullyDto expected = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.FOUND)
                .message("Usuario encontrado")
                .build();

        when(userService.getUserByIdResponse(id)).thenReturn(expected);

        ResponseEntity<ResponseSuccessfullyDto> response = controller.getUserById(id);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(response.getBody().getMessage()).isEqualTo("Usuario encontrado");
        verify(userService).getUserByIdResponse(id);
    }

    @Test
    void getAllUsers_shouldReturnFound() {
        ResponseSuccessfullyDto expected = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.FOUND)
                .message("Usuarios encontrados")
                .build();

        when(userService.getAllUsersListResponse()).thenReturn(expected);

        ResponseEntity<ResponseSuccessfullyDto> response = controller.getAllUsers();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(response.getBody().getMessage()).isEqualTo("Usuarios encontrados");
        verify(userService).getAllUsersListResponse();
    }

    @Test
    void getUsersByRol_shouldReturnFound() {
        Integer rolId = 2;
        ResponseSuccessfullyDto expected = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.FOUND)
                .message("Usuarios del rol encontrados")
                .build();

        when(userService.getUsersByRolIdResponse(rolId)).thenReturn(expected);

        ResponseEntity<ResponseSuccessfullyDto> response = controller.getUsersByRol(rolId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(response.getBody().getMessage()).isEqualTo("Usuarios del rol encontrados");
        verify(userService).getUsersByRolIdResponse(rolId);
    }

    @Test
    void recoveryPassword_shouldReturnOk() {
        RecoveryPasswordDto dto = GENERATOR.nextObject(RecoveryPasswordDto.class);
        Integer userId = 7;
        ResponseSuccessfullyDto expected = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("Contraseña actualizada correctamente")
                .build();

        when(userService.recoveryPassword(dto, userId)).thenReturn(expected);

        ResponseEntity<ResponseSuccessfullyDto> response = controller.recoveryPassword(dto, userId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getMessage()).isEqualTo("Contraseña actualizada correctamente");
        verify(userService).recoveryPassword(dto, userId);
    }

    @Test
    void userForgotPassword_shouldReturnOk() {
        UserForgotPasswordDto dto = GENERATOR.nextObject(UserForgotPasswordDto.class);
        ResponseSuccessfullyDto expected = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("Correo de recuperación enviado")
                .build();

        when(userService.userForgotPassword(dto)).thenReturn(expected);

        ResponseEntity<ResponseSuccessfullyDto> response = controller.userForgotPassword(dto);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getMessage()).isEqualTo("Correo de recuperación enviado");
        verify(userService).userForgotPassword(dto);
    }

    @Test
    void login_shouldReturnOk() {
        LoginDto dto = GENERATOR.nextObject(LoginDto.class);
        ResponseSuccessfullyDto expected = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("Inicio de sesión exitoso")
                .build();

        when(userService.login(dto)).thenReturn(expected);

        ResponseEntity<ResponseSuccessfullyDto> response = controller.login(dto);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getMessage()).isEqualTo("Inicio de sesión exitoso");
        verify(userService).login(dto);
    }

    @Test
    void validateToken_shouldReturnOk() {
        ValidateCodeDto dto = GENERATOR.nextObject(ValidateCodeDto.class);
        ResponseSuccessfullyDto expected = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("Token válido")
                .build();

        when(userService.validateCode(dto)).thenReturn(expected);

        ResponseEntity<ResponseSuccessfullyDto> response = controller.validateToken(dto);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getMessage()).isEqualTo("Token válido");
        verify(userService).validateCode(dto);
    }

    @Test
    void updateAuthStatus_shouldReturnOk() {
        UpdateAuthStatusDto dto = new UpdateAuthStatusDto();
        dto.setStatus(true);
        Integer userId = 15;
        ResponseSuccessfullyDto expected = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("Estado de autenticación actualizado")
                .build();

        when(userService.updateAuthenticationStatus(userId, dto.getStatus())).thenReturn(expected);

        ResponseEntity<ResponseSuccessfullyDto> response = controller.updateAuthStatus(dto, userId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getMessage()).isEqualTo("Estado de autenticación actualizado");
        verify(userService).updateAuthenticationStatus(userId, dto.getStatus());
    }
}
