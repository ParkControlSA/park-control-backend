package backend.project.parkcontrol.controllers;

import backend.project.parkcontrol.controller.RateAssignmentController;
import backend.project.parkcontrol.dto.request.NewRateAssignmentDto;
import backend.project.parkcontrol.dto.response.RateAssignmentDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.service.RateAssignmentService;
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
class RateAssignmentControllerTest {

    @Mock
    private RateAssignmentService rateAssignmentService;

    @InjectMocks
    private RateAssignmentController controller;

    private static final EasyRandom GENERATOR = new EasyRandom();

    // ==============================
    // CRUD TESTS
    // ==============================

    @Test
    void create_shouldReturnCreatedResponse() {
        NewRateAssignmentDto dto = GENERATOR.nextObject(NewRateAssignmentDto.class);
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.CREATED)
                .message("RateAssignment creado con éxito")
                .body(null)
                .build();
        when(rateAssignmentService.createRateAssignment(dto)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.create(dto);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(result.getBody()).isEqualTo(response);
        verify(rateAssignmentService).createRateAssignment(dto);
    }

    @Test
    void update_shouldReturnAcceptedResponse() {
        RateAssignmentDto dto = GENERATOR.nextObject(RateAssignmentDto.class);
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.ACCEPTED)
                .message("RateAssignment actualizado con éxito")
                .body(null)
                .build();
        when(rateAssignmentService.updateRateAssignment(dto)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.update(dto);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        assertThat(result.getBody()).isEqualTo(response);
        verify(rateAssignmentService).updateRateAssignment(dto);
    }

    @Test
    void delete_shouldReturnAcceptedResponse() {
        Integer id = 5;
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.ACCEPTED)
                .message("RateAssignment eliminado con éxito")
                .body(null)
                .build();
        when(rateAssignmentService.deleteRateAssignment(id)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.delete(id);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        assertThat(result.getBody()).isEqualTo(response);
        verify(rateAssignmentService).deleteRateAssignment(id);
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
        when(rateAssignmentService.getAllRateAssignmentListResponse()).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.getAll();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(response);
        verify(rateAssignmentService).getAllRateAssignmentListResponse();
    }

    @Test
    void getById_shouldReturnOkResponse() {
        Integer id = 10;
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("RateAssignment encontrado")
                .body(null)
                .build();
        when(rateAssignmentService.getRateAssignment(id)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.getById(id);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(response);
        verify(rateAssignmentService).getRateAssignment(id);
    }

    @Test
    void getRateAssignamentById_branchIsActive_shouldReturnOkResponse() {
        Integer id = 3;
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("RateAssignment activo encontrado")
                .body(null)
                .build();
        when(rateAssignmentService.getRateAssignamentById_branchIsActiveResponse(id)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.getRateAssignamentById_branchIsActive(id);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(response);
        verify(rateAssignmentService).getRateAssignamentById_branchIsActiveResponse(id);
    }

    @Test
    void getByBranchId_shouldReturnOkResponse() {
        Integer id = 7;
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("RateAssignments por sucursal")
                .body(null)
                .build();
        when(rateAssignmentService.getById_branchResponse(id)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.getByBranchId(id);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(response);
        verify(rateAssignmentService).getById_branchResponse(id);
    }
}
