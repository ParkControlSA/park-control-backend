package backend.project.parkcontrol.controllers;

import backend.project.parkcontrol.controller.TicketBusinessUsageController;
import backend.project.parkcontrol.dto.request.NewTicketBusinessUsageDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.dto.response.TicketBusinessUsageDto;
import backend.project.parkcontrol.service.TicketBusinessUsageService;
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
class TicketBusinessUsageControllerTest {

    @Mock
    private TicketBusinessUsageService service;

    @InjectMocks
    private TicketBusinessUsageController controller;

    private static final EasyRandom GENERATOR = new EasyRandom();

    @Test
    void createTicketBusinessUsage_shouldReturnCreatedResponse() {
        NewTicketBusinessUsageDto dto = GENERATOR.nextObject(NewTicketBusinessUsageDto.class);
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.CREATED)
                .message("Creado con éxito")
                .body(null)
                .build();
        when(service.createTicketBusinessUsage(dto)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.createTicketBusinessUsage(dto);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(result.getBody()).isEqualTo(response);
        verify(service).createTicketBusinessUsage(dto);
    }

    @Test
    void updateTicketBusinessUsage_shouldReturnAcceptedResponse() {
        TicketBusinessUsageDto dto = GENERATOR.nextObject(TicketBusinessUsageDto.class);
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.ACCEPTED)
                .message("Actualizado con éxito")
                .body(null)
                .build();
        when(service.updateTicketBusinessUsage(dto)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.updateTicketBusinessUsage(dto);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        assertThat(result.getBody()).isEqualTo(response);
        verify(service).updateTicketBusinessUsage(dto);
    }

    @Test
    void deleteTicketBusinessUsage_shouldReturnAcceptedResponse() {
        Integer id = 42;
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.ACCEPTED)
                .message("Eliminado con éxito")
                .body(null)
                .build();
        when(service.deleteTicketBusinessUsage(id)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.deleteTicketBusinessUsage(id);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        assertThat(result.getBody()).isEqualTo(response);
        verify(service).deleteTicketBusinessUsage(id);
    }

    @Test
    void getAllTicketBusinessUsageList_shouldReturnOkResponse() {
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("Lista obtenida")
                .body(null)
                .build();
        when(service.getAllTicketBusinessUsageListResponse()).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.getAllTicketBusinessUsageList();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(response);
        verify(service).getAllTicketBusinessUsageListResponse();
    }

    @Test
    void getTicketBusinessUsageById_shouldReturnOkResponse() {
        Integer id = 10;
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("Encontrado por ID")
                .body(null)
                .build();
        when(service.getTicketBusinessUsageByIdResponse(id)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.getTicketBusinessUsageById(id);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(response);
        verify(service).getTicketBusinessUsageByIdResponse(id);
    }

    @Test
    void getById_ticket_usage_shouldReturnOkResponse() {
        Integer id = 5;
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("Ticket Usage encontrado")
                .body(null)
                .build();
        when(service.getById_ticket_usageResponse(id)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.getById_ticket_usage(id);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(response);
        verify(service).getById_ticket_usageResponse(id);
    }

    @Test
    void getById_affiliated_business_shouldReturnOkResponse() {
        Integer id = 7;
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("Afiliado encontrado")
                .body(null)
                .build();
        when(service.getById_affiliated_businessResponse(id)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.getById_affiliated_business(id);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(response);
        verify(service).getById_affiliated_businessResponse(id);
    }
}
