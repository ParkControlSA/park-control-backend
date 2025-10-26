package backend.project.parkcontrol.controller.api;

import backend.project.parkcontrol.dto.request.NewTicketUsageDto;
import backend.project.parkcontrol.dto.response.TicketUsageDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/ticketUsage")
public interface TicketUsageApi {

    @PostMapping
    ResponseEntity<ResponseSuccessfullyDto> createTicketUsage(@RequestBody NewTicketUsageDto dto);

    @PutMapping
    ResponseEntity<ResponseSuccessfullyDto> updateTicketUsage(@RequestBody TicketUsageDto dto);

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseSuccessfullyDto> deleteTicketUsage(@PathVariable Integer id);

    @GetMapping("/all")
    ResponseEntity<ResponseSuccessfullyDto> getAllTicketUsages();

    @GetMapping("/{id}")
    ResponseEntity<ResponseSuccessfullyDto> getTicketUsageById(@PathVariable Integer id);

    @GetMapping("/ticket/{id}")
    ResponseEntity<ResponseSuccessfullyDto> getTicketUsageByTicketId(@PathVariable Integer id);
}
