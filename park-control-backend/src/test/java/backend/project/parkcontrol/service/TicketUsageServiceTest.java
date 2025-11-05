package backend.project.parkcontrol.service;

import backend.project.parkcontrol.dto.request.NewTicketUsageDto;
import backend.project.parkcontrol.dto.response.TicketUsageDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.enums.TicketStatus;
import backend.project.parkcontrol.repository.crud.*;
import backend.project.parkcontrol.repository.entities.*;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TicketUsageServiceTest {

    @Mock private TicketCrud ticketCrud;
    @Mock private TicketUsageCrud ticketusageCrud;
    @Mock private RateAssignmentService rateAssignmentService;
    @Mock private ContractService contractService;
    @Mock private ContractHistoryService contractHistoryService;
    @Mock private TicketPaymentCrud ticketpaymentCrud;

    @InjectMocks private TicketUsageService service;

    private static final EasyRandom GENERATOR = new EasyRandom();

    // ========================================
    // GETTERS
    // ========================================
    @Test
    void getById_ticket_returnsList() {
        Integer id = 1;
        List<TicketUsage> expected = List.of(GENERATOR.nextObject(TicketUsage.class));
        when(ticketusageCrud.findById_ticket(id)).thenReturn(expected);

        List<TicketUsage> result = service.getById_ticket(id);

        assertThat(result).isEqualTo(expected);
        verify(ticketusageCrud).findById_ticket(id);
    }

    @Test
    void getById_ticketResponse_returnsResponseSuccessfullyDto() {
        Integer id = 2;
        List<TicketUsage> list = List.of(GENERATOR.nextObject(TicketUsage.class));
        when(ticketusageCrud.findById_ticket(id)).thenReturn(list);

        ResponseSuccessfullyDto resp = service.getById_ticketResponse(id);

        assertThat(resp.getCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(resp.getMessage()).isEqualTo("Registros encontrados con éxito");
        assertThat(resp.getBody()).isEqualTo(list);
    }

    @Test
    void getAllTicketUsageList_returnsAll() {
        List<TicketUsage> list = Arrays.asList(GENERATOR.nextObject(TicketUsage.class), GENERATOR.nextObject(TicketUsage.class));
        when(ticketusageCrud.findAll()).thenReturn(list);

        List<TicketUsage> result = service.getAllTicketUsageList();

        assertThat(result).isEqualTo(list);
        assertThat(result.size()).isEqualTo(2);
        verify(ticketusageCrud).findAll();
    }

    @Test
    void getAllTicketUsageListResponse_returnsResponse() {
        List<TicketUsage> list = Arrays.asList(GENERATOR.nextObject(TicketUsage.class));
        when(ticketusageCrud.findAll()).thenReturn(list);

        ResponseSuccessfullyDto resp = service.getAllTicketUsageListResponse();

        assertThat(resp.getCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(resp.getBody()).isEqualTo(list);
    }

    @Test
    void getTicketUsageById_found() {
        Integer id = 5;
        TicketUsage entity = GENERATOR.nextObject(TicketUsage.class);
        when(ticketusageCrud.findById(id)).thenReturn(Optional.of(entity));

        TicketUsage result = service.getTicketUsageById(id);

        assertThat(result).isEqualTo(entity);
        verify(ticketusageCrud).findById(id);
    }

    @Test
    void getTicketUsageById_notFound_returnsEmpty() {
        Integer id = 10;
        when(ticketusageCrud.findById(id)).thenReturn(Optional.empty());

        TicketUsage result = service.getTicketUsageById(id);

        assertNotNull(result);
    }

    @Test
    void getTicketUsageByIdResponse_returnsResponse() {
        Integer id = 6;
        TicketUsage entity = GENERATOR.nextObject(TicketUsage.class);
        when(ticketusageCrud.findById(id)).thenReturn(Optional.of(entity));

        ResponseSuccessfullyDto resp = service.getTicketUsageByIdResponse(id);

        assertThat(resp.getCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(resp.getMessage()).isEqualTo("Registro encontrado con éxito");
        assertThat(resp.getBody()).isEqualTo(entity);
    }

    // ========================================
    // CRUD
    // ========================================
    @Test
    void createTicketUsage_success() {
        NewTicketUsageDto dto = new NewTicketUsageDto();
        dto.setGranted_hours(2);
        dto.setConsumed_plan_hours(1);
        dto.setExceeded_hours(0);
        dto.setTotal_hours(2);
        dto.setHourly_rate(10.0);
        dto.setCustomer_amount(20.0);

        when(ticketusageCrud.save(any(TicketUsage.class))).thenReturn(GENERATOR.nextObject(TicketUsage.class));

        ResponseSuccessfullyDto resp = service.createTicketUsage(dto);

        assertThat(resp.getCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(resp.getMessage()).isEqualTo("Registro creado con éxito");
        verify(ticketusageCrud).save(any(TicketUsage.class));
    }

    @Test
    void updateTicketUsage_success() {
        TicketUsageDto dto = new TicketUsageDto();
        dto.setId(1);
        dto.setGranted_hours(3);
        dto.setConsumed_plan_hours(2);
        dto.setExceeded_hours(1);
        dto.setTotal_hours(6);
        dto.setHourly_rate(5.0);
        dto.setCustomer_amount(30.0);

        TicketUsage existing = GENERATOR.nextObject(TicketUsage.class);
        existing.setId(dto.getId());

        when(ticketusageCrud.findById(dto.getId())).thenReturn(Optional.of(existing));
        when(ticketusageCrud.save(any(TicketUsage.class))).thenReturn(existing);

        ResponseSuccessfullyDto resp = service.updateTicketUsage(dto);

        assertThat(resp.getCode()).isEqualTo(HttpStatus.OK);
        assertThat(resp.getMessage()).isEqualTo("Registro actualizado con éxito");
        verify(ticketusageCrud).save(any(TicketUsage.class));
    }

    @Test
    void deleteTicketUsage_success() {
        Integer id = 9;
        TicketUsage entity = GENERATOR.nextObject(TicketUsage.class);
        when(ticketusageCrud.findById(id)).thenReturn(Optional.of(entity));

        ResponseSuccessfullyDto resp = service.deleteTicketUsage(id);

        assertThat(resp.getCode()).isEqualTo(HttpStatus.OK);
        assertThat(resp.getMessage()).isEqualTo("Registro eliminado con éxito");
        verify(ticketusageCrud).delete(entity);
    }

    // ========================================
    // LOGIC METHODS
    // ========================================
    @Test
    void updateRateAssignament_shouldApplyRateFromBranch() {
        Ticket ticket = new Ticket();
        ticket.setId(1);
        Branch branch = new Branch();
        branch.setId(5);
        ticket.setBranch(branch);

        TicketUsage usage = new TicketUsage();
        usage.setHourly_rate(0.0);
        when(ticketusageCrud.findById_ticket(ticket.getId())).thenReturn(List.of(usage));

        RateAssignment ra = new RateAssignment();
        ra.setHourly_rate(15.0);
        when(rateAssignmentService.getRateAssignamentById_branchIsActive(branch.getId())).thenReturn(List.of(ra));

        service.updateRateAssignament(ticket);

        assertThat(usage.getHourly_rate()).isEqualTo(15.0);
        verify(ticketusageCrud).save(usage);
    }

    @Test
    void calculateHours_validDates() {
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plusMinutes(130);

        int result = service.calculateHours(start, end);

        assertThat(result).isEqualTo(3);
    }

    @Test
    void calculateHours_invalidDates_throwsException() {
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.minusHours(1);

        assertThrows(IllegalArgumentException.class, () -> service.calculateHours(start, end));
    }

    @Test
    void createTicketPayment_success() {
        Ticket ticket = GENERATOR.nextObject(Ticket.class);

        service.createTicketPayment(ticket, 50.0, 1);

        verify(ticketpaymentCrud).save(any(TicketPayment.class));
    }

    @Test
    void calculatePayment_withContractAndGrantedHours() {
        Ticket ticket = new Ticket();
        ticket.setId(1);
        ticket.setPlate("ABC123");
        ticket.setEntry_date(LocalDateTime.now().minusHours(2));
        ticket.setExit_date(LocalDateTime.now());
        ticket.setStatus(TicketStatus.ENTRADA_REGISTRADA.getValue());

        TicketUsage usage = new TicketUsage();
        usage.setGranted_hours(1);
        usage.setHourly_rate(10.0);

        Contract contract = new Contract();
        contract.setId(5);

        ContractHistory ch = new ContractHistory();
        ch.setIncluded_hours(5);
        ch.setConsumed_hours(1);

        when(ticketusageCrud.findById_ticket(ticket.getId())).thenReturn(List.of(usage));
        when(contractService.getByLicense_plateIsActive(ticket.getPlate())).thenReturn(List.of(contract));
        when(contractHistoryService.findByContractAndDate(anyInt(), any())).thenReturn(List.of(ch));

        service.calculatePayment(ticket);

        verify(ticketCrud).save(ticket);
        verify(ticketusageCrud).save(usage);
    }
}
