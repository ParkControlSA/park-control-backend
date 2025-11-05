package backend.project.parkcontrol.service;

import backend.project.parkcontrol.dto.request.NewTicketDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.dto.response.TicketDto;
import backend.project.parkcontrol.enums.TicketStatus;
import backend.project.parkcontrol.exception.BusinessException;
import backend.project.parkcontrol.repository.crud.BranchCrud;
import backend.project.parkcontrol.repository.crud.TicketCrud;
import backend.project.parkcontrol.repository.entities.Branch;
import backend.project.parkcontrol.repository.entities.Ticket;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TicketServiceTest {

    @Mock
    private TicketCrud ticketCrud;

    @Mock
    private BranchService branchService;

    @Mock
    private BranchCrud branchCrud;

    @Mock
    private TicketUsageService ticketUsageService;

    @InjectMocks
    private TicketService service;

    private static final EasyRandom GENERATOR = new EasyRandom();

    // ==============================
    // GETTERS
    // ==============================

    @Test
    void getById_branch_shouldReturnList() {
        Integer id = 1;
        List<Ticket> expected = List.of(GENERATOR.nextObject(Ticket.class));
        when(ticketCrud.findById_branch(id)).thenReturn(expected);

        List<Ticket> result = service.getById_branch(id);

        assertThat(result).isEqualTo(expected);
        verify(ticketCrud).findById_branch(id);
    }

    @Test
    void getById_branchResponse_shouldReturnSuccess() {
        Integer id = 2;
        List<Ticket> list = List.of(GENERATOR.nextObject(Ticket.class));
        when(ticketCrud.findById_branch(id)).thenReturn(list);

        ResponseSuccessfullyDto resp = service.getById_branchResponse(id);

        assertThat(resp.getCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(resp.getBody()).isEqualTo(list);
    }

    @Test
    void getByPlateResponse_shouldReturnSuccess() {
        String plate = "ABC123";
        List<Ticket> list = List.of(GENERATOR.nextObject(Ticket.class));
        when(ticketCrud.findByPlate(plate)).thenReturn(list);

        ResponseSuccessfullyDto resp = service.getByPlateResponse(plate);

        assertThat(resp.getCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(resp.getBody()).isEqualTo(list);
    }

    @Test
    void getAllTicketListResponse_shouldReturnSuccess() {
        List<Ticket> list = List.of(GENERATOR.nextObject(Ticket.class));
        when(ticketCrud.findAll(any(Sort.class))).thenReturn(list);

        ResponseSuccessfullyDto resp = service.getAllTicketListResponse();

        assertThat(resp.getCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(resp.getBody()).isEqualTo(list);
    }

    @Test
    void getTicketByIdResponse_shouldReturnSuccess() {
        Integer id = 10;
        Ticket t = GENERATOR.nextObject(Ticket.class);
        when(ticketCrud.findById(id)).thenReturn(Optional.of(t));

        ResponseSuccessfullyDto resp = service.getTicketByIdResponse(id);

        assertThat(resp.getCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(resp.getBody()).isEqualTo(t);
    }

    // ==============================
    // CREATE
    // ==============================

    @Test
    void createTicket_success_shouldSaveTicket() {
        NewTicketDto dto = new NewTicketDto();
        dto.setId_branch(1);
        dto.setPlate("XYZ999");
        dto.setIs_4r(true);

        Branch branch = new Branch();
        branch.setId(1);
        branch.setOcupation_4r(0);
        branch.setCapacity_4r(2);
        branch.setName("Sucursal Test");

        Ticket saved = new Ticket();
        saved.setId(1);
        saved.setPlate(dto.getPlate());
        saved.setBranch(branch);

        when(ticketCrud.findByPlateStatus(dto.getPlate(), 1)).thenReturn(Collections.emptyList());
        when(branchService.getBranchById(dto.getId_branch())).thenReturn(branch);
        when(ticketCrud.save(any(Ticket.class))).thenReturn(saved);

        ResponseSuccessfullyDto resp = service.createTicket(dto);

        assertThat(resp.getCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(resp.getMessage()).contains("éxito");
        verify(ticketCrud, atLeastOnce()).save(any(Ticket.class));
        verify(ticketUsageService).updateRateAssignament(any(Ticket.class));
    }

    @Test
    void createTicket_shouldThrowIfPlateExists() {
        NewTicketDto dto = new NewTicketDto();
        dto.setId_branch(1);
        dto.setPlate("DUPLICATE");
        dto.setIs_4r(false);

        when(ticketCrud.findByPlateStatus(dto.getPlate(), 1))
                .thenReturn(List.of(GENERATOR.nextObject(Ticket.class)));

        assertThrows(BusinessException.class, () -> service.createTicket(dto));
    }

    @Test
    void createTicket_shouldThrowIfNoCapacity4r() {
        NewTicketDto dto = new NewTicketDto();
        dto.setId_branch(1);
        dto.setIs_4r(true);

        Branch branch = new Branch();
        branch.setOcupation_4r(5);
        branch.setCapacity_4r(5);

        lenient().when(ticketCrud.findByPlateStatus(any(), anyInt())).thenReturn(Collections.emptyList());
        when(branchService.getBranchById(dto.getId_branch())).thenReturn(branch);

        assertThrows(BusinessException.class, () -> service.createTicket(dto));
    }

    @Test
    void createTicket_shouldThrowIfNoCapacity2r() {
        NewTicketDto dto = new NewTicketDto();
        dto.setId_branch(1);
        dto.setIs_4r(false);

        Branch branch = new Branch();
        branch.setOcupation_2r(3);
        branch.setCapacity_2r(3);

        lenient().when(ticketCrud.findByPlateStatus(any(), anyInt())).thenReturn(Collections.emptyList());
        when(branchService.getBranchById(dto.getId_branch())).thenReturn(branch);

        assertThrows(BusinessException.class, () -> service.createTicket(dto));
    }

    // ==============================
    // UPDATE Y DELETE
    // ==============================

    @Test
    void updateTicket_shouldUpdateSuccessfully() {
        TicketDto dto = GENERATOR.nextObject(TicketDto.class);
        dto.setId(1);
        Ticket existing = GENERATOR.nextObject(Ticket.class);
        existing.setId(1);

        when(ticketCrud.findById(dto.getId())).thenReturn(Optional.of(existing));
        when(branchService.getBranchById(dto.getId_branch())).thenReturn(GENERATOR.nextObject(Branch.class));
        when(ticketCrud.save(any(Ticket.class))).thenReturn(existing);

        ResponseSuccessfullyDto resp = service.updateTicket(dto);

        assertThat(resp.getCode()).isEqualTo(HttpStatus.OK);
        verify(ticketCrud).save(any(Ticket.class));
    }

    @Test
    void deleteTicket_shouldDeleteSuccessfully() {
        Integer id = 1;
        Ticket ticket = GENERATOR.nextObject(Ticket.class);
        ticket.setId(id);
        when(ticketCrud.findById(id)).thenReturn(Optional.of(ticket));

        ResponseSuccessfullyDto resp = service.deleteTicket(id);

        assertThat(resp.getCode()).isEqualTo(HttpStatus.OK);
        verify(ticketCrud).delete(ticket);
    }

    // ==============================
    // UTILS
    // ==============================

    @Test
    void generateCardValue_shouldEncodeBase64() {
        String input = "test123";
        String result = service.generateCardValue(input);
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(Base64.getUrlEncoder().withoutPadding()
                .encodeToString(input.getBytes()));
    }

    @Test
    void generateQrBase64_shouldReturnBase64() {
        String result = service.generateQrBase64("testQR");
        assertThat(result).isNotNull();
        assertThat(result.length()).isGreaterThan(50);
    }

    // ==============================
    // CLOSE TICKET (flujo simplificado)
    // ==============================

    @Test
    void closeTicket_shouldCloseByPlate() {
        Ticket t = new Ticket();
        t.setStatus(TicketStatus.ENTRADA_REGISTRADA.getValue());
        t.setBranch(GENERATOR.nextObject(Branch.class));
        t.setIs_4r(false);
        when(ticketCrud.findByPlateStatus(anyString(), anyInt())).thenReturn(List.of(t));

        ResponseSuccessfullyDto resp = service.closeTicket("ABC123", 1);

        assertThat(resp.getCode()).isEqualTo(HttpStatus.OK);
        verify(ticketCrud).save(any(Ticket.class));
        verify(ticketUsageService).calculatePayment(any(Ticket.class));
    }

    @Test
    void closeTicket_shouldThrowIfTicketAlreadyClosed() {
        Ticket t = new Ticket();
        t.setStatus(TicketStatus.SALIDA_REGISTRADA.getValue());
        when(ticketCrud.findByPlateStatus(anyString(), anyInt())).thenReturn(List.of(t));

        assertThrows(BusinessException.class, () -> service.closeTicket("XYZ999", 1));
    }
}
