package backend.project.parkcontrol.controller.api;

import backend.project.parkcontrol.dto.request.NewTicketBusinessUsageDto;
import backend.project.parkcontrol.dto.response.TicketBusinessUsageDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/ticketBusinessUsage")
public interface TicketBusinessUsageApi {

    // CRUD
    @PostMapping
    ResponseEntity<ResponseSuccessfullyDto> createTicketBusinessUsage(@RequestBody NewTicketBusinessUsageDto dto);

    @PutMapping
    ResponseEntity<ResponseSuccessfullyDto> updateTicketBusinessUsage(@RequestBody TicketBusinessUsageDto dto);

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseSuccessfullyDto> deleteTicketBusinessUsage(@PathVariable Integer id);

    // GETTERS
    @GetMapping("/all")
    ResponseEntity<ResponseSuccessfullyDto> getAllTicketBusinessUsageList();

    @GetMapping("/{id}")
    ResponseEntity<ResponseSuccessfullyDto> getTicketBusinessUsageById(@PathVariable Integer id);

    // Filtros
    @GetMapping("/ticketUsage/{id}")
    ResponseEntity<ResponseSuccessfullyDto> getById_ticket_usage(@PathVariable Integer id);

    @GetMapping("/affiliatedBusiness/{id}")
    ResponseEntity<ResponseSuccessfullyDto> getById_affiliated_business(@PathVariable Integer id);
}
