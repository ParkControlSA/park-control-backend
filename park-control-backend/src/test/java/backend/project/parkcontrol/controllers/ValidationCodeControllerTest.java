package backend.project.parkcontrol.controllers;
import backend.project.parkcontrol.controller.ValidationCodeController;
import backend.project.parkcontrol.dto.request.ValidateCodeDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.repository.entities.UserEntity;
import backend.project.parkcontrol.repository.entities.ValidationCode;
import backend.project.parkcontrol.service.ValidationCodeRedisService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ValidationCodeControllerTest {

    @Mock
    private ValidationCodeRedisService validationCodeRedisService;

    @InjectMocks
    private ValidationCodeController controller;

    // --- TEST 1: Usuario nulo o sin ID ---
    @Test
    void createValidationCode_shouldReturnBadRequest_ifUserIsNull() {
        ValidationCode dto = new ValidationCode();
        dto.setCode("ABC123");

        ResponseEntity<ResponseSuccessfullyDto> response = controller.createValidationCode(dto);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody().getMessage()).isEqualTo("El usuario es obligatorio para generar el código");
        verifyNoInteractions(validationCodeRedisService);
    }

    // --- TEST 2: Creación exitosa de código ---
    @Test
    void createValidationCode_shouldReturnCreated_ifValidUser() {
        UserEntity user = new UserEntity();
        user.setId(10);
        ValidationCode dto = new ValidationCode();
        dto.setUser(user);
        dto.setCode("XYZ99");

        ResponseEntity<ResponseSuccessfullyDto> response = controller.createValidationCode(dto);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().getMessage()).isEqualTo("Código de validación almacenado en Redis");
        verify(validationCodeRedisService).saveCode(user.getId(), dto.getCode(), 5);
    }

    // --- TEST 3: Código no encontrado ---
    @Test
    void getValidationCodeByUser_shouldReturnNotFound_ifCodeNotExists() {
        ValidateCodeDto dto = new ValidateCodeDto();
        dto.setUserId(5);
        dto.setCode("AAAAA");

        when(validationCodeRedisService.getCode(5)).thenReturn(null);

        ResponseEntity<ResponseSuccessfullyDto> response = controller.getValidationCodeByUser(dto);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody().getMessage()).isEqualTo("El código de validación expiró o no existe.");
        verify(validationCodeRedisService).getCode(5);
        verifyNoMoreInteractions(validationCodeRedisService);
    }

    // --- TEST 4: Código incorrecto ---
    @Test
    void getValidationCodeByUser_shouldReturnUnauthorized_ifCodeMismatch() {
        ValidateCodeDto dto = new ValidateCodeDto();
        dto.setUserId(7);
        dto.setCode("WRONG");

        when(validationCodeRedisService.getCode(7)).thenReturn("RIGHT");

        ResponseEntity<ResponseSuccessfullyDto> response = controller.getValidationCodeByUser(dto);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody().getMessage()).isEqualTo("El código no coincide.");
        verify(validationCodeRedisService).getCode(7);
        verifyNoMoreInteractions(validationCodeRedisService);
    }

    // --- TEST 5: Código correcto ---
    @Test
    void getValidationCodeByUser_shouldReturnOk_ifCodeMatches() {
        ValidateCodeDto dto = new ValidateCodeDto();
        dto.setUserId(9);
        dto.setCode("VALID123");

        when(validationCodeRedisService.getCode(9)).thenReturn("VALID123");

        ResponseEntity<ResponseSuccessfullyDto> response = controller.getValidationCodeByUser(dto);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getMessage()).isEqualTo("Código validado correctamente. Inicio de sesión exitoso.");

        verify(validationCodeRedisService).getCode(9);
        verify(validationCodeRedisService).deleteCode(9);
    }
}
