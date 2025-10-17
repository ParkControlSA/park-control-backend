package backend.project.parkcontrol.service;

import backend.project.parkcontrol.dto.request.NewTicketPaymentDto;
import backend.project.parkcontrol.dto.response.TicketPaymentDto;
import backend.project.parkcontrol.exception.BusinessException;
import backend.project.parkcontrol.repository.crud.TicketPaymentCrud;
import backend.project.parkcontrol.repository.entities.TicketPayment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.*;



@Slf4j
@RequiredArgsConstructor
@Service
public class TicketPaymentService {
    private final TicketPaymentCrud ticketpaymentCrud;
    private final TicketService ticketService;
    // FK helper: find by id_ticket
    public List<TicketPayment> getById_ticket(Integer id){
        List<TicketPayment> list = ticketpaymentCrud.findById_ticket(id);
        if(list.isEmpty()) throw new BusinessException(org.springframework.http.HttpStatus.NOT_FOUND, "Not found");
        return list;
    }

    public List<TicketPayment> getAllTicketPaymentList(){
        List<TicketPayment> list = ticketpaymentCrud.findAll();
        if(list.isEmpty()) throw new BusinessException(HttpStatus.NOT_FOUND, "No records");
        return list;
    }

    public TicketPayment getTicketPaymentById(Integer id){
        Optional<TicketPayment> optional = ticketpaymentCrud.findById(id);
        if(optional.isEmpty()) throw new BusinessException(HttpStatus.NOT_FOUND, "TicketPayment not found");
        return optional.get();
    }

    public void deleteTicketPayment(Integer id){
        TicketPayment entity = getTicketPaymentById(id);
        ticketpaymentCrud.delete(entity);
    }

    public void createTicketPayment(NewTicketPaymentDto dto){
        TicketPayment e = new TicketPayment();
        e.setTicket(ticketService.getTicketById(dto.getId_ticket()));
        e.setTotal_amount(dto.getTotal_amount());
        e.setDate(dto.getDate());
        e.setPayment_method(dto.getPayment_method());

        ticketpaymentCrud.save(e);
    }

    public void updateTicketPayment(TicketPaymentDto dto){
        TicketPayment existing = getTicketPaymentById(dto.getId());
        existing.setTicket(ticketService.getTicketById(dto.getId_ticket()));
        existing.setTotal_amount(dto.getTotal_amount());
        existing.setDate(dto.getDate());
        existing.setPayment_method(dto.getPayment_method());

        ticketpaymentCrud.save(existing);
    }
}
