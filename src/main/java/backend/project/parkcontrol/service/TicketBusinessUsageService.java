package backend.project.parkcontrol.service;

import backend.project.parkcontrol.dto.request.NewTicketBusinessUsageDto;
import backend.project.parkcontrol.dto.response.TicketBusinessUsageDto;
import backend.project.parkcontrol.exception.BusinessException;
import backend.project.parkcontrol.repository.crud.TicketBusinessUsageCrud;
import backend.project.parkcontrol.repository.entities.TicketBusinessUsage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class TicketBusinessUsageService {
    private final TicketBusinessUsageCrud ticketbusinessusageCrud;
    private final AffiliatedBusinessService affiliatedBusinessService;
    private final TicketUsageService ticketUsageService;

    // FK helper: find by id_ticket_usage
    public List<TicketBusinessUsage> getById_ticket_usage(Integer id){
        List<TicketBusinessUsage> list = ticketbusinessusageCrud.findById_ticket_usage(id);
        if(list.isEmpty()) throw new backend.project.parkcontrol.exception.BusinessException(org.springframework.http.HttpStatus.NOT_FOUND, "Not found");
        return list;
    }

    // FK helper: find by id_affiliated_business
    public List<TicketBusinessUsage> getById_affiliated_business(Integer id){
        List<TicketBusinessUsage> list = ticketbusinessusageCrud.findById_affiliated_business(id);
        if(list.isEmpty()) throw new backend.project.parkcontrol.exception.BusinessException(org.springframework.http.HttpStatus.NOT_FOUND, "Not found");
        return list;
    }

    public List<TicketBusinessUsage> getAllTicketBusinessUsageList(){
        List<TicketBusinessUsage> list = ticketbusinessusageCrud.findAll();
        if(list.isEmpty()) throw new BusinessException(HttpStatus.NOT_FOUND, "No records");
        return list;
    }

    public TicketBusinessUsage getTicketBusinessUsageById(Integer id){
        Optional<TicketBusinessUsage> optional = ticketbusinessusageCrud.findById(id);
        if(optional.isEmpty()) throw new BusinessException(HttpStatus.NOT_FOUND, "TicketBusinessUsage not found");
        return optional.get();
    }

    public void deleteTicketBusinessUsage(Integer id){
        TicketBusinessUsage entity = getTicketBusinessUsageById(id);
        ticketbusinessusageCrud.delete(entity);
    }

    public void createTicketBusinessUsage(NewTicketBusinessUsageDto dto){
        TicketBusinessUsage e = new TicketBusinessUsage();
        e.setTicketUsage(ticketUsageService.getTicketUsageById(dto.getId_ticket_usage()));
        e.setAffiliatedBusiness(affiliatedBusinessService.getAffiliatedBusinessById(dto.getId_affiliated_business()));
        e.setGranted_hours(dto.getGranted_hours());

        ticketbusinessusageCrud.save(e);
    }

    public void updateTicketBusinessUsage(TicketBusinessUsageDto dto){
        TicketBusinessUsage existing = getTicketBusinessUsageById(dto.getId());
        existing.setTicketUsage(ticketUsageService.getTicketUsageById(dto.getId_ticket_usage()));
        existing.setAffiliatedBusiness(affiliatedBusinessService.getAffiliatedBusinessById(dto.getId_affiliated_business()));
        existing.setGranted_hours(dto.getGranted_hours());

        ticketbusinessusageCrud.save(existing);
    }
}
