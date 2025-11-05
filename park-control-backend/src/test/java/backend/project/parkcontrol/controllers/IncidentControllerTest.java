package backend.project.parkcontrol.controllers;

import backend.project.parkcontrol.controller.IncidentController;
import backend.project.parkcontrol.dto.request.NewIncidentDto;
import backend.project.parkcontrol.dto.response.IncidentDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.service.IncidentService;
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
class IncidentControllerTest {

    @Mock
    private IncidentService incidentService;

    @InjectMocks
    private IncidentController controller;

    private static final EasyRandom GENERATOR = new EasyRandom();

    // =============================
    // CRUD TESTS
    // =============================

    @Test
    void createIncident_shouldReturnCreatedResponse() {
        NewIncidentDto dto = GENERATOR.nextObject(NewIncidentDto.class);
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.CREATED)
                .message("Incidente creado con éxito")
                .body(null)
                .build();

        when(incidentService.createIncident(dto)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.createIncident(dto);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(result.getBody()).isEqualTo(response);
        verify(incidentService).createIncident(dto);
    }

    @Test
    void updateIncident_shouldReturnOkResponse() {
        IncidentDto dto = GENERATOR.nextObject(IncidentDto.class);
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("Incidente actualizado con éxito")
                .body(null)
                .build();

        when(incidentService.updateIncident(dto)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.updateIncident(dto);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(response);
        verify(incidentService).updateIncident(dto);
    }

    @Test
    void solveIncident_shouldReturnAcceptedResponse() {
        Integer id = 12;
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.ACCEPTED)
                .message("Incidente resuelto con éxito")
                .body(null)
                .build();

        when(incidentService.solveIncident(id)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.solveIncident(id);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        assertThat(result.getBody()).isEqualTo(response);
        verify(incidentService).solveIncident(id);
    }

    @Test
    void deleteIncident_shouldReturnOkResponse() {
        Integer id = 7;
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("Incidente eliminado con éxito")
                .body(null)
                .build();

        when(incidentService.deleteIncident(id)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.deleteIncident(id);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(response);
        verify(incidentService).deleteIncident(id);
    }

    // =============================
    // GETTERS TESTS
    // =============================

    @Test
    void getAllIncidents_shouldReturnFoundResponse() {
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.FOUND)
                .message("Lista de incidentes obtenida con éxito")
                .body(null)
                .build();

        when(incidentService.getAllIncidentListResponse()).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.getAllIncidents();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(result.getBody()).isEqualTo(response);
        verify(incidentService).getAllIncidentListResponse();
    }

    @Test
    void getIncidentById_shouldReturnFoundResponse() {
        Integer id = 3;
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.FOUND)
                .message("Incidente encontrado con éxito")
                .body(null)
                .build();

        when(incidentService.getIncident(id)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.getIncidentById(id);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(result.getBody()).isEqualTo(response);
        verify(incidentService).getIncident(id);
    }

    @Test
    void getIncidentsByTicketId_shouldReturnFoundResponse() {
        Integer id = 5;
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.FOUND)
                .message("Incidentes por ticket obtenidos con éxito")
                .body(null)
                .build();

        when(incidentService.getById_ticketResponse(id)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.getIncidentsByTicketId(id);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(result.getBody()).isEqualTo(response);
        verify(incidentService).getById_ticketResponse(id);
    }

    @Test
    void getIncidentsByUserManagerId_shouldReturnFoundResponse() {
        Integer id = 9;
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.FOUND)
                .message("Incidentes por usuario manager obtenidos con éxito")
                .body(null)
                .build();

        when(incidentService.getById_user_managerResponse(id)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.getIncidentsByUserManagerId(id);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(result.getBody()).isEqualTo(response);
        verify(incidentService).getById_user_managerResponse(id);
    }
}
