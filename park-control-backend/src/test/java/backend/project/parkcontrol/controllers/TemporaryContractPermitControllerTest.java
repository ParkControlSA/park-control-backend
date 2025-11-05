package backend.project.parkcontrol.controllers;

import backend.project.parkcontrol.controller.TemporaryContractPermitController;
import backend.project.parkcontrol.dto.request.NewTemporaryContractPermitDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.dto.response.TemporaryContractPermitDto;
import backend.project.parkcontrol.service.TemporaryContractPermitService;
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
class TemporaryContractPermitControllerTest {

    @Mock
    private TemporaryContractPermitService service;

    @InjectMocks
    private TemporaryContractPermitController controller;

    private static final EasyRandom GENERATOR = new EasyRandom();

    // ==============================
    // CRUD TESTS
    // ==============================

    @Test
    void create_shouldReturnCreatedResponse() {
        NewTemporaryContractPermitDto dto = GENERATOR.nextObject(NewTemporaryContractPermitDto.class);
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.CREATED)
                .message("TemporaryContractPermit creado con éxito")
                .body(null)
                .build();
        when(service.createTemporaryContractPermit(dto)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.create(dto);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(result.getBody()).isEqualTo(response);
        verify(service).createTemporaryContractPermit(dto);
    }

    @Test
    void update_shouldReturnAcceptedResponse() {
        TemporaryContractPermitDto dto = GENERATOR.nextObject(TemporaryContractPermitDto.class);
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.ACCEPTED)
                .message("TemporaryContractPermit actualizado con éxito")
                .body(null)
                .build();
        when(service.updateTemporaryContractPermit(dto)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.update(dto);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        assertThat(result.getBody()).isEqualTo(response);
        verify(service).updateTemporaryContractPermit(dto);
    }

    @Test
    void delete_shouldReturnAcceptedResponse() {
        Integer id = 10;
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.ACCEPTED)
                .message("TemporaryContractPermit eliminado con éxito")
                .body(null)
                .build();
        when(service.deleteTemporaryContractPermit(id)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.delete(id);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        assertThat(result.getBody()).isEqualTo(response);
        verify(service).deleteTemporaryContractPermit(id);
    }

    // ==============================
    // GETTERS TESTS
    // ==============================

    @Test
    void getAll_shouldReturnOkResponse() {
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("Lista obtenida correctamente")
                .body(null)
                .build();
        when(service.getAllTemporaryContractPermitListResponse()).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.getAll();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(response);
        verify(service).getAllTemporaryContractPermitListResponse();
    }

    @Test
    void getById_shouldReturnOkResponse() {
        Integer id = 3;
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("TemporaryContractPermit encontrado")
                .body(null)
                .build();
        when(service.getTemporaryContractPermitByIdResponse(id)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.getById(id);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(response);
        verify(service).getTemporaryContractPermitByIdResponse(id);
    }

    @Test
    void getByContract_shouldReturnOkResponse() {
        Integer id = 7;
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("TemporaryContractPermit por contrato")
                .body(null)
                .build();
        when(service.getById_contractResponse(id)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.getByContract(id);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(response);
        verify(service).getById_contractResponse(id);
    }

    @Test
    void getByAssigned_shouldReturnOkResponse() {
        Integer id = 9;
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("TemporaryContractPermit por asignado")
                .body(null)
                .build();
        when(service.getById_assignedResponse(id)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.getByAssigned(id);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(response);
        verify(service).getById_assignedResponse(id);
    }
}
