package backend.project.parkcontrol.service;

import backend.project.parkcontrol.dto.request.NewTemporaryContractPermitDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.dto.response.TemporaryContractPermitDto;
import backend.project.parkcontrol.exception.BusinessException;
import backend.project.parkcontrol.repository.crud.TemporaryContractPermitCrud;
import backend.project.parkcontrol.repository.crud.UserCrud;
import backend.project.parkcontrol.repository.entities.Contract;
import backend.project.parkcontrol.repository.entities.TemporaryContractPermit;
import backend.project.parkcontrol.repository.entities.UserEntity;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TemporaryContractPermitServiceTest {

    @Mock
    private TemporaryContractPermitCrud temporarycontractpermitCrud;

    @Mock
    private ContractService contractService;

    @Mock
    private UserCrud userCrud;

    @InjectMocks
    private TemporaryContractPermitService service;

    private static final EasyRandom GENERATOR = new EasyRandom();

    // ==============================
    // GETTERS
    // ==============================

    @Test
    void getById_contract_success() {
        Integer id = 1;
        List<TemporaryContractPermit> list = List.of(GENERATOR.nextObject(TemporaryContractPermit.class));
        when(temporarycontractpermitCrud.findById_contract(id)).thenReturn(list);

        List<TemporaryContractPermit> result = service.getById_contract(id);

        assertThat(result).isEqualTo(list);
        verify(temporarycontractpermitCrud).findById_contract(id);
    }

    @Test
    void getById_contract_notFound() {
        when(temporarycontractpermitCrud.findById_contract(99)).thenReturn(Collections.emptyList());

        BusinessException ex = assertThrows(BusinessException.class, () -> service.getById_contract(99));
        assertThat(ex.getCode()).isEqualTo(HttpStatus.NOT_FOUND);
        verify(temporarycontractpermitCrud).findById_contract(99);
    }

    @Test
    void getById_contractResponse_success() {
        Integer id = 1;
        List<TemporaryContractPermit> list = List.of(GENERATOR.nextObject(TemporaryContractPermit.class));
        when(temporarycontractpermitCrud.findById_contract(id)).thenReturn(list);

        ResponseSuccessfullyDto resp = service.getById_contractResponse(id);

        assertThat(resp.getCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(resp.getMessage()).isEqualTo("Registros encontrados con Éxito");
        verify(temporarycontractpermitCrud).findById_contract(id);
    }

    @Test
    void getById_assigned_success() {
        Integer id = 5;
        List<TemporaryContractPermit> list = List.of(GENERATOR.nextObject(TemporaryContractPermit.class));
        when(temporarycontractpermitCrud.findById_assigned(id)).thenReturn(list);

        List<TemporaryContractPermit> result = service.getById_assigned(id);

        assertThat(result).isEqualTo(list);
        verify(temporarycontractpermitCrud).findById_assigned(id);
    }

    @Test
    void getById_assigned_notFound() {
        when(temporarycontractpermitCrud.findById_assigned(77)).thenReturn(Collections.emptyList());

        BusinessException ex = assertThrows(BusinessException.class, () -> service.getById_assigned(77));
        assertThat(ex.getCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void getById_assignedResponse_success() {
        Integer id = 2;
        List<TemporaryContractPermit> list = List.of(GENERATOR.nextObject(TemporaryContractPermit.class));
        when(temporarycontractpermitCrud.findById_assigned(id)).thenReturn(list);

        ResponseSuccessfullyDto resp = service.getById_assignedResponse(id);

        assertThat(resp.getCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(resp.getBody()).isEqualTo(list);
        verify(temporarycontractpermitCrud).findById_assigned(id);
    }

    @Test
    void getAllTemporaryContractPermitList_success() {
        List<TemporaryContractPermit> list = List.of(GENERATOR.nextObject(TemporaryContractPermit.class));
        when(temporarycontractpermitCrud.findAll()).thenReturn(list);

        List<TemporaryContractPermit> result = service.getAllTemporaryContractPermitList();

        assertThat(result).isEqualTo(list);
        verify(temporarycontractpermitCrud).findAll();
    }

    @Test
    void getAllTemporaryContractPermitList_notFound() {
        when(temporarycontractpermitCrud.findAll()).thenReturn(Collections.emptyList());

        assertThrows(BusinessException.class, () -> service.getAllTemporaryContractPermitList());
    }

    @Test
    void getAllTemporaryContractPermitListResponse_success() {
        List<TemporaryContractPermit> list = List.of(GENERATOR.nextObject(TemporaryContractPermit.class));
        when(temporarycontractpermitCrud.findAll()).thenReturn(list);

        ResponseSuccessfullyDto resp = service.getAllTemporaryContractPermitListResponse();

        assertThat(resp.getCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(resp.getBody()).isEqualTo(list);
    }

    @Test
    void getTemporaryContractPermitById_success() {
        Integer id = 9;
        TemporaryContractPermit entity = GENERATOR.nextObject(TemporaryContractPermit.class);
        when(temporarycontractpermitCrud.findById(id)).thenReturn(Optional.of(entity));

        TemporaryContractPermit result = service.getTemporaryContractPermitById(id);

        assertThat(result).isEqualTo(entity);
        verify(temporarycontractpermitCrud).findById(id);
    }

    @Test
    void getTemporaryContractPermitById_notFound() {
        when(temporarycontractpermitCrud.findById(77)).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> service.getTemporaryContractPermitById(77));
    }

    @Test
    void getTemporaryContractPermitByIdResponse_success() {
        Integer id = 3;
        TemporaryContractPermit entity = GENERATOR.nextObject(TemporaryContractPermit.class);
        when(temporarycontractpermitCrud.findById(id)).thenReturn(Optional.of(entity));

        ResponseSuccessfullyDto resp = service.getTemporaryContractPermitByIdResponse(id);

        assertThat(resp.getCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(resp.getBody()).isEqualTo(entity);
        verify(temporarycontractpermitCrud).findById(id);
    }

    // ==============================
    // CRUD
    // ==============================

    @Test
    void createTemporaryContractPermit_success() {
        NewTemporaryContractPermitDto dto = new NewTemporaryContractPermitDto();
        dto.setId_contract(10);
        dto.setId_assigned(20);
        dto.setTemporary_plate("TEMP123");
        dto.setStart_date(LocalDateTime.now());
        dto.setEnd_date(LocalDateTime.now().plusDays(10));
        dto.setMax_uses(5);
        dto.setUsed_count(0);
        dto.setRemaining_count(5);
        dto.setIs_4r(false);
        dto.setStatus(1);
        dto.setObservations("Test obs");

        Contract contract = GENERATOR.nextObject(Contract.class);
        UserEntity user = GENERATOR.nextObject(UserEntity.class);

        when(contractService.getContractById(10)).thenReturn(contract);
        when(userCrud.findById(20)).thenReturn(Optional.of(user));
        when(temporarycontractpermitCrud.save(any())).thenReturn(new TemporaryContractPermit());

        ResponseSuccessfullyDto resp = service.createTemporaryContractPermit(dto);

        assertThat(resp.getCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(resp.getMessage()).isEqualTo("Registro creado con Éxito");
        verify(temporarycontractpermitCrud).save(any(TemporaryContractPermit.class));
    }

    @Test
    void createTemporaryContractPermit_userNotFound() {
        NewTemporaryContractPermitDto dto = new NewTemporaryContractPermitDto();
        dto.setId_contract(10);
        dto.setId_assigned(99);
        Contract contract = GENERATOR.nextObject(Contract.class);

        when(contractService.getContractById(10)).thenReturn(contract);
        when(userCrud.findById(99)).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> service.createTemporaryContractPermit(dto));
    }

    @Test
    void updateTemporaryContractPermit_success() {
        TemporaryContractPermitDto dto = new TemporaryContractPermitDto();
        dto.setId(1);
        dto.setId_contract(10);
        dto.setId_assigned(20);
        dto.setTemporary_plate("TMP-1");
        dto.setStart_date(LocalDateTime.now());
        dto.setEnd_date(LocalDateTime.now().plusDays(2));
        dto.setMax_uses(3);
        dto.setUsed_count(1);
        dto.setRemaining_count(2);
        dto.setIs_4r(true);
        dto.setStatus(1);
        dto.setObservations("Updated");

        TemporaryContractPermit existing = GENERATOR.nextObject(TemporaryContractPermit.class);
        Contract contract = GENERATOR.nextObject(Contract.class);
        UserEntity user = GENERATOR.nextObject(UserEntity.class);

        when(temporarycontractpermitCrud.findById(1)).thenReturn(Optional.of(existing));
        when(contractService.getContractById(10)).thenReturn(contract);
        when(userCrud.findById(20)).thenReturn(Optional.of(user));
        when(temporarycontractpermitCrud.save(any())).thenReturn(existing);

        ResponseSuccessfullyDto resp = service.updateTemporaryContractPermit(dto);

        assertThat(resp.getCode()).isEqualTo(HttpStatus.OK);
        assertThat(resp.getMessage()).isEqualTo("Registro actualizado con Éxito");
        verify(temporarycontractpermitCrud).save(any(TemporaryContractPermit.class));
    }

    @Test
    void updateTemporaryContractPermit_userNotFound() {
        TemporaryContractPermitDto dto = new TemporaryContractPermitDto();
        dto.setId(1);
        dto.setId_contract(10);
        dto.setId_assigned(99);

        TemporaryContractPermit existing = GENERATOR.nextObject(TemporaryContractPermit.class);
        Contract contract = GENERATOR.nextObject(Contract.class);

        when(temporarycontractpermitCrud.findById(1)).thenReturn(Optional.of(existing));
        when(contractService.getContractById(10)).thenReturn(contract);
        when(userCrud.findById(99)).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> service.updateTemporaryContractPermit(dto));
    }

    @Test
    void deleteTemporaryContractPermit_success() {
        Integer id = 11;
        TemporaryContractPermit entity = GENERATOR.nextObject(TemporaryContractPermit.class);
        when(temporarycontractpermitCrud.findById(id)).thenReturn(Optional.of(entity));

        ResponseSuccessfullyDto resp = service.deleteTemporaryContractPermit(id);

        assertThat(resp.getCode()).isEqualTo(HttpStatus.OK);
        assertThat(resp.getMessage()).isEqualTo("Registro eliminado con Éxito");
        verify(temporarycontractpermitCrud).delete(entity);
    }

    @Test
    void deleteTemporaryContractPermit_notFound() {
        when(temporarycontractpermitCrud.findById(77)).thenReturn(Optional.empty());
        assertThrows(BusinessException.class, () -> service.deleteTemporaryContractPermit(77));
    }
}
