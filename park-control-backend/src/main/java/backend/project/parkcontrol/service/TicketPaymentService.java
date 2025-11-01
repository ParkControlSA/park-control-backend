package backend.project.parkcontrol.service;

import backend.project.parkcontrol.dto.request.NewTicketPaymentDto;
import backend.project.parkcontrol.dto.response.TicketPaymentDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.enums.TicketStatus;
import backend.project.parkcontrol.exception.BusinessException;
import backend.project.parkcontrol.repository.crud.TicketCrud;
import backend.project.parkcontrol.repository.crud.TicketPaymentCrud;
import backend.project.parkcontrol.repository.entities.Ticket;
import backend.project.parkcontrol.repository.entities.TicketPayment;
import backend.project.parkcontrol.repository.entities.TicketUsage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class TicketPaymentService {
    private final TicketPaymentCrud ticketpaymentCrud;
    private final TicketService ticketService;
    private final TicketCrud ticketCrud;
    private final TicketUsageService ticketUsageService;

    // ==============================
    // GETTERS
    // ==============================
    public List<TicketPayment> getById_ticket(Integer id){
        List<TicketPayment> list = ticketpaymentCrud.findById_ticket(id);
        return list;
    }

    public ResponseSuccessfullyDto getById_ticketResponse(Integer id){
        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.FOUND)
                .message("Registros encontrados con Éxito")
                .body(getById_ticket(id))
                .build();
    }

    public List<TicketPayment> getAllTicketPaymentList(){
        List<TicketPayment> list = ticketpaymentCrud.findAll();
        return list;
    }

    public ResponseSuccessfullyDto getAllTicketPaymentListResponse(){
        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.FOUND)
                .message("Registros encontrados con Éxito")
                .body(getAllTicketPaymentList())
                .build();
    }

    public TicketPayment getTicketPaymentById(Integer id){
        return ticketpaymentCrud.findById(id).get();
    }

    public ResponseSuccessfullyDto getTicketPaymentByIdResponse(Integer id){
        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.FOUND)
                .message("Registro encontrado con Éxito")
                .body(getTicketPaymentById(id))
                .build();
    }

    // ==============================
    // CRUD
    // ==============================
    public ResponseSuccessfullyDto createTicketPayment(NewTicketPaymentDto dto){
        TicketPayment e = new TicketPayment();
        //verifyTicketPayment(dto);
        TicketUsage ticketUsage = ticketUsageService.getById_ticket(dto.getId_ticket()).getFirst();
        e.setTicket(ticketService.getTicketById(dto.getId_ticket()));
        e.setTotal_amount(ticketUsage.getCustomer_amount());
        e.setDate(LocalDateTime.now(ZoneId.of("America/Guatemala")));
        e.setPayment_method(dto.getPayment_method());
        ticketpaymentCrud.save(e);
        changeTicketStatus(dto);
        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.CREATED)
                .message("Registro creado con Éxito")
                .build();
    }

    private void changeTicketStatus(NewTicketPaymentDto dto) {
        Ticket ticket = ticketService.getTicketById(dto.getId_ticket());
        ticket.setStatus(TicketStatus.TICKET_PAGADO.getValue());
        ticketCrud.save(ticket);
    }

    public ResponseSuccessfullyDto updateTicketPayment(TicketPaymentDto dto){
        TicketPayment existing = getTicketPaymentById(dto.getId());
        existing.setTicket(ticketService.getTicketById(dto.getId_ticket()));
        existing.setTotal_amount(dto.getTotal_amount());
        existing.setDate(dto.getDate());
        existing.setPayment_method(dto.getPayment_method());

        ticketpaymentCrud.save(existing);

        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("Registro actualizado con Éxito")
                .build();
    }

    public ResponseSuccessfullyDto deleteTicketPayment(Integer id){
        TicketPayment entity = getTicketPaymentById(id);
        ticketpaymentCrud.delete(entity);

        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("Registro eliminado con Éxito")
                .build();
    }
}
