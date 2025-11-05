package backend.project.parkcontrol.service;

import backend.project.parkcontrol.dto.request.NewTicketBusinessUsageDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.dto.response.TicketBusinessUsageDto;
import backend.project.parkcontrol.exception.BusinessException;
import backend.project.parkcontrol.repository.crud.TicketBusinessUsageCrud;
import backend.project.parkcontrol.repository.entities.AffiliatedBusiness;
import backend.project.parkcontrol.repository.entities.TicketBusinessUsage;
import backend.project.parkcontrol.repository.entities.TicketUsage;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TicketBusinessUsageServiceTest {

    @Mock
    private TicketBusinessUsageCrud ticketbusinessusageCrud;

    @Mock
    private AffiliatedBusinessService affiliatedBusinessService;

    @Mock
    private TicketUsageService ticketUsageService;

    @Mock
    private ValidationService validationService;

    @InjectMocks
    private TicketBusinessUsageService service;

    private static final EasyRandom GENERATOR = new EasyRandom();

    @Test
    void getById_ticket_usage() {
        Integer id = 1;
        List<TicketBusinessUsage> expected = Arrays.asList(new TicketBusinessUsage(), new TicketBusinessUsage());
        when(ticketbusinessusageCrud.findById_ticket_usage(id)).thenReturn(expected);

        List<TicketBusinessUsage> result = service.getById_ticket_usage(id);

        assertThat(result).isEqualTo(expected);
        assertThat(result.size()).isEqualTo(2);
        verify(ticketbusinessusageCrud).findById_ticket_usage(id);
    }

    @Test
    void getById_ticket_usageResponse() {
        Integer id = 2;
        List<TicketBusinessUsage> expected = Arrays.asList(new TicketBusinessUsage());
        when(ticketbusinessusageCrud.findById_ticket_usage(id)).thenReturn(expected);

        ResponseSuccessfullyDto resp = service.getById_ticket_usageResponse(id);

        assertThat(resp.getCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(resp.getMessage()).isEqualTo("Registros encontrados con Éxito");
        assertThat(resp.getBody()).isEqualTo(expected);
    }

    @Test
    void getById_affiliated_business() {
        Integer id = 3;
        List<TicketBusinessUsage> expected = Arrays.asList(new TicketBusinessUsage(), new TicketBusinessUsage());
        when(ticketbusinessusageCrud.findById_affiliated_business(id)).thenReturn(expected);

        List<TicketBusinessUsage> result = service.getById_affiliated_business(id);

        assertThat(result).isEqualTo(expected);
        assertThat(result.size()).isEqualTo(2);
        verify(ticketbusinessusageCrud).findById_affiliated_business(id);
    }

    @Test
    void getById_affiliated_businessResponse() {
        Integer id = 4;
        List<TicketBusinessUsage> expected = Arrays.asList(new TicketBusinessUsage());
        when(ticketbusinessusageCrud.findById_affiliated_business(id)).thenReturn(expected);

        ResponseSuccessfullyDto resp = service.getById_affiliated_businessResponse(id);

        assertThat(resp.getCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(resp.getMessage()).isEqualTo("Registros encontrados con Éxito");
        assertThat(resp.getBody()).isEqualTo(expected);
    }

    @Test
    void getAllTicketBusinessUsageList_andResponse() {
        List<TicketBusinessUsage> expected = Arrays.asList(new TicketBusinessUsage(), new TicketBusinessUsage());
        when(ticketbusinessusageCrud.findAll()).thenReturn(expected);

        List<TicketBusinessUsage> list = service.getAllTicketBusinessUsageList();
        assertThat(list).isEqualTo(expected);
        assertThat(list.size()).isEqualTo(2);
        verify(ticketbusinessusageCrud).findAll();

        ResponseSuccessfullyDto resp = service.getAllTicketBusinessUsageListResponse();
        assertThat(resp.getCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(resp.getMessage()).isEqualTo("Registros encontrados con Éxito");
        assertThat(resp.getBody()).isEqualTo(expected);
    }

    @Test
    void getTicketBusinessUsageById_success_andResponse() {
        Integer id = 5;
        TicketBusinessUsage entity = new TicketBusinessUsage();
        entity.setId(id);
        when(ticketbusinessusageCrud.findById(id)).thenReturn(Optional.of(entity));

        TicketBusinessUsage result = service.getTicketBusinessUsageById(id);
        assertThat(result).isEqualTo(entity);
        verify(ticketbusinessusageCrud).findById(id);

        ResponseSuccessfullyDto resp = service.getTicketBusinessUsageByIdResponse(id);
        assertThat(resp.getCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(resp.getMessage()).isEqualTo("Registro encontrado con Éxito");
        assertThat(resp.getBody()).isEqualTo(entity);
    }

    @Test
    void getTicketBusinessUsageById_notFound_throws() {
        Integer id = 6;
        when(ticketbusinessusageCrud.findById(id)).thenReturn(Optional.empty());

        BusinessException ex = assertThrows(BusinessException.class, () -> service.getTicketBusinessUsageById(id));
        assertThat(ex.getCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(ex.getMessage()).isEqualTo("Registro no encontrado");
    }

    @Test
    void createTicketBusinessUsage_success() {
        NewTicketBusinessUsageDto dto = new NewTicketBusinessUsageDto();
        dto.setId_ticket_usage(10);
        dto.setId_affiliated_business(20);
        dto.setGranted_hours(3);

        TicketUsage ticketUsage = new TicketUsage();
        ticketUsage.setId(dto.getId_ticket_usage());
        AffiliatedBusiness affiliatedBusiness = new AffiliatedBusiness();
        affiliatedBusiness.setId(dto.getId_affiliated_business());

        when(ticketbusinessusageCrud.findById_affiliated_businessId_ticket_usage(dto.getId_affiliated_business(), dto.getId_ticket_usage()))
                .thenReturn(Collections.emptyList());
        when(ticketUsageService.getTicketUsageById(dto.getId_ticket_usage())).thenReturn(ticketUsage);
        when(affiliatedBusinessService.getAffiliatedBusinessById(dto.getId_affiliated_business())).thenReturn(affiliatedBusiness);

        ArgumentCaptor<TicketBusinessUsage> captor = ArgumentCaptor.forClass(TicketBusinessUsage.class);

        ResponseSuccessfullyDto resp = service.createTicketBusinessUsage(dto);

        assertThat(resp.getCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(resp.getMessage()).isEqualTo("Registro creado con Éxito");
        verify(validationService).validatePositiveNumber(dto.getGranted_hours(), "Horas Otorgadas del Comercio");
        verify(ticketbusinessusageCrud).save(captor.capture());
        TicketBusinessUsage saved = captor.getValue();
        assertThat(saved.getTicketUsage()).isEqualTo(ticketUsage);
        assertThat(saved.getAffiliatedBusiness()).isEqualTo(affiliatedBusiness);
        assertThat(saved.getGranted_hours()).isEqualTo(3);
    }

    @Test
    void createTicketBusinessUsage_duplicate_throwsBadRequest() {
        NewTicketBusinessUsageDto dto = new NewTicketBusinessUsageDto();
        dto.setId_ticket_usage(11);
        dto.setId_affiliated_business(22);
        dto.setGranted_hours(2);

        when(ticketbusinessusageCrud.findById_affiliated_businessId_ticket_usage(dto.getId_affiliated_business(), dto.getId_ticket_usage()))
                .thenReturn(Arrays.asList(new TicketBusinessUsage()));

        BusinessException ex = assertThrows(BusinessException.class, () -> service.createTicketBusinessUsage(dto));
        assertThat(ex.getCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(ex.getMessage()).isEqualTo("Ya se han asignado horas gratuitas a este ticket.");
        verify(validationService).validatePositiveNumber(dto.getGranted_hours(), "Horas Otorgadas del Comercio");
        verify(ticketbusinessusageCrud, never()).save(any(TicketBusinessUsage.class));
    }

    @Test
    void updateTicketBusinessUsage_success() {
        TicketBusinessUsageDto dto = new TicketBusinessUsageDto();
        dto.setId(30);
        dto.setId_ticket_usage(40);
        dto.setId_affiliated_business(50);
        dto.setGranted_hours(5);

        TicketBusinessUsage existing = new TicketBusinessUsage();
        existing.setId(dto.getId());
        TicketUsage ticketUsage = new TicketUsage();
        ticketUsage.setId(dto.getId_ticket_usage());
        AffiliatedBusiness affiliatedBusiness = new AffiliatedBusiness();
        affiliatedBusiness.setId(dto.getId_affiliated_business());

        when(ticketbusinessusageCrud.findById(dto.getId())).thenReturn(Optional.of(existing));
        when(ticketUsageService.getTicketUsageById(dto.getId_ticket_usage())).thenReturn(ticketUsage);
        when(affiliatedBusinessService.getAffiliatedBusinessById(dto.getId_affiliated_business())).thenReturn(affiliatedBusiness);
        when(ticketbusinessusageCrud.save(any(TicketBusinessUsage.class))).thenReturn(existing);

        ResponseSuccessfullyDto resp = service.updateTicketBusinessUsage(dto);

        assertThat(resp.getCode()).isEqualTo(HttpStatus.OK);
        assertThat(resp.getMessage()).isEqualTo("Registro actualizado con Éxito");
        verify(ticketbusinessusageCrud).save(any(TicketBusinessUsage.class));
    }

    @Test
    void deleteTicketBusinessUsage_success() {
        Integer id = 70;
        TicketBusinessUsage entity = new TicketBusinessUsage();
        entity.setId(id);
        when(ticketbusinessusageCrud.findById(id)).thenReturn(Optional.of(entity));

        ResponseSuccessfullyDto resp = service.deleteTicketBusinessUsage(id);

        assertThat(resp.getCode()).isEqualTo(HttpStatus.OK);
        assertThat(resp.getMessage()).isEqualTo("Registro eliminado con Éxito");
        verify(ticketbusinessusageCrud).delete(entity);
    }
}


