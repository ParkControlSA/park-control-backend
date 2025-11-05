package backend.project.parkcontrol.controllers;

import backend.project.parkcontrol.controller.RoleController;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.service.RolService;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleControllerTest {

    @Mock
    private RolService roleService;

    @InjectMocks
    private RoleController controller;

    private static final EasyRandom GENERATOR = new EasyRandom();

    @Test
    void getAllRoles_shouldReturnOkResponse() {
        // Arrange
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("Lista de roles obtenida con éxito")
                .body(GENERATOR.nextObject(Object.class))
                .build();

        when(roleService.getAllRoles()).thenReturn(response);

        // Act
        ResponseEntity<ResponseSuccessfullyDto> result = controller.getAllRoles();

        // Assert
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(response);
        verify(roleService).getAllRoles();
    }
}
