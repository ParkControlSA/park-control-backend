package backend.project.parkcontrol.service;

import backend.project.parkcontrol.dto.request.NewRateAssignmentDto;
import backend.project.parkcontrol.dto.response.RateAssignmentDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.repository.crud.RateAssignmentCrud;
import backend.project.parkcontrol.repository.entities.Branch;
import backend.project.parkcontrol.repository.entities.RateAssignment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RateAssignmentServiceTest {

    @Mock
    private RateAssignmentCrud rateAssignmentCrud;

    @Mock
    private BranchService branchService;

    @Mock
    private ValidationService validationService;

    @InjectMocks
    private RateAssignmentService service;

    @Test
    void getById_branch() {
        Integer branchId = 1;
        List<RateAssignment> expected = Arrays.asList(new RateAssignment(), new RateAssignment());
        when(rateAssignmentCrud.findById_branch(branchId)).thenReturn(expected);

        List<RateAssignment> result = service.getById_branch(branchId);

        assertThat(result).isEqualTo(expected);
        assertThat(result.size()).isEqualTo(2);
        verify(rateAssignmentCrud).findById_branch(branchId);
    }

    @Test
    void getAllRateAssignmentList() {
        List<RateAssignment> expected = Arrays.asList(new RateAssignment(), new RateAssignment());
        when(rateAssignmentCrud.findAll()).thenReturn(expected);

        List<RateAssignment> result = service.getAllRateAssignmentList();

        assertThat(result).isEqualTo(expected);
        assertThat(result.size()).isEqualTo(2);
        verify(rateAssignmentCrud).findAll();
    }

    @Test
    void getRateAssignamentById_branchIsActive() {
        Integer branchId = 2;
        List<RateAssignment> expected = Arrays.asList(new RateAssignment());
        when(rateAssignmentCrud.findById_branchIsActive(branchId, true)).thenReturn(expected);

        List<RateAssignment> result = service.getRateAssignamentById_branchIsActive(branchId);

        assertThat(result).isEqualTo(expected);
        verify(rateAssignmentCrud).findById_branchIsActive(branchId, true);
    }

    @Test
    void getRateAssignmentById() {
        Integer id = 5;
        RateAssignment entity = new RateAssignment();
        entity.setId(id);
        when(rateAssignmentCrud.findById(id)).thenReturn(Optional.of(entity));

        RateAssignment result = service.getRateAssignmentById(id);

        assertThat(result).isEqualTo(entity);
        verify(rateAssignmentCrud).findById(id);
    }

    @Test
    void createRateAssignment_success_withoutPreviousActive() {
        NewRateAssignmentDto dto = new NewRateAssignmentDto();
        dto.setId_branch(10);
        dto.setHourly_rate(7.5);

        Branch branch = new Branch();
        branch.setId(dto.getId_branch());

        when(branchService.getBranchById(dto.getId_branch())).thenReturn(branch);
        when(rateAssignmentCrud.findById_branchIsActive(dto.getId_branch(), true)).thenReturn(Collections.emptyList());

        ArgumentCaptor<RateAssignment> captor = ArgumentCaptor.forClass(RateAssignment.class);

        ResponseSuccessfullyDto resp = service.createRateAssignment(dto);

        assertThat(resp.getCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(resp.getMessage()).isEqualTo("Registro creado con Éxito");
        verify(rateAssignmentCrud).save(captor.capture());
        RateAssignment saved = captor.getValue();
        assertThat(saved.getBranch()).isEqualTo(branch);
        assertThat(saved.getHourly_rate()).isEqualTo(7.5);
        assertThat(saved.getIs_active()).isTrue();
        assertThat(saved.getInsert_date()).isNotNull();
        assertThat(saved.getUpdate_date()).isNotNull();
        verify(rateAssignmentCrud, never()).findById_branchIsActive(dto.getId_branch(), false);
    }

    @Test
    void createRateAssignment_success_withPreviousActive_isDeactivated() {
        NewRateAssignmentDto dto = new NewRateAssignmentDto();
        dto.setId_branch(20);
        dto.setHourly_rate(12.0);

        Branch branch = new Branch();
        branch.setId(dto.getId_branch());

        RateAssignment previouslyActive = new RateAssignment();
        previouslyActive.setId(99);
        previouslyActive.setBranch(branch);
        previouslyActive.setIs_active(true);

        when(branchService.getBranchById(dto.getId_branch())).thenReturn(branch);
        when(rateAssignmentCrud.findById_branchIsActive(dto.getId_branch(), true)).thenReturn(Arrays.asList(previouslyActive));

        ResponseSuccessfullyDto resp = service.createRateAssignment(dto);

        assertThat(resp.getCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(resp.getMessage()).isEqualTo("Registro creado con Éxito");
        // Se debe desactivar el anterior y guardar
        ArgumentCaptor<RateAssignment> captor = ArgumentCaptor.forClass(RateAssignment.class);
        verify(rateAssignmentCrud, atLeastOnce()).save(captor.capture());
        List<RateAssignment> savedList = captor.getAllValues();
        // El primero guardado suele ser el desactivado, y luego el nuevo; validamos que uno haya quedado inactivo
        boolean anyInactive = savedList.stream().anyMatch(r -> Boolean.FALSE.equals(r.getIs_active()));
        assertThat(anyInactive).isTrue();
    }

    @Test
    void updateRateAssignment_success() {
        RateAssignmentDto dto = new RateAssignmentDto();
        dto.setId(30);
        dto.setId_branch(40);
        dto.setHourly_rate(15.0);
        dto.setIs_active(false);
        LocalDateTime now = LocalDateTime.now().plusMinutes(1);
        dto.setInsert_date(now);
        dto.setUpdate_date(now.plusMinutes(1));

        Branch branch = new Branch();
        branch.setId(dto.getId_branch());

        RateAssignment existing = new RateAssignment();
        existing.setId(dto.getId());

        when(rateAssignmentCrud.findById(dto.getId())).thenReturn(Optional.of(existing));
        when(branchService.getBranchById(dto.getId_branch())).thenReturn(branch);
        when(rateAssignmentCrud.save(any(RateAssignment.class))).thenReturn(existing);

        ResponseSuccessfullyDto resp = service.updateRateAssignment(dto);

        assertThat(resp.getCode()).isEqualTo(HttpStatus.OK);
        assertThat(resp.getMessage()).isEqualTo("Registro actualizado con Éxito");
        verify(validationService).validateCurrentOrFutureDateTime(dto.getInsert_date(), "Insert DateTime");
        verify(validationService).validateCurrentOrFutureDateTime(dto.getInsert_date(), "Update DateTime");
        verify(rateAssignmentCrud).save(any(RateAssignment.class));
    }

    @Test
    void deleteRateAssignment_success() {
        Integer id = 50;
        RateAssignment entity = new RateAssignment();
        entity.setId(id);
        when(rateAssignmentCrud.findById(id)).thenReturn(Optional.of(entity));

        ResponseSuccessfullyDto resp = service.deleteRateAssignment(id);

        assertThat(resp.getCode()).isEqualTo(HttpStatus.OK);
        assertThat(resp.getMessage()).isEqualTo("Registro eliminado con Éxito");
        verify(rateAssignmentCrud).delete(entity);
    }

    @Test
    void responseGetters() {
        Integer id = 60;
        RateAssignment entity = new RateAssignment();
        entity.setId(id);
        when(rateAssignmentCrud.findById(id)).thenReturn(Optional.of(entity));

        ResponseSuccessfullyDto single = service.getRateAssignment(id);
        assertThat(single.getCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(single.getMessage()).isEqualTo("Registro encontrado con Éxito");
        assertThat(single.getBody()).isEqualTo(entity);

        List<RateAssignment> list = Arrays.asList(entity);
        when(rateAssignmentCrud.findAll()).thenReturn(list);
        ResponseSuccessfullyDto all = service.getAllRateAssignmentListResponse();
        assertThat(all.getCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(all.getMessage()).isEqualTo("Registros encontrados con Éxito");
        assertThat(all.getBody()).isEqualTo(list);

        when(rateAssignmentCrud.findById_branch(id)).thenReturn(list);
        ResponseSuccessfullyDto byBranch = service.getById_branchResponse(id);
        assertThat(byBranch.getCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(byBranch.getMessage()).isEqualTo("Registros encontrados con Éxito");
        assertThat(byBranch.getBody()).isEqualTo(list);

        when(rateAssignmentCrud.findById_branchIsActive(id, true)).thenReturn(list);
        ResponseSuccessfullyDto activeByBranch = service.getRateAssignamentById_branchIsActiveResponse(id);
        assertThat(activeByBranch.getCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(activeByBranch.getMessage()).isEqualTo("Registros encontrados con Éxito");
        assertThat(activeByBranch.getBody()).isEqualTo(list);
    }
}


