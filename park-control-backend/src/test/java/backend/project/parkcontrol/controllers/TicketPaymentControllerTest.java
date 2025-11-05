package backend.project.parkcontrol.controllers;

import backend.project.parkcontrol.controller.TicketPaymentController;
import backend.project.parkcontrol.dto.request.NewTicketPaymentDto;
import backend.project.parkcontrol.dto.response.TicketPaymentDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.service.TicketPaymentService;
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
class TicketPaymentControllerTest {

    @Mock
    private TicketPaymentService ticketPaymentService;

    @InjectMocks
    private TicketPaymentController controller;

    private static final EasyRandom GENERATOR = new EasyRandom();

    // ==========================
    // CRUD TESTS
    // ==========================

    @Test
    void createTicketPayment_shouldReturnCreatedResponse() {
        NewTicketPaymentDto dto = GENERATOR.nextObject(NewTicketPaymentDto.class);
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.CREATED)
                .message("TicketPayment creado con éxito")
                .body(null)
                .build();

        when(ticketPaymentService.createTicketPayment(dto)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.createTicketPayment(dto);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(result.getBody()).isEqualTo(response);
        verify(ticketPaymentService).createTicketPayment(dto);
    }

    @Test
    void updateTicketPayment_shouldReturnAcceptedResponse() {
        TicketPaymentDto dto = GENERATOR.nextObject(TicketPaymentDto.class);
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.ACCEPTED)
                .message("TicketPayment actualizado con éxito")
                .body(null)
                .build();

        when(ticketPaymentService.updateTicketPayment(dto)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.updateTicketPayment(dto);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        assertThat(result.getBody()).isEqualTo(response);
        verify(ticketPaymentService).updateTicketPayment(dto);
    }

    @Test
    void deleteTicketPayment_shouldReturnAcceptedResponse() {
        Integer id = 10;
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.ACCEPTED)
                .message("TicketPayment eliminado con éxito")
                .body(null)
                .build();

        when(ticketPaymentService.deleteTicketPayment(id)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.deleteTicketPayment(id);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        assertThat(result.getBody()).isEqualTo(response);
        verify(ticketPaymentService).deleteTicketPayment(id);
    }

    // ==========================
    // GETTERS TESTS
    // ==========================

    @Test
    void getAllTicketPayments_shouldReturnOkResponse() {
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("Lista de pagos obtenida correctamente")
                .body(null)
                .build();

        when(ticketPaymentService.getAllTicketPaymentListResponse()).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.getAllTicketPayments();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(response);
        verify(ticketPaymentService).getAllTicketPaymentListResponse();
    }

    @Test
    void getTicketPaymentById_shouldReturnOkResponse() {
        Integer id = 1;
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("Pago encontrado por ID")
                .body(null)
                .build();

        when(ticketPaymentService.getTicketPaymentByIdResponse(id)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.getTicketPaymentById(id);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(response);
        verify(ticketPaymentService).getTicketPaymentByIdResponse(id);
    }

    @Test
    void getTicketPaymentByTicketId_shouldReturnOkResponse() {
        Integer id = 5;
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("Pagos obtenidos por ticket")
                .body(null)
                .build();

        when(ticketPaymentService.getById_ticketResponse(id)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.getTicketPaymentByTicketId(id);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(response);
        verify(ticketPaymentService).getById_ticketResponse(id);
    }
}
