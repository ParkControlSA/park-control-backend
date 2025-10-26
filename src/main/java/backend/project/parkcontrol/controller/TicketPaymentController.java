package backend.project.parkcontrol.controller;

import backend.project.parkcontrol.controller.api.TicketPaymentApi;
import backend.project.parkcontrol.dto.request.NewTicketPaymentDto;
import backend.project.parkcontrol.dto.response.TicketPaymentDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.service.TicketPaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class TicketPaymentController implements TicketPaymentApi {

    private final TicketPaymentService ticketPaymentService;

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> createTicketPayment(NewTicketPaymentDto dto) {
        log.info("POST /ticketPayment");
        ResponseSuccessfullyDto resp = ticketPaymentService.createTicketPayment(dto);
        return new ResponseEntity<>(resp, resp.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> updateTicketPayment(TicketPaymentDto dto) {
        log.info("PUT /ticketPayment");
        ResponseSuccessfullyDto resp = ticketPaymentService.updateTicketPayment(dto);
        return new ResponseEntity<>(resp, resp.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> deleteTicketPayment(Integer id) {
        log.info("DELETE /ticketPayment/{}", id);
        ResponseSuccessfullyDto resp = ticketPaymentService.deleteTicketPayment(id);
        return new ResponseEntity<>(resp, resp.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getAllTicketPayments() {
        log.info("GET /ticketPayment/all");
        ResponseSuccessfullyDto resp = ticketPaymentService.getAllTicketPaymentListResponse();
        return new ResponseEntity<>(resp, resp.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getTicketPaymentById(Integer id) {
        log.info("GET /ticketPayment/{}", id);
        ResponseSuccessfullyDto resp = ticketPaymentService.getTicketPaymentByIdResponse(id);
        return new ResponseEntity<>(resp, resp.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getTicketPaymentByTicketId(Integer id) {
        log.info("GET /ticketPayment/ticket/{}", id);
        ResponseSuccessfullyDto resp = ticketPaymentService.getById_ticketResponse(id);
        return new ResponseEntity<>(resp, resp.getCode());
    }
}
