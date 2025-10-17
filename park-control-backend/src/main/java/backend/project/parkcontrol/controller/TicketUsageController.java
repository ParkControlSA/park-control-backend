package backend.project.parkcontrol.controller;

import backend.project.parkcontrol.controller.api.TicketUsageApi;
import backend.project.parkcontrol.dto.request.NewTicketUsageDto;
import backend.project.parkcontrol.dto.response.TicketUsageDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.service.TicketUsageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class TicketUsageController implements TicketUsageApi {

    private final TicketUsageService ticketUsageService;

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> createTicketUsage(NewTicketUsageDto dto) {
        log.info("POST /ticketUsage");
        ResponseSuccessfullyDto resp = ticketUsageService.createTicketUsage(dto);
        return new ResponseEntity<>(resp, resp.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> updateTicketUsage(TicketUsageDto dto) {
        log.info("PUT /ticketUsage");
        ResponseSuccessfullyDto resp = ticketUsageService.updateTicketUsage(dto);
        return new ResponseEntity<>(resp, resp.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> deleteTicketUsage(Integer id) {
        log.info("DELETE /ticketUsage/{}", id);
        ResponseSuccessfullyDto resp = ticketUsageService.deleteTicketUsage(id);
        return new ResponseEntity<>(resp, resp.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getAllTicketUsages() {
        log.info("GET /ticketUsage/all");
        ResponseSuccessfullyDto resp = ticketUsageService.getAllTicketUsageListResponse();
        return new ResponseEntity<>(resp, resp.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getTicketUsageById(Integer id) {
        log.info("GET /ticketUsage/{}", id);
        ResponseSuccessfullyDto resp = ticketUsageService.getTicketUsageByIdResponse(id);
        return new ResponseEntity<>(resp, resp.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getTicketUsageByTicketId(Integer id) {
        log.info("GET /ticketUsage/ticket/{}", id);
        ResponseSuccessfullyDto resp = ticketUsageService.getById_ticketResponse(id);
        return new ResponseEntity<>(resp, resp.getCode());
    }
}
