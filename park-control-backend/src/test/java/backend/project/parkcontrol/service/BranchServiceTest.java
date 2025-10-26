package backend.project.parkcontrol.service;

import backend.project.parkcontrol.dto.request.NewBranchDto;
import backend.project.parkcontrol.dto.response.BranchDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.repository.crud.BranchCrud;
import backend.project.parkcontrol.repository.entities.Branch;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.time.LocalTime;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BranchServiceTest {

    @Mock
    private BranchCrud branchCrud;

    @Mock
    private ValidationService validationService;

    @InjectMocks
    private BranchService service;

    private static final EasyRandom GENERATOR = new EasyRandom();

    @Test
    void getAllBranchList() {
        List<Branch> expected = Arrays.asList(
                GENERATOR.nextObject(Branch.class),
                GENERATOR.nextObject(Branch.class)
        );

        when(branchCrud.findAll()).thenReturn(expected);

        List<Branch> result = service.getAllBranchList();

        assertThat(result).isEqualTo(expected);
        assertThat(result.size()).isEqualTo(2);
        verify(branchCrud).findAll();
    }

    @Test
    void getBranchById() {
        Integer id = 5;
        Branch entity = GENERATOR.nextObject(Branch.class);
        entity.setId(id);

        when(branchCrud.findById(id)).thenReturn(Optional.of(entity));

        Branch result = service.getBranchById(id);

        assertThat(result).isEqualTo(entity);
        verify(branchCrud).findById(id);
    }

    @Test
    void deleteBranch() {
        Integer id = 7;
        Branch entity = GENERATOR.nextObject(Branch.class);
        entity.setId(id);

        when(branchCrud.findById(id)).thenReturn(Optional.of(entity));

        ResponseSuccessfullyDto resp = service.deleteBranch(id);

        assertThat(resp.getCode()).isEqualTo(HttpStatus.OK);
        assertThat(resp.getMessage()).isEqualTo("Registro eliminado con Éxito");
        verify(branchCrud).delete(entity);
    }

    @Test
    void createBranch_success() {
        NewBranchDto dto = new NewBranchDto();
        dto.setName("Test Branch");
        dto.setAddress("Test Address");
        dto.setOpening_time(LocalTime.now());
        dto.setClosing_time(LocalTime.now());
        dto.setCapacity_2r(50);
        dto.setCapacity_4r(30);

        Branch saved = new Branch();
        saved.setId(99);

        when(branchCrud.save(any(Branch.class))).thenReturn(saved);

        ResponseSuccessfullyDto resp = service.createBranch(dto);

        assertThat(resp.getCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(resp.getMessage()).isEqualTo("Registro creado con Éxito");
        verify(validationService).validatePositiveNumber(dto.getCapacity_2r(), "Capacidad 2r");
        verify(validationService).validatePositiveNumber(dto.getCapacity_4r(), "Capacidad 4r");
        verify(branchCrud).save(any(Branch.class));
    }

    @Test
    void updateBranch_success() {
        BranchDto dto = new BranchDto();
        dto.setId(1);
        dto.setName("Updated Branch");
        dto.setAddress("Updated Address");
        dto.setOpening_time(LocalTime.now());
        dto.setClosing_time(LocalTime.now());
        dto.setCapacity_2r(60);
        dto.setCapacity_4r(40);
        dto.setOcupation_2r(10);
        dto.setOcupation_4r(5);

        Branch existing = GENERATOR.nextObject(Branch.class);
        existing.setId(dto.getId());

        when(branchCrud.findById(dto.getId())).thenReturn(Optional.of(existing));
        when(branchCrud.save(any(Branch.class))).thenReturn(existing);

        ResponseSuccessfullyDto resp = service.updateBranch(dto);

        assertThat(resp.getCode()).isEqualTo(HttpStatus.OK);
        assertThat(resp.getMessage()).isEqualTo("Registro actualizado con Éxito");
        verify(validationService).validatePositiveNumber(dto.getCapacity_2r(), "Capacidad 2r");
        verify(validationService).validatePositiveNumber(dto.getCapacity_4r(), "Capacidad 4r");
        verify(branchCrud).save(any(Branch.class));
    }

    @Test
    void getBranch_responses() {
        Integer id = 33;
        Branch entity = GENERATOR.nextObject(Branch.class);
        entity.setId(id);

        when(branchCrud.findById(id)).thenReturn(Optional.of(entity));

        ResponseSuccessfullyDto single = service.getBranch(id);
        assertThat(single.getCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(single.getMessage()).isEqualTo("Registro encontrado con Éxito");
        assertThat(single.getBody()).isEqualTo(entity);

        List<Branch> list = Arrays.asList(entity);
        when(branchCrud.findAll()).thenReturn(list);
        ResponseSuccessfullyDto all = service.getAllBranchListResponse();
        assertThat(all.getCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(all.getMessage()).isEqualTo("Registros encontrados con Éxito");
        assertThat(all.getBody()).isEqualTo(list);
    }
}