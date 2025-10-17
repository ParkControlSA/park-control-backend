package backend.project.parkcontrol.controller;

import backend.project.parkcontrol.controller.api.TicketBusinessUsageApi;
import backend.project.parkcontrol.dto.request.NewTicketBusinessUsageDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.dto.response.TicketBusinessUsageDto;
import backend.project.parkcontrol.service.TicketBusinessUsageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class TicketBusinessUsageController implements TicketBusinessUsageApi {

    private final TicketBusinessUsageService ticketBusinessUsageService;

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> createTicketBusinessUsage(NewTicketBusinessUsageDto dto) {
        log.info("POST /ticketBusinessUsage");
        return ResponseEntity.status(201).body(ticketBusinessUsageService.createTicketBusinessUsage(dto));
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> updateTicketBusinessUsage(TicketBusinessUsageDto dto) {
        log.info("PUT /ticketBusinessUsage");
        return ResponseEntity.status(202).body(ticketBusinessUsageService.updateTicketBusinessUsage(dto));
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> deleteTicketBusinessUsage(Integer id) {
        log.info("DELETE /ticketBusinessUsage/{}", id);
        return ResponseEntity.status(202).body(ticketBusinessUsageService.deleteTicketBusinessUsage(id));
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getAllTicketBusinessUsageList() {
        log.info("GET /ticketBusinessUsage/all");
        return ResponseEntity.ok(ticketBusinessUsageService.getAllTicketBusinessUsageListResponse());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getTicketBusinessUsageById(Integer id) {
        log.info("GET /ticketBusinessUsage/{}", id);
        return ResponseEntity.ok(ticketBusinessUsageService.getTicketBusinessUsageByIdResponse(id));
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getById_ticket_usage(Integer id) {
        log.info("GET /ticketBusinessUsage/ticketUsage/{}", id);
        return ResponseEntity.ok(ticketBusinessUsageService.getById_ticket_usageResponse(id));
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getById_affiliated_business(Integer id) {
        log.info("GET /ticketBusinessUsage/affiliatedBusiness/{}", id);
        return ResponseEntity.ok(ticketBusinessUsageService.getById_affiliated_businessResponse(id));
    }
}
