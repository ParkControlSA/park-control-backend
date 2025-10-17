package backend.project.parkcontrol.service;

import backend.project.parkcontrol.dto.request.NewIncidentDto;
import backend.project.parkcontrol.dto.response.IncidentDto;
import backend.project.parkcontrol.exception.BusinessException;
import backend.project.parkcontrol.repository.crud.IncidentCrud;
import backend.project.parkcontrol.repository.crud.UserCrud;
import backend.project.parkcontrol.repository.entities.Incident;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class IncidentService {
    private final IncidentCrud incidentCrud;
    private final UserCrud userCrud;
    private final TicketService ticketService;

    // FK helper: find by id_ticket
    public List<Incident> getById_ticket(Integer id){
        List<Incident> list = incidentCrud.findById_ticket(id);
        if(list.isEmpty()) throw new backend.project.parkcontrol.exception.BusinessException(org.springframework.http.HttpStatus.NOT_FOUND, "Not found");
        return list;
    }

    // FK helper: find by id_user_manager
    public List<Incident> getById_user_manager(Integer id){
        List<Incident> list = incidentCrud.findById_user_manager(id);
        if(list.isEmpty()) throw new backend.project.parkcontrol.exception.BusinessException(org.springframework.http.HttpStatus.NOT_FOUND, "Not found");
        return list;
    }

    public List<Incident> getAllIncidentList(){
        List<Incident> list = incidentCrud.findAll();
        if(list.isEmpty()) throw new BusinessException(HttpStatus.NOT_FOUND, "No records");
        return list;
    }

    public Incident getIncidentById(Integer id){
        Optional<Incident> optional = incidentCrud.findById(id);
        if(optional.isEmpty()) throw new BusinessException(HttpStatus.NOT_FOUND, "Incident not found");
        return optional.get();
    }

    public void deleteIncident(Integer id){
        Incident entity = getIncidentById(id);
        incidentCrud.delete(entity);
    }

    public void createIncident(NewIncidentDto dto){
        Incident e = new Incident();
        e.setTicket(ticketService.getTicketById(dto.getId_ticket()));
        e.setIncident_type(dto.getIncident_type());
        e.setDescription(dto.getDescription());
        e.setEvidence_url(dto.getEvidence_url());
        e.setUser(userCrud.findById(dto.getId_user_manager()).get());
        e.setStatus(dto.getStatus());
        e.setDate(dto.getDate());

        incidentCrud.save(e);
    }

    public void updateIncident(IncidentDto dto){
        Incident existing = getIncidentById(dto.getId());
        existing.setTicket(ticketService.getTicketById(dto.getId_ticket()));
        existing.setIncident_type(dto.getIncident_type());
        existing.setDescription(dto.getDescription());
        existing.setEvidence_url(dto.getEvidence_url());
        existing.setUser(userCrud.findById(dto.getId_user_manager()).get());
        existing.setStatus(dto.getStatus());
        existing.setDate(dto.getDate());

        incidentCrud.save(existing);
    }
}
