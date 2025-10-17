package backend.project.parkcontrol.controller.api;

import backend.project.parkcontrol.dto.request.NewTicketPaymentDto;
import backend.project.parkcontrol.dto.response.TicketPaymentDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/ticketPayment")
public interface TicketPaymentApi {

    @PostMapping
    ResponseEntity<ResponseSuccessfullyDto> createTicketPayment(@RequestBody NewTicketPaymentDto dto);

    @PutMapping
    ResponseEntity<ResponseSuccessfullyDto> updateTicketPayment(@RequestBody TicketPaymentDto dto);

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseSuccessfullyDto> deleteTicketPayment(@PathVariable Integer id);

    @GetMapping("/all")
    ResponseEntity<ResponseSuccessfullyDto> getAllTicketPayments();

    @GetMapping("/{id}")
    ResponseEntity<ResponseSuccessfullyDto> getTicketPaymentById(@PathVariable Integer id);

    @GetMapping("/ticket/{id}")
    ResponseEntity<ResponseSuccessfullyDto> getTicketPaymentByTicketId(@PathVariable Integer id);
}
