package backend.project.parkcontrol.service;

import backend.project.parkcontrol.dto.request.NewBranchTemporaryPermitDto;
import backend.project.parkcontrol.dto.response.BranchTemporaryPermitDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.exception.BusinessException;
import backend.project.parkcontrol.repository.crud.BranchTemporaryPermitCrud;
import backend.project.parkcontrol.repository.entities.Branch;
import backend.project.parkcontrol.repository.entities.BranchTemporaryPermit;
import backend.project.parkcontrol.repository.entities.TemporaryContractPermit;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BranchTemporaryPermitServiceTest {

    @Mock
    private BranchTemporaryPermitCrud branchtemporarypermitCrud;

    @Mock
    private TemporaryContractPermitService temporaryContractPermitService;

    @Mock
    private BranchService branchService;

    @InjectMocks
    private BranchTemporaryPermitService service;

    private static final EasyRandom GENERATOR = new EasyRandom();

    @Test
    void getById_temporary_permit_success() {
        Integer permitId = 1;
        List<BranchTemporaryPermit> expected = Arrays.asList(
                GENERATOR.nextObject(BranchTemporaryPermit.class),
                GENERATOR.nextObject(BranchTemporaryPermit.class)
        );

        when(branchtemporarypermitCrud.findById_temporary_permit(permitId)).thenReturn(expected);

        List<BranchTemporaryPermit> result = service.getById_temporary_permit(permitId);

        assertThat(result).isEqualTo(expected);
        assertThat(result.size()).isEqualTo(2);
        verify(branchtemporarypermitCrud).findById_temporary_permit(permitId);
    }

    @Test
    void getById_temporary_permit_notFound() {
        Integer permitId = 999;
        when(branchtemporarypermitCrud.findById_temporary_permit(permitId))
                .thenReturn(Collections.emptyList());

        BusinessException ex = assertThrows(BusinessException.class, () ->
                service.getById_temporary_permit(permitId));

        assertThat(ex.getCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(ex.getMessage()).isEqualTo("Not found");
    }

    @Test
    void getById_branch_success() {
        Integer branchId = 1;
        List<BranchTemporaryPermit> expected = Arrays.asList(
                GENERATOR.nextObject(BranchTemporaryPermit.class)
        );

        when(branchtemporarypermitCrud.findById_branch(branchId)).thenReturn(expected);

        List<BranchTemporaryPermit> result = service.getById_branch(branchId);

        assertThat(result).isEqualTo(expected);
        verify(branchtemporarypermitCrud).findById_branch(branchId);
    }

    @Test
    void getById_branch_notFound() {
        Integer branchId = 999;
        when(branchtemporarypermitCrud.findById_branch(branchId))
                .thenReturn(Collections.emptyList());

        BusinessException ex = assertThrows(BusinessException.class, () ->
                service.getById_branch(branchId));

        assertThat(ex.getCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(ex.getMessage()).isEqualTo("Not found");
    }

    @Test
    void getAllBranchTemporaryPermitList_success() {
        List<BranchTemporaryPermit> expected = Arrays.asList(
                GENERATOR.nextObject(BranchTemporaryPermit.class),
                GENERATOR.nextObject(BranchTemporaryPermit.class)
        );

        when(branchtemporarypermitCrud.findAll()).thenReturn(expected);

        List<BranchTemporaryPermit> result = service.getAllBranchTemporaryPermitList();

        assertThat(result).isEqualTo(expected);
        assertThat(result.size()).isEqualTo(2);
        verify(branchtemporarypermitCrud).findAll();
    }

    @Test
    void getAllBranchTemporaryPermitList_noRecords() {
        when(branchtemporarypermitCrud.findAll()).thenReturn(Collections.emptyList());

        BusinessException ex = assertThrows(BusinessException.class, () ->
                service.getAllBranchTemporaryPermitList());

        assertThat(ex.getCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(ex.getMessage()).isEqualTo("No records");
    }

    @Test
    void getBranchTemporaryPermitById_success() {
        Integer id = 5;
        BranchTemporaryPermit entity = GENERATOR.nextObject(BranchTemporaryPermit.class);
        entity.setId(id);

        when(branchtemporarypermitCrud.findById(id)).thenReturn(Optional.of(entity));

        BranchTemporaryPermit result = service.getBranchTemporaryPermitById(id);

        assertThat(result).isEqualTo(entity);
        verify(branchtemporarypermitCrud).findById(id);
    }

    @Test
    void getBranchTemporaryPermitById_notFound() {
        Integer id = 999;
        when(branchtemporarypermitCrud.findById(id)).thenReturn(Optional.empty());

        BusinessException ex = assertThrows(BusinessException.class, () ->
                service.getBranchTemporaryPermitById(id));

        assertThat(ex.getCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(ex.getMessage()).isEqualTo("BranchTemporaryPermit not found");
    }

    @Test
    void deleteBranchTemporaryPermit() {
        Integer id = 7;
        BranchTemporaryPermit entity = GENERATOR.nextObject(BranchTemporaryPermit.class);
        entity.setId(id);

        when(branchtemporarypermitCrud.findById(id)).thenReturn(Optional.of(entity));

        ResponseSuccessfullyDto resp = service.deleteBranchTemporaryPermit(id);

        assertThat(resp.getCode()).isEqualTo(HttpStatus.OK);
        assertThat(resp.getMessage()).isEqualTo("Registro eliminado con Éxito");
        verify(branchtemporarypermitCrud).delete(entity);
    }

    @Test
    void createBranchTemporaryPermit_success() {
        NewBranchTemporaryPermitDto dto = new NewBranchTemporaryPermitDto();
        dto.setId_temporary_permit(10);
        dto.setId_branch(20);

        TemporaryContractPermit permit = GENERATOR.nextObject(TemporaryContractPermit.class);
        Branch branch = GENERATOR.nextObject(Branch.class);
        BranchTemporaryPermit saved = new BranchTemporaryPermit();
        saved.setId(99);

        when(temporaryContractPermitService.getTemporaryContractPermitById(dto.getId_temporary_permit()))
                .thenReturn(permit);
        when(branchService.getBranchById(dto.getId_branch())).thenReturn(branch);
        when(branchtemporarypermitCrud.save(any(BranchTemporaryPermit.class))).thenReturn(saved);

        ResponseSuccessfullyDto resp = service.createBranchTemporaryPermit(dto);

        assertThat(resp.getCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(resp.getMessage()).isEqualTo("Registro creado con Éxito");
        verify(branchtemporarypermitCrud).save(any(BranchTemporaryPermit.class));
    }

    @Test
    void updateBranchTemporaryPermit() {
        BranchTemporaryPermitDto dto = new BranchTemporaryPermitDto();
        dto.setId(55);
        dto.setId_temporary_permit(101);
        dto.setId_branch(202);

        BranchTemporaryPermit existing = GENERATOR.nextObject(BranchTemporaryPermit.class);
        existing.setId(dto.getId());
        TemporaryContractPermit permit = GENERATOR.nextObject(TemporaryContractPermit.class);
        Branch branch = GENERATOR.nextObject(Branch.class);

        when(branchtemporarypermitCrud.findById(dto.getId())).thenReturn(Optional.of(existing));
        when(temporaryContractPermitService.getTemporaryContractPermitById(dto.getId_temporary_permit()))
                .thenReturn(permit);
        when(branchService.getBranchById(dto.getId_branch())).thenReturn(branch);
        when(branchtemporarypermitCrud.save(any(BranchTemporaryPermit.class))).thenReturn(existing);

        ResponseSuccessfullyDto resp = service.updateBranchTemporaryPermit(dto);

        assertThat(resp.getCode()).isEqualTo(HttpStatus.OK);
        assertThat(resp.getMessage()).isEqualTo("Registro actualizado con Éxito");
        verify(branchtemporarypermitCrud).save(any(BranchTemporaryPermit.class));
    }

    @Test
    void getBranchTemporaryPermit_responses() {
        Integer id = 33;
        BranchTemporaryPermit entity = GENERATOR.nextObject(BranchTemporaryPermit.class);
        entity.setId(id);

        when(branchtemporarypermitCrud.findById(id)).thenReturn(Optional.of(entity));

        ResponseSuccessfullyDto single = service.getBranchTemporaryPermit(id);
        assertThat(single.getCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(single.getMessage()).isEqualTo("Registro encontrado con Éxito");
        assertThat(single.getBody()).isEqualTo(entity);

        List<BranchTemporaryPermit> list = Arrays.asList(entity);
        when(branchtemporarypermitCrud.findAll()).thenReturn(list);
        ResponseSuccessfullyDto all = service.getAllBranchTemporaryPermitListResponse();
        assertThat(all.getCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(all.getMessage()).isEqualTo("Registros encontrados con Éxito");
        assertThat(all.getBody()).isEqualTo(list);

        when(branchtemporarypermitCrud.findById_branch(id)).thenReturn(list);
        ResponseSuccessfullyDto byBranch = service.getById_branchResponse(id);
        assertThat(byBranch.getCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(byBranch.getMessage()).isEqualTo("Registros encontrados con Éxito");
        assertThat(byBranch.getBody()).isEqualTo(list);

        when(branchtemporarypermitCrud.findById_temporary_permit(id)).thenReturn(list);
        ResponseSuccessfullyDto byPermit = service.getById_temporary_permitResponse(id);
        assertThat(byPermit.getCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(byPermit.getMessage()).isEqualTo("Registros encontrados con Éxito");
        assertThat(byPermit.getBody()).isEqualTo(list);
    }
}