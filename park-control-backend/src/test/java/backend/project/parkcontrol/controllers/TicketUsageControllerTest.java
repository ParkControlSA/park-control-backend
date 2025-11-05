package backend.project.parkcontrol.controllers;

import backend.project.parkcontrol.controller.TicketUsageController;
import backend.project.parkcontrol.dto.request.NewTicketUsageDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.dto.response.TicketUsageDto;
import backend.project.parkcontrol.service.TicketUsageService;
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
class TicketUsageControllerTest {

    @Mock
    private TicketUsageService ticketUsageService;

    @InjectMocks
    private TicketUsageController controller;

    private static final EasyRandom GENERATOR = new EasyRandom();

    @Test
    void createTicketUsage_shouldReturnCreatedResponse() {
        NewTicketUsageDto dto = GENERATOR.nextObject(NewTicketUsageDto.class);
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.CREATED)
                .message("Ticket usage creado con éxito")
                .body(null)
                .build();
        when(ticketUsageService.createTicketUsage(dto)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.createTicketUsage(dto);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(result.getBody()).isEqualTo(response);
        verify(ticketUsageService).createTicketUsage(dto);
    }

    @Test
    void updateTicketUsage_shouldReturnAcceptedResponse() {
        TicketUsageDto dto = GENERATOR.nextObject(TicketUsageDto.class);
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.ACCEPTED)
                .message("Ticket usage actualizado con éxito")
                .body(null)
                .build();
        when(ticketUsageService.updateTicketUsage(dto)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.updateTicketUsage(dto);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        assertThat(result.getBody()).isEqualTo(response);
        verify(ticketUsageService).updateTicketUsage(dto);
    }

    @Test
    void deleteTicketUsage_shouldReturnAcceptedResponse() {
        Integer id = 5;
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.ACCEPTED)
                .message("Ticket usage eliminado con éxito")
                .body(null)
                .build();
        when(ticketUsageService.deleteTicketUsage(id)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.deleteTicketUsage(id);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        assertThat(result.getBody()).isEqualTo(response);
        verify(ticketUsageService).deleteTicketUsage(id);
    }

    @Test
    void getAllTicketUsages_shouldReturnOkResponse() {
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("Lista de ticket usages obtenida")
                .body(null)
                .build();
        when(ticketUsageService.getAllTicketUsageListResponse()).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.getAllTicketUsages();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(response);
        verify(ticketUsageService).getAllTicketUsageListResponse();
    }

    @Test
    void getTicketUsageById_shouldReturnOkResponse() {
        Integer id = 10;
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("Ticket usage encontrado")
                .body(null)
                .build();
        when(ticketUsageService.getTicketUsageByIdResponse(id)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.getTicketUsageById(id);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(response);
        verify(ticketUsageService).getTicketUsageByIdResponse(id);
    }

    @Test
    void getTicketUsageByTicketId_shouldReturnOkResponse() {
        Integer id = 20;
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("Ticket usage por ticket obtenido")
                .body(null)
                .build();
        when(ticketUsageService.getById_ticketResponse(id)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.getTicketUsageByTicketId(id);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(response);
        verify(ticketUsageService).getById_ticketResponse(id);
    }
}
