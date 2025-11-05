package backend.project.parkcontrol.controllers;

import backend.project.parkcontrol.controller.TicketController;
import backend.project.parkcontrol.dto.request.NewTicketDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.dto.response.TicketDto;
import backend.project.parkcontrol.enums.TicketStatus;
import backend.project.parkcontrol.service.TicketService;
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
class TicketControllerTest {

    @Mock
    private TicketService ticketService;

    @InjectMocks
    private TicketController controller;

    private static final EasyRandom GENERATOR = new EasyRandom();

    @Test
    void createTicket_shouldReturnCreatedResponse() {
        NewTicketDto dto = GENERATOR.nextObject(NewTicketDto.class);
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.CREATED)
                .message("Ticket creado con éxito")
                .body(null)
                .build();
        when(ticketService.createTicket(dto)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.createTicket(dto);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(result.getBody()).isEqualTo(response);
        verify(ticketService).createTicket(dto);
    }

    @Test
    void updateTicket_shouldReturnAcceptedResponse() {
        TicketDto dto = GENERATOR.nextObject(TicketDto.class);
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.CREATED)
                .message("Ticket actualizado con éxito")
                .body(null)
                .build();
        when(ticketService.updateTicket(dto)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.updateTicket(dto);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        assertThat(result.getBody()).isEqualTo(response);
        verify(ticketService).updateTicket(dto);
    }

    @Test
    void deleteTicket_shouldReturnAcceptedResponse() {
        Integer id = 10;
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.CREATED)
                .message("Ticket eliminado con éxito")
                .body(null)
                .build();
        when(ticketService.deleteTicket(id)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.deleteTicket(id);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        assertThat(result.getBody()).isEqualTo(response);
        verify(ticketService).deleteTicket(id);
    }

    @Test
    void getAllTicketList_shouldReturnOkResponse() {
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.CREATED)
                .message("Lista obtenida")
                .body(null)
                .build();
        when(ticketService.getAllTicketListResponse()).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.getAllTicketList();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(response);
        verify(ticketService).getAllTicketListResponse();
    }

    @Test
    void getTicketById_shouldReturnOkResponse() {
        Integer id = 1;
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.CREATED)
                .message("Ticket encontrado")
                .body(null)
                .build();
        when(ticketService.getTicketByIdResponse(id)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.getTicketById(id);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(response);
        verify(ticketService).getTicketByIdResponse(id);
    }

    @Test
    void getById_branch_shouldReturnOkResponse() {
        Integer id = 2;
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.CREATED)
                .message("Tickets por sucursal")
                .body(null)
                .build();
        when(ticketService.getById_branchResponse(id)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.getById_branch(id);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(response);
        verify(ticketService).getById_branchResponse(id);
    }

    @Test
    void getByPlate_shouldReturnOkResponse() {
        String plate = "ABC123";
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.CREATED)
                .message("Tickets por placa")
                .body(null)
                .build();
        when(ticketService.getByPlateResponse(plate)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.getByPlate(plate);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(response);
        verify(ticketService).getByPlateResponse(plate);
    }

    @Test
    void getByIdbranchActive_shouldReturnOkResponse() {
        Integer id = 3;
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.CREATED)
                .message("Tickets activos")
                .body(null)
                .build();
        when(ticketService.getByIdbranchStatusResponse(id, TicketStatus.ENTRADA_REGISTRADA.getValue()))
                .thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.getByIdbranchActive(id);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(response);
        verify(ticketService).getByIdbranchStatusResponse(id, TicketStatus.ENTRADA_REGISTRADA.getValue());
    }

    @Test
    void closeTicketId_shouldReturnAcceptedResponse() {
        Integer id = 5;
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.CREATED)
                .message("Ticket cerrado por ID")
                .body(null)
                .build();
        when(ticketService.closeTicket(String.valueOf(id), 5)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.closeTicketId(id);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        assertThat(result.getBody()).isEqualTo(response);
        verify(ticketService).closeTicket(String.valueOf(id), 5);
    }

    @Test
    void closeTicketPlate_shouldReturnAcceptedResponse() {
        String plate = "XYZ999";
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.CREATED)
                .message("Ticket cerrado por placa")
                .body(null)
                .build();
        when(ticketService.closeTicket(plate, 1)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.closeTicketPlate(plate);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        assertThat(result.getBody()).isEqualTo(response);
        verify(ticketService).closeTicket(plate, 1);
    }

    @Test
    void closeTicketCard_shouldReturnAcceptedResponse() {
        String card = "CARD-777";
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.CREATED)
                .message("Ticket cerrado por tarjeta")
                .body(null)
                .build();
        when(ticketService.closeTicket(card, 2)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.closeTicketCard(card);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        assertThat(result.getBody()).isEqualTo(response);
        verify(ticketService).closeTicket(card, 2);
    }
}
