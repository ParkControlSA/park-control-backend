package backend.project.parkcontrol.controllers;

import backend.project.parkcontrol.controller.LicensePlateBlockRequestController;
import backend.project.parkcontrol.dto.request.NewLicensePlateBlockRequestDto;
import backend.project.parkcontrol.dto.response.LicensePlateBlockRequestDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.service.LicensePlateBlockRequestService;
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
class LicensePlateBlockRequestControllerTest {

    @Mock
    private LicensePlateBlockRequestService service;

    @InjectMocks
    private LicensePlateBlockRequestController controller;

    private static final EasyRandom GENERATOR = new EasyRandom();

    // ===========================================================
    // CRUD Tests
    // ===========================================================

    @Test
    void createLicensePlateBlockRequest_shouldReturnCreated() {
        NewLicensePlateBlockRequestDto dto = GENERATOR.nextObject(NewLicensePlateBlockRequestDto.class);
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.CREATED)
                .message("Solicitud creada con éxito")
                .body(null)
                .build();

        when(service.createLicensePlateBlockRequest(any())).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.createLicensePlateBlockRequest(dto);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(result.getBody()).isEqualTo(response);
        verify(service).createLicensePlateBlockRequest(dto);
    }

    @Test
    void updateLicensePlateBlockRequest_shouldReturnOk() {
        LicensePlateBlockRequestDto dto = GENERATOR.nextObject(LicensePlateBlockRequestDto.class);
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("Solicitud actualizada con éxito")
                .body(null)
                .build();

        when(service.updateLicensePlateBlockRequest(any())).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.updateLicensePlateBlockRequest(dto);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(response);
        verify(service).updateLicensePlateBlockRequest(dto);
    }

    @Test
    void deleteLicensePlateBlockRequest_shouldReturnOk() {
        Integer id = 5;
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("Solicitud eliminada con éxito")
                .body(null)
                .build();

        when(service.deleteLicensePlateBlockRequest(id)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.deleteLicensePlateBlockRequest(id);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(response);
        verify(service).deleteLicensePlateBlockRequest(id);
    }

    @Test
    void changeStatus_shouldReturnAccepted() {
        Integer idUser = 10;
        Integer id = 3;
        Integer status = 1;
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.ACCEPTED)
                .message("Estado actualizado con éxito")
                .body(null)
                .build();

        when(service.changeStatus(idUser, id, status)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.changeStatus(idUser, id, status);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        assertThat(result.getBody()).isEqualTo(response);
        verify(service).changeStatus(idUser, id, status);
    }

    // ===========================================================
    // GETTERS Tests
    // ===========================================================

    @Test
    void getAllLicensePlateBlockRequest_shouldReturnFound() {
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.FOUND)
                .message("Listado obtenido con éxito")
                .body(null)
                .build();

        when(service.getAllLicensePlateBlockRequestListResponse()).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.getAllLicensePlateBlockRequest();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(result.getBody()).isEqualTo(response);
        verify(service).getAllLicensePlateBlockRequestListResponse();
    }

    @Test
    void getAllByStatus_shouldReturnFound() {
        Integer id = 1;
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.FOUND)
                .message("Solicitudes por estado")
                .body(null)
                .build();

        when(service.getAllLicensePlateBlockRequestListByStatusResponse(id)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.getAllByStatus(id);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(result.getBody()).isEqualTo(response);
        verify(service).getAllLicensePlateBlockRequestListByStatusResponse(id);
    }

    @Test
    void getById_shouldReturnFound() {
        Integer id = 7;
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.FOUND)
                .message("Solicitud encontrada")
                .body(null)
                .build();

        when(service.getLicensePlateBlockRequest(id)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.getById(id);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(result.getBody()).isEqualTo(response);
        verify(service).getLicensePlateBlockRequest(id);
    }

    @Test
    void getByContractId_shouldReturnFound() {
        Integer id = 9;
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.FOUND)
                .message("Solicitudes por contrato")
                .body(null)
                .build();

        when(service.getById_contractResponse(id)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.getByContractId(id);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(result.getBody()).isEqualTo(response);
        verify(service).getById_contractResponse(id);
    }

    @Test
    void getByAssignedUserId_shouldReturnFound() {
        Integer id = 4;
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.FOUND)
                .message("Solicitudes asignadas al usuario")
                .body(null)
                .build();

        when(service.getById_assignedResponse(id)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.getByAssignedUserId(id);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(result.getBody()).isEqualTo(response);
        verify(service).getById_assignedResponse(id);
    }
}
