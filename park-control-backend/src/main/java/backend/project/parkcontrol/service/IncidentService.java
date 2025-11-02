package backend.project.parkcontrol.service;

import backend.project.parkcontrol.dto.request.NewIncidentDto;
import backend.project.parkcontrol.dto.response.IncidentDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.enums.IncidentStatus;
import backend.project.parkcontrol.enums.TicketStatus;
import backend.project.parkcontrol.exception.BusinessException;
import backend.project.parkcontrol.repository.crud.IncidentCrud;
import backend.project.parkcontrol.repository.crud.TicketCrud;
import backend.project.parkcontrol.repository.crud.UserCrud;
import backend.project.parkcontrol.repository.entities.Incident;
import backend.project.parkcontrol.repository.entities.Ticket;
import backend.project.parkcontrol.repository.entities.UserEntity;
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
public class IncidentService {
    private final IncidentCrud incidentCrud;
    private final UserCrud userCrud;
    private final TicketCrud ticketCrud;
    private final TicketService ticketService;
    // ==============================
    // GETTERS
    // ==============================

    public List<Incident> getById_ticket(Integer id) {
        List<Incident> list = incidentCrud.findById_ticket(id);
        return list;
    }

    public List<Incident> getById_user_manager(Integer id) {
        List<Incident> list = incidentCrud.findById_user_manager(id);
        return list;
    }

    public List<Incident> getAllIncidentList() {
        List<Incident> list = incidentCrud.findAll();
        return list;
    }

    public Incident getIncidentById(Integer id) {
        Optional<Incident> optional = incidentCrud.findById(id);
        return optional.get();
    }

    // ==============================
    // CRUD Methods with ResponseSuccessfullyDto
    // ==============================

    public ResponseSuccessfullyDto deleteIncident(Integer id) {
        Incident entity = getIncidentById(id);
        incidentCrud.delete(entity);
        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("Registro eliminado con Éxito")
                .build();
    }

    public ResponseSuccessfullyDto createIncident(NewIncidentDto dto) {
        Incident e = new Incident();
        e.setTicket(ticketService.getTicketById(dto.getId_ticket()));
        e.setIncident_type(dto.getIncident_type());
        e.setDescription(dto.getDescription());
        e.setEvidence_url(dto.getEvidence_url());
        verifyTypeUser(dto.getId_user_manager());
        e.setUser(userCrud.findById(dto.getId_user_manager()).get());
        e.setStatus(IncidentStatus.PENDIENTE.getValue());
        e.setDate(LocalDateTime.now(ZoneId.of("America/Guatemala")));
        incidentCrud.save(e);

        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.CREATED)
                .message("Registro creado con Éxito")
                .build();
    }

    private void verifyTypeUser(Integer idUserManager) {
        UserEntity userEntity = userCrud.findById(idUserManager).get();
        if (userEntity.getRol().getId() != 2) {
            throw new BusinessException(HttpStatus.BAD_REQUEST,
                    "El usuario no corresponde a un Encargado de Sucursal");
        }
    }

    public ResponseSuccessfullyDto updateIncident(IncidentDto dto) {
        Incident existing = getIncidentById(dto.getId());
        existing.setTicket(ticketService.getTicketById(dto.getId_ticket()));
        existing.setIncident_type(dto.getIncident_type());
        existing.setDescription(dto.getDescription());
        existing.setEvidence_url(dto.getEvidence_url());
        existing.setUser(userCrud.findById(dto.getId_user_manager()).get());
        existing.setStatus(dto.getStatus());
        existing.setDate(dto.getDate());
        incidentCrud.save(existing);

        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("Registro actualizado con Éxito")
                .build();
    }

    public ResponseSuccessfullyDto solveIncident(Integer id) {
        Incident existing = getIncidentById(id);
        if(existing.getStatus()!=1){
            throw new BusinessException(HttpStatus.NOT_FOUND,"El Incidente ya ha sido resuelto.");
        }

        existing.setStatus(IncidentStatus.RESUELTO.getValue());
        incidentCrud.save(existing);
        //cambiamos el estado del ticket
        Ticket ticket = existing.getTicket();
        ticket.setStatus(TicketStatus.ENTRADA_REGISTRADA.getValue());
        ticketCrud.save(ticket);
        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("Incidente Resuelto Y Ticket Actualizado.")
                .build();
    }

    // ==============================
    // ResponseSuccessfullyDto Getters
    // ==============================

    public ResponseSuccessfullyDto getIncident(Integer id) {
        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.FOUND)
                .message("Registro encontrado con Éxito")
                .body(getIncidentById(id))
                .build();
    }

    public ResponseSuccessfullyDto getAllIncidentListResponse() {
        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.FOUND)
                .message("Registros encontrados con Éxito")
                .body(getAllIncidentList())
                .build();
    }

    public ResponseSuccessfullyDto getById_ticketResponse(Integer id) {
        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.FOUND)
                .message("Registros encontrados con Éxito")
                .body(getById_ticket(id))
                .build();
    }

    public ResponseSuccessfullyDto getById_user_managerResponse(Integer id) {
        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.FOUND)
                .message("Registros encontrados con Éxito")
                .body(getById_user_manager(id))
                .build();
    }
}
