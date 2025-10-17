package backend.project.parkcontrol.service;

import backend.project.parkcontrol.dto.request.NewTicketDto;
import backend.project.parkcontrol.dto.response.TicketDto;
import backend.project.parkcontrol.exception.BusinessException;
import backend.project.parkcontrol.repository.crud.TicketCrud;
import backend.project.parkcontrol.repository.entities.Ticket;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class TicketService {
    private final TicketCrud ticketCrud;
    private final BranchService branchService;

    // FK helper: find by id_branch
    public List<Ticket> getById_branch(Integer id){
        List<Ticket> list = ticketCrud.findById_branch(id);
        if(list.isEmpty()) throw new BusinessException(org.springframework.http.HttpStatus.NOT_FOUND, "Not found");
        return list;
    }

    public List<Ticket> getAllTicketList(){
        List<Ticket> list = ticketCrud.findAll();
        if(list.isEmpty()) throw new BusinessException(HttpStatus.NOT_FOUND, "No records");
        return list;
    }

    public Ticket getTicketById(Integer id){
        Optional<Ticket> optional = ticketCrud.findById(id);
        if(optional.isEmpty()) throw new BusinessException(HttpStatus.NOT_FOUND, "Ticket not found");
        return optional.get();
    }

    public void deleteTicket(Integer id){
        Ticket entity = getTicketById(id);
        ticketCrud.delete(entity);
    }

    public void createTicket(NewTicketDto dto){
        Ticket e = new Ticket();
        e.setBranch(branchService.getBranchById(dto.getId_branch()));
        e.setPlate(dto.getPlate());
        e.setCard(dto.getCard());
        e.setQr(dto.getQr());
        e.setEntry_date(dto.getEntry_date());
        e.setExit_date(dto.getExit_date());
        e.setIs_4r(dto.getIs_4r());
        e.setStatus(dto.getStatus());

        ticketCrud.save(e);
    }

    public void updateTicket(TicketDto dto){
        Ticket existing = getTicketById(dto.getId());
        existing.setBranch(branchService.getBranchById(dto.getId_branch()));
        existing.setPlate(dto.getPlate());
        existing.setCard(dto.getCard());
        existing.setQr(dto.getQr());
        existing.setEntry_date(dto.getEntry_date());
        existing.setExit_date(dto.getExit_date());
        existing.setIs_4r(dto.getIs_4r());
        existing.setStatus(dto.getStatus());

        ticketCrud.save(existing);
    }
}
