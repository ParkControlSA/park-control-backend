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
import backend.project.parkcontrol.repository.entities.*;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IncidentServiceTest {

    @Mock private IncidentCrud incidentCrud;
    @Mock private UserCrud userCrud;
    @Mock private TicketCrud ticketCrud;
    @Mock private TicketService ticketService;

    @InjectMocks private IncidentService service;

    private static final EasyRandom GENERATOR = new EasyRandom();

    // ==============================
    // GETTERS
    // ==============================

    @Test
    void getById_ticket() {
        Integer id = 1;
        List<Incident> expected = Arrays.asList(GENERATOR.nextObject(Incident.class));
        when(incidentCrud.findById_ticket(id)).thenReturn(expected);

        List<Incident> result = service.getById_ticket(id);

        assertThat(result).isEqualTo(expected);
        verify(incidentCrud).findById_ticket(id);
    }

    @Test
    void getById_user_manager() {
        Integer id = 2;
        List<Incident> expected = Arrays.asList(GENERATOR.nextObject(Incident.class));
        when(incidentCrud.findById_user_manager(id)).thenReturn(expected);

        List<Incident> result = service.getById_user_manager(id);

        assertThat(result).isEqualTo(expected);
        verify(incidentCrud).findById_user_manager(id);
    }

    @Test
    void getAllIncidentList() {
        List<Incident> expected = Arrays.asList(GENERATOR.nextObject(Incident.class), GENERATOR.nextObject(Incident.class));
        when(incidentCrud.findAll()).thenReturn(expected);

        List<Incident> result = service.getAllIncidentList();

        assertThat(result).isEqualTo(expected);
        verify(incidentCrud).findAll();
    }

    @Test
    void getIncidentById() {
        Integer id = 10;
        Incident incident = GENERATOR.nextObject(Incident.class);
        incident.setId(id);
        when(incidentCrud.findById(id)).thenReturn(Optional.of(incident));

        Incident result = service.getIncidentById(id);

        assertThat(result).isEqualTo(incident);
        verify(incidentCrud).findById(id);
    }

    // ==============================
    // CRUD TESTS
    // ==============================

    @Test
    void deleteIncident_success() {
        Integer id = 1;
        Incident entity = GENERATOR.nextObject(Incident.class);
        entity.setId(id);
        when(incidentCrud.findById(id)).thenReturn(Optional.of(entity));

        ResponseSuccessfullyDto resp = service.deleteIncident(id);

        assertThat(resp.getCode()).isEqualTo(HttpStatus.OK);
        assertThat(resp.getMessage()).isEqualTo("Registro eliminado con Éxito");
        verify(incidentCrud).delete(entity);
    }

    @Test
    void createIncident_success() {
        NewIncidentDto dto = new NewIncidentDto();
        dto.setId_ticket(1);
        dto.setIncident_type(1);
        dto.setDescription("Error técnico");
        dto.setEvidence_url("http://image.com");
        dto.setId_user_manager(2);

        Ticket ticket = new Ticket();
        ticket.setId(1);
        ticket.setStatus(TicketStatus.ENTRADA_REGISTRADA.getValue());

        UserEntity user = new UserEntity();
        RoleEntity rol = new RoleEntity();
        rol.setId(2); // Encargado
        user.setRol(rol);

        when(ticketService.getTicketById(dto.getId_ticket())).thenReturn(ticket);
        when(userCrud.findById(dto.getId_user_manager())).thenReturn(Optional.of(user));
        when(incidentCrud.save(any(Incident.class))).thenReturn(GENERATOR.nextObject(Incident.class));
        when(ticketCrud.save(any(Ticket.class))).thenReturn(ticket);

        ResponseSuccessfullyDto resp = service.createIncident(dto);

        assertThat(resp.getCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(resp.getMessage()).isEqualTo("Registro creado con Éxito");
        verify(ticketCrud).save(any(Ticket.class));
        verify(incidentCrud).save(any(Incident.class));
    }

    @Test
    void createIncident_shouldThrowIfNotManager() {
        NewIncidentDto dto = new NewIncidentDto();
        dto.setId_ticket(1);
        dto.setId_user_manager(5);

        UserEntity user = new UserEntity();
        RoleEntity rol = new RoleEntity();
        rol.setId(3); // NO es encargado
        user.setRol(rol);

        when(userCrud.findById(dto.getId_user_manager())).thenReturn(Optional.of(user));
        when(ticketService.getTicketById(any())).thenReturn(new Ticket());

        BusinessException ex = assertThrows(BusinessException.class, () -> service.createIncident(dto));
        assertThat(ex.getMessage()).contains("no corresponde a un Encargado");
    }

    @Test
    void updateIncident_success() {
        IncidentDto dto = new IncidentDto();
        dto.setId(1);
        dto.setId_ticket(10);
        dto.setIncident_type(1);
        dto.setDescription("Nueva descripción");
        dto.setEvidence_url("http://evidencia.com");
        dto.setId_user_manager(3);
        dto.setStatus(IncidentStatus.PENDIENTE.getValue());
        dto.setDate(LocalDateTime.now());

        Incident existing = new Incident();
        existing.setId(dto.getId());

        Ticket ticket = new Ticket();
        UserEntity user = new UserEntity();
        when(incidentCrud.findById(dto.getId())).thenReturn(Optional.of(existing));
        when(ticketService.getTicketById(dto.getId_ticket())).thenReturn(ticket);
        when(userCrud.findById(dto.getId_user_manager())).thenReturn(Optional.of(user));
        when(incidentCrud.save(any(Incident.class))).thenReturn(existing);

        ResponseSuccessfullyDto resp = service.updateIncident(dto);

        assertThat(resp.getCode()).isEqualTo(HttpStatus.OK);
        assertThat(resp.getMessage()).isEqualTo("Registro actualizado con Éxito");
        verify(incidentCrud).save(existing);
    }

    @Test
    void solveIncident_success() {
        Integer id = 1;
        Incident incident = new Incident();
        incident.setId(id);
        incident.setStatus(IncidentStatus.PENDIENTE.getValue());

        Ticket ticket = new Ticket();
        ticket.setStatus(TicketStatus.INCIDENTE.getValue());
        incident.setTicket(ticket);

        when(incidentCrud.findById(id)).thenReturn(Optional.of(incident));

        ResponseSuccessfullyDto resp = service.solveIncident(id);

        assertThat(resp.getCode()).isEqualTo(HttpStatus.OK);
        assertThat(resp.getMessage()).isEqualTo("Incidente Resuelto Y Ticket Actualizado.");
        verify(incidentCrud).save(incident);
        verify(ticketCrud).save(ticket);
    }

    @Test
    void solveIncident_shouldThrowIfAlreadyResolved() {
        Integer id = 1;
        Incident incident = new Incident();
        incident.setId(id);
        incident.setStatus(IncidentStatus.RESUELTO.getValue());

        when(incidentCrud.findById(id)).thenReturn(Optional.of(incident));

        BusinessException ex = assertThrows(BusinessException.class, () -> service.solveIncident(id));
        assertThat(ex.getMessage()).contains("ya ha sido resuelto");
    }

    // ==============================
    // RESPONSES
    // ==============================

    @Test
    void responseMethods() {
        Integer id = 7;
        Incident entity = new Incident();
        entity.setId(id);

        when(incidentCrud.findById(id)).thenReturn(Optional.of(entity));
        when(incidentCrud.findAll()).thenReturn(List.of(entity));
        when(incidentCrud.findById_ticket(id)).thenReturn(List.of(entity));
        when(incidentCrud.findById_user_manager(id)).thenReturn(List.of(entity));

        ResponseSuccessfullyDto one = service.getIncident(id);
        assertThat(one.getCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(one.getMessage()).isEqualTo("Registro encontrado con Éxito");
        assertThat(one.getBody()).isEqualTo(entity);

        ResponseSuccessfullyDto all = service.getAllIncidentListResponse();
        assertThat(all.getCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(all.getMessage()).isEqualTo("Registros encontrados con Éxito");

        ResponseSuccessfullyDto byTicket = service.getById_ticketResponse(id);
        assertThat(byTicket.getCode()).isEqualTo(HttpStatus.FOUND);

        ResponseSuccessfullyDto byManager = service.getById_user_managerResponse(id);
        assertThat(byManager.getCode()).isEqualTo(HttpStatus.FOUND);

        verify(incidentCrud).findById(id);
        verify(incidentCrud).findAll();
        verify(incidentCrud).findById_ticket(id);
        verify(incidentCrud).findById_user_manager(id);
    }
}
