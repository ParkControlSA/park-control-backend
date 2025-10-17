package backend.project.parkcontrol.service;

import backend.project.parkcontrol.dto.request.NewTicketUsageDto;
import backend.project.parkcontrol.dto.response.TicketUsageDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.exception.BusinessException;
import backend.project.parkcontrol.repository.crud.TicketUsageCrud;
import backend.project.parkcontrol.repository.entities.TicketUsage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class TicketUsageService {
    private final TicketUsageCrud ticketusageCrud;
    private final TicketService ticketService;

    // ==============================
    // GETTERS
    // ==============================
    public List<TicketUsage> getById_ticket(Integer id){
        List<TicketUsage> list = ticketusageCrud.findById_ticket(id);
        if(list.isEmpty()) throw new BusinessException(HttpStatus.NOT_FOUND, "No se encontraron registros para el ticket");
        return list;
    }

    public ResponseSuccessfullyDto getById_ticketResponse(Integer id){
        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.FOUND)
                .message("Registros encontrados con éxito")
                .body(getById_ticket(id))
                .build();
    }

    public List<TicketUsage> getAllTicketUsageList(){
        List<TicketUsage> list = ticketusageCrud.findAll();
        if(list.isEmpty()) throw new BusinessException(HttpStatus.NOT_FOUND, "No hay registros");
        return list;
    }

    public ResponseSuccessfullyDto getAllTicketUsageListResponse(){
        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.FOUND)
                .message("Registros encontrados con éxito")
                .body(getAllTicketUsageList())
                .build();
    }

    public TicketUsage getTicketUsageById(Integer id){
        return ticketusageCrud.findById(id)
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Registro no encontrado"));
    }

    public ResponseSuccessfullyDto getTicketUsageByIdResponse(Integer id){
        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.FOUND)
                .message("Registro encontrado con éxito")
                .body(getTicketUsageById(id))
                .build();
    }

    // ==============================
    // CRUD
    // ==============================
    public ResponseSuccessfullyDto createTicketUsage(NewTicketUsageDto dto){
        TicketUsage e = new TicketUsage();
        e.setTicket(ticketService.getTicketById(dto.getId_ticket()));
        e.setGranted_hours(dto.getGranted_hours());
        e.setConsumed_plan_hours(dto.getConsumed_plan_hours());
        e.setExceeded_hours(dto.getExceeded_hours());
        e.setTotal_hours(dto.getTotal_hours());
        e.setHourly_rate(dto.getHourly_rate());
        e.setCustomer_amount(dto.getCustomer_amount());

        ticketusageCrud.save(e);

        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.CREATED)
                .message("Registro creado con éxito")
                .build();
    }

    public ResponseSuccessfullyDto updateTicketUsage(TicketUsageDto dto){
        TicketUsage existing = getTicketUsageById(dto.getId());
        existing.setTicket(ticketService.getTicketById(dto.getId_ticket()));
        existing.setGranted_hours(dto.getGranted_hours());
        existing.setConsumed_plan_hours(dto.getConsumed_plan_hours());
        existing.setExceeded_hours(dto.getExceeded_hours());
        existing.setTotal_hours(dto.getTotal_hours());
        existing.setHourly_rate(dto.getHourly_rate());
        existing.setCustomer_amount(dto.getCustomer_amount());

        ticketusageCrud.save(existing);

        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.ACCEPTED)
                .message("Registro actualizado con éxito")
                .build();
    }

    public ResponseSuccessfullyDto deleteTicketUsage(Integer id){
        TicketUsage entity = getTicketUsageById(id);
        ticketusageCrud.delete(entity);

        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.ACCEPTED)
                .message("Registro eliminado con éxito")
                .build();
    }
}
