package backend.project.parkcontrol.controller;

import backend.project.parkcontrol.controller.api.TicketApi;
import backend.project.parkcontrol.dto.request.NewTicketDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.dto.response.TicketDto;
import backend.project.parkcontrol.service.TicketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class TicketController implements TicketApi {

    private final TicketService ticketService;

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> createTicket(NewTicketDto dto) {
        log.info("POST /ticket");
        return ResponseEntity.status(201).body(ticketService.createTicket(dto));
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> updateTicket(TicketDto dto) {
        log.info("PUT /ticket");
        return ResponseEntity.status(202).body(ticketService.updateTicket(dto));
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> deleteTicket(Integer id) {
        log.info("DELETE /ticket/{}", id);
        return ResponseEntity.status(202).body(ticketService.deleteTicket(id));
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getAllTicketList() {
        log.info("GET /ticket/all");
        return ResponseEntity.ok(ticketService.getAllTicketListResponse());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getTicketById(Integer id) {
        log.info("GET /ticket/{}", id);
        return ResponseEntity.ok(ticketService.getTicketByIdResponse(id));
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getById_branch(Integer id) {
        log.info("GET /ticket/branch/{}", id);
        return ResponseEntity.ok(ticketService.getById_branchResponse(id));
    }
}
