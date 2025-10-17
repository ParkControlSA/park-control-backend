package backend.project.parkcontrol.controller.api;

import backend.project.parkcontrol.dto.request.NewTicketDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.dto.response.TicketDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RequestMapping("/ticket")
public interface TicketApi {

    // CRUD
    @PostMapping
    ResponseEntity<ResponseSuccessfullyDto> createTicket(@RequestBody NewTicketDto dto);

    @PutMapping
    ResponseEntity<ResponseSuccessfullyDto> updateTicket(@RequestBody TicketDto dto);

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseSuccessfullyDto> deleteTicket(@PathVariable Integer id);

    // GETTERS
    @GetMapping("/all")
    ResponseEntity<ResponseSuccessfullyDto> getAllTicketList();

    @GetMapping("/{id}")
    ResponseEntity<ResponseSuccessfullyDto> getTicketById(@PathVariable Integer id);

    // Filtro por sucursal
    @GetMapping("/branch/{id}")
    ResponseEntity<ResponseSuccessfullyDto> getById_branch(@PathVariable Integer id);
}
