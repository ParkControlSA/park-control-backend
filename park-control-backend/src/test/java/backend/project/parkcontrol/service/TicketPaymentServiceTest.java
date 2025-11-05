package backend.project.parkcontrol.service;

import backend.project.parkcontrol.dto.request.NewTicketPaymentDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.dto.response.TicketPaymentDto;
import backend.project.parkcontrol.enums.TicketStatus;
import backend.project.parkcontrol.repository.crud.TicketCrud;
import backend.project.parkcontrol.repository.crud.TicketPaymentCrud;
import backend.project.parkcontrol.repository.entities.Ticket;
import backend.project.parkcontrol.repository.entities.TicketPayment;
import backend.project.parkcontrol.repository.entities.TicketUsage;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TicketPaymentServiceTest {

    @Mock
    private TicketPaymentCrud ticketpaymentCrud;

    @Mock
    private TicketService ticketService;

    @Mock
    private TicketCrud ticketCrud;

    @Mock
    private TicketUsageService ticketUsageService;

    @InjectMocks
    private TicketPaymentService service;

    private static final EasyRandom GENERATOR = new EasyRandom();

    @Test
    void getById_ticket() {
        Integer ticketId = 1;
        List<TicketPayment> expected = Arrays.asList(
                GENERATOR.nextObject(TicketPayment.class),
                GENERATOR.nextObject(TicketPayment.class)
        );

        when(ticketpaymentCrud.findById_ticket(ticketId)).thenReturn(expected);

        List<TicketPayment> result = service.getById_ticket(ticketId);

        assertThat(result).isEqualTo(expected);
        assertThat(result.size()).isEqualTo(2);
        verify(ticketpaymentCrud).findById_ticket(ticketId);
    }

    @Test
    void getById_ticketResponse() {
        Integer ticketId = 2;
        List<TicketPayment> expected = Arrays.asList(GENERATOR.nextObject(TicketPayment.class));
        when(ticketpaymentCrud.findById_ticket(ticketId)).thenReturn(expected);

        ResponseSuccessfullyDto resp = service.getById_ticketResponse(ticketId);

        assertThat(resp.getCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(resp.getMessage()).isEqualTo("Registros encontrados con Éxito");
        assertThat(resp.getBody()).isEqualTo(expected);
    }

    @Test
    void getAllTicketPaymentList() {
        List<TicketPayment> expected = Arrays.asList(
                GENERATOR.nextObject(TicketPayment.class),
                GENERATOR.nextObject(TicketPayment.class)
        );

        when(ticketpaymentCrud.findAll()).thenReturn(expected);

        List<TicketPayment> result = service.getAllTicketPaymentList();

        assertThat(result).isEqualTo(expected);
        assertThat(result.size()).isEqualTo(2);
        verify(ticketpaymentCrud).findAll();
    }

    @Test
    void getAllTicketPaymentListResponse() {
        List<TicketPayment> expected = Arrays.asList(GENERATOR.nextObject(TicketPayment.class));
        when(ticketpaymentCrud.findAll()).thenReturn(expected);

        ResponseSuccessfullyDto resp = service.getAllTicketPaymentListResponse();

        assertThat(resp.getCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(resp.getMessage()).isEqualTo("Registros encontrados con Éxito");
        assertThat(resp.getBody()).isEqualTo(expected);
    }

    @Test
    void getTicketPaymentById() {
        Integer id = 7;
        TicketPayment entity = GENERATOR.nextObject(TicketPayment.class);
        entity.setId(id);

        when(ticketpaymentCrud.findById(id)).thenReturn(Optional.of(entity));

        TicketPayment result = service.getTicketPaymentById(id);

        assertThat(result).isEqualTo(entity);
        verify(ticketpaymentCrud).findById(id);
    }

    @Test
    void getTicketPaymentByIdResponse() {
        Integer id = 8;
        TicketPayment entity = GENERATOR.nextObject(TicketPayment.class);
        entity.setId(id);
        when(ticketpaymentCrud.findById(id)).thenReturn(Optional.of(entity));

        ResponseSuccessfullyDto resp = service.getTicketPaymentByIdResponse(id);

        assertThat(resp.getCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(resp.getMessage()).isEqualTo("Registro encontrado con Éxito");
        assertThat(resp.getBody()).isEqualTo(entity);
    }

    @Test
    void createTicketPayment_success() {
        NewTicketPaymentDto dto = new NewTicketPaymentDto();
        dto.setId_ticket(10);
        dto.setPayment_method(2);

        TicketUsage usage = new TicketUsage();
        usage.setCustomer_amount(150.0);
        List<TicketUsage> usageList = Arrays.asList(usage);

        Ticket ticket = new Ticket();
        ticket.setId(dto.getId_ticket());
        ticket.setStatus(1);

        when(ticketUsageService.getById_ticket(dto.getId_ticket())).thenReturn(usageList);
        when(ticketService.getTicketById(dto.getId_ticket())).thenReturn(ticket);
        when(ticketpaymentCrud.save(any(TicketPayment.class))).thenAnswer(inv -> inv.getArgument(0));

        ResponseSuccessfullyDto resp = service.createTicketPayment(dto);

        assertThat(resp.getCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(resp.getMessage()).isEqualTo("Registro creado con Éxito");
        verify(ticketpaymentCrud).save(any(TicketPayment.class));
        verify(ticketCrud).save(ticket);
        assertThat(ticket.getStatus()).isEqualTo(TicketStatus.TICKET_PAGADO.getValue());
    }

    @Test
    void updateTicketPayment_success() {
        TicketPaymentDto dto = new TicketPaymentDto();
        dto.setId(20);
        dto.setId_ticket(30);
        dto.setTotal_amount(200.5);
        dto.setDate(LocalDateTime.now());
        dto.setPayment_method(1);

        TicketPayment existing = new TicketPayment();
        existing.setId(dto.getId());

        Ticket ticket = new Ticket();
        ticket.setId(dto.getId_ticket());

        when(ticketpaymentCrud.findById(dto.getId())).thenReturn(Optional.of(existing));
        when(ticketService.getTicketById(dto.getId_ticket())).thenReturn(ticket);
        when(ticketpaymentCrud.save(any(TicketPayment.class))).thenReturn(existing);

        ResponseSuccessfullyDto resp = service.updateTicketPayment(dto);

        assertThat(resp.getCode()).isEqualTo(HttpStatus.OK);
        assertThat(resp.getMessage()).isEqualTo("Registro actualizado con Éxito");
        verify(ticketpaymentCrud).save(any(TicketPayment.class));
    }

    @Test
    void deleteTicketPayment_success() {
        Integer id = 40;
        TicketPayment entity = GENERATOR.nextObject(TicketPayment.class);
        entity.setId(id);

        when(ticketpaymentCrud.findById(id)).thenReturn(Optional.of(entity));

        ResponseSuccessfullyDto resp = service.deleteTicketPayment(id);

        assertThat(resp.getCode()).isEqualTo(HttpStatus.OK);
        assertThat(resp.getMessage()).isEqualTo("Registro eliminado con Éxito");
        verify(ticketpaymentCrud).delete(entity);
    }
}


