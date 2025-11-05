package backend.project.parkcontrol.service;

import backend.project.parkcontrol.dto.request.NewLicensePlateBlockRequestDto;
import backend.project.parkcontrol.dto.response.LicensePlateBlockRequestDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.enums.LicensePlateBlockRequestStatus;
import backend.project.parkcontrol.exception.BusinessException;
import backend.project.parkcontrol.repository.crud.ContractCrud;
import backend.project.parkcontrol.repository.crud.LicensePlateBlockRequestCrud;
import backend.project.parkcontrol.repository.crud.UserCrud;
import backend.project.parkcontrol.repository.entities.Contract;
import backend.project.parkcontrol.repository.entities.LicensePlateBlockRequest;
import backend.project.parkcontrol.repository.entities.UserEntity;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LicensePlateBlockRequestServiceTest {

    @Mock
    private LicensePlateBlockRequestCrud licensePlateBlockRequestCrud;
    @Mock
    private ContractService contractService;
    @Mock
    private ContractCrud contractCrud;
    @Mock
    private UserCrud userCrud;

    @InjectMocks
    private LicensePlateBlockRequestService service;

    private static final EasyRandom GENERATOR = new EasyRandom();

    // ==============================
    // GETTERS
    // ==============================

    @Test
    void getById_contract_success() {
        List<LicensePlateBlockRequest> expected = Arrays.asList(
                GENERATOR.nextObject(LicensePlateBlockRequest.class),
                GENERATOR.nextObject(LicensePlateBlockRequest.class)
        );
        when(licensePlateBlockRequestCrud.findById_contract(1)).thenReturn(expected);

        List<LicensePlateBlockRequest> result = service.getById_contract(1);

        assertThat(result).isEqualTo(expected);
        verify(licensePlateBlockRequestCrud).findById_contract(1);
    }

    @Test
    void getById_assigned_success() {
        List<LicensePlateBlockRequest> expected = Arrays.asList(GENERATOR.nextObject(LicensePlateBlockRequest.class));
        when(licensePlateBlockRequestCrud.findById_assigned(2)).thenReturn(expected);

        List<LicensePlateBlockRequest> result = service.getById_assigned(2);

        assertThat(result).isEqualTo(expected);
        verify(licensePlateBlockRequestCrud).findById_assigned(2);
    }

    @Test
    void getAllLicensePlateBlockRequestList_success() {
        List<LicensePlateBlockRequest> expected = Arrays.asList(GENERATOR.nextObject(LicensePlateBlockRequest.class));
        when(licensePlateBlockRequestCrud.findAll()).thenReturn(expected);

        List<LicensePlateBlockRequest> result = service.getAllLicensePlateBlockRequestList();

        assertThat(result).isEqualTo(expected);
        verify(licensePlateBlockRequestCrud).findAll();
    }

    @Test
    void getAllLicensePlateBlockRequestListByStatus_success() {
        List<LicensePlateBlockRequest> expected = Arrays.asList(GENERATOR.nextObject(LicensePlateBlockRequest.class));
        when(licensePlateBlockRequestCrud.findAllByStatus(3)).thenReturn(expected);

        List<LicensePlateBlockRequest> result = service.getAllLicensePlateBlockRequestListByStatus(3);

        assertThat(result).isEqualTo(expected);
        verify(licensePlateBlockRequestCrud).findAllByStatus(3);
    }

    @Test
    void getLicensePlateBlockRequestById_success() {
        LicensePlateBlockRequest entity = GENERATOR.nextObject(LicensePlateBlockRequest.class);
        when(licensePlateBlockRequestCrud.findById(5)).thenReturn(Optional.of(entity));

        LicensePlateBlockRequest result = service.getLicensePlateBlockRequestById(5);

        assertThat(result).isEqualTo(entity);
        verify(licensePlateBlockRequestCrud).findById(5);
    }

    // ==============================
    // CRUD Methods
    // ==============================

    @Test
    void createLicensePlateBlockRequest_success() {
        NewLicensePlateBlockRequestDto dto = new NewLicensePlateBlockRequestDto();
        dto.setId_contract(1);
        dto.setNew_plate("NEW-123");
        dto.setEvidence_url("url");
        dto.setNote("note");
        dto.setIs_4r(true);

        Contract contract = new Contract();
        contract.setLicense_plate("OLD-999");
        contract.setActive(true);

        when(licensePlateBlockRequestCrud.findById_contract(1)).thenReturn(Collections.emptyList());
        when(contractService.getContractById(1)).thenReturn(contract);
        when(contractService.getByLicense_plateIsActive("NEW-123")).thenReturn(Collections.emptyList());
        when(licensePlateBlockRequestCrud.save(any(LicensePlateBlockRequest.class))).thenReturn(new LicensePlateBlockRequest());

        ResponseSuccessfullyDto resp = service.createLicensePlateBlockRequest(dto);

        assertThat(resp.getCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(resp.getMessage()).isEqualTo("Registro creado con Éxito");
        verify(licensePlateBlockRequestCrud).save(any(LicensePlateBlockRequest.class));
    }

    @Test
    void createLicensePlateBlockRequest_shouldThrowIfAlreadyRequested() {
        NewLicensePlateBlockRequestDto dto = new NewLicensePlateBlockRequestDto();
        dto.setId_contract(1);
        dto.setNew_plate("ABC");
        dto.setIs_4r(false);

        when(licensePlateBlockRequestCrud.findById_contract(1))
                .thenReturn(List.of(GENERATOR.nextObject(LicensePlateBlockRequest.class)));

        assertThrows(BusinessException.class, () -> service.createLicensePlateBlockRequest(dto));
    }

    @Test
    void createLicensePlateBlockRequest_shouldThrowIfContractInactive() {
        NewLicensePlateBlockRequestDto dto = new NewLicensePlateBlockRequestDto();
        dto.setId_contract(1);
        dto.setNew_plate("DEF");
        dto.setIs_4r(true);

        when(licensePlateBlockRequestCrud.findById_contract(1)).thenReturn(Collections.emptyList());
        Contract contract = new Contract();
        contract.setActive(false);
        when(contractService.getContractById(1)).thenReturn(contract);

        assertThrows(BusinessException.class, () -> service.createLicensePlateBlockRequest(dto));
    }

    @Test
    void createLicensePlateBlockRequest_shouldThrowIfNewPlateActive() {
        NewLicensePlateBlockRequestDto dto = new NewLicensePlateBlockRequestDto();
        dto.setId_contract(1);
        dto.setNew_plate("XYZ");
        dto.setIs_4r(true);

        when(licensePlateBlockRequestCrud.findById_contract(1)).thenReturn(Collections.emptyList());
        Contract contract = new Contract();
        contract.setActive(true);
        when(contractService.getContractById(1)).thenReturn(contract);
        when(contractService.getByLicense_plateIsActive("XYZ"))
                .thenReturn(List.of(GENERATOR.nextObject(Contract.class)));

        assertThrows(BusinessException.class, () -> service.createLicensePlateBlockRequest(dto));
    }

    @Test
    void changeStatus_shouldApproveChange() {
        LicensePlateBlockRequest request = new LicensePlateBlockRequest();
        Contract contract = new Contract();
        contract.setLicense_plate("OLD");
        request.setContract(contract);
        request.setNew_plate("NEW");
        request.setIs_4r(true);

        when(licensePlateBlockRequestCrud.findById(1)).thenReturn(Optional.of(request));
        when(userCrud.findById(2)).thenReturn(Optional.of(new UserEntity()));

        ResponseSuccessfullyDto resp = service.changeStatus(2, 1, 2);

        assertThat(resp.getCode()).isEqualTo(HttpStatus.OK);
        assertThat(resp.getMessage()).isEqualTo("El Cambio de Placa se ha realizado correctamente.");
        verify(contractCrud).save(any(Contract.class));
        verify(licensePlateBlockRequestCrud).save(any(LicensePlateBlockRequest.class));
    }

    @Test
    void changeStatus_shouldRejectChange() {
        LicensePlateBlockRequest request = GENERATOR.nextObject(LicensePlateBlockRequest.class);
        when(licensePlateBlockRequestCrud.findById(1)).thenReturn(Optional.of(request));
        when(userCrud.findById(2)).thenReturn(Optional.of(new UserEntity()));

        ResponseSuccessfullyDto resp = service.changeStatus(2, 1, 3);

        assertThat(resp.getMessage()).isEqualTo("El Cambio de Placa ha sido rechazado.");
        verify(licensePlateBlockRequestCrud).save(any(LicensePlateBlockRequest.class));
    }

    @Test
    void changeStatus_shouldRevokeChange() {
        LicensePlateBlockRequest request = new LicensePlateBlockRequest();
        Contract contract = new Contract();
        contract.setLicense_plate("NEW");
        request.setContract(contract);
        request.setOld_plate("OLD");
        request.setIs_4r(false);

        when(licensePlateBlockRequestCrud.findById(1)).thenReturn(Optional.of(request));
        when(userCrud.findById(2)).thenReturn(Optional.of(new UserEntity()));

        ResponseSuccessfullyDto resp = service.changeStatus(2, 1, 4);

        assertThat(resp.getMessage()).isEqualTo("El Cambio de Placa ha sido revocado, se ha restaurado la placa original.");
        verify(contractCrud).save(any(Contract.class));
    }

    @Test
    void changeStatus_shouldThrowIfPendingOrInvalid() {
        LicensePlateBlockRequest request = GENERATOR.nextObject(LicensePlateBlockRequest.class);
        when(licensePlateBlockRequestCrud.findById(1)).thenReturn(Optional.of(request));

        assertThrows(BusinessException.class, () -> service.changeStatus(1, 1, 1));
        assertThrows(BusinessException.class, () -> service.changeStatus(1, 1, 99));
    }

    @Test
    void updateLicensePlateBlockRequest_success() {
        LicensePlateBlockRequestDto dto = new LicensePlateBlockRequestDto();
        dto.setId(1);
        dto.setId_contract(10);
        dto.setNew_plate("NEW-PLT");
        dto.setEvidence_url("url");
        dto.setNote("note");
        dto.setIs_4r(true);

        LicensePlateBlockRequest existing = new LicensePlateBlockRequest();
        when(licensePlateBlockRequestCrud.findById(1)).thenReturn(Optional.of(existing));
        when(contractService.getContractById(10)).thenReturn(new Contract());
        when(licensePlateBlockRequestCrud.save(any(LicensePlateBlockRequest.class))).thenReturn(existing);

        ResponseSuccessfullyDto resp = service.updateLicensePlateBlockRequest(dto);

        assertThat(resp.getCode()).isEqualTo(HttpStatus.OK);
        assertThat(resp.getMessage()).isEqualTo("Registro actualizado con Éxito");
        verify(licensePlateBlockRequestCrud).save(existing);
    }

    @Test
    void deleteLicensePlateBlockRequest_success() {
        LicensePlateBlockRequest entity = GENERATOR.nextObject(LicensePlateBlockRequest.class);
        when(licensePlateBlockRequestCrud.findById(7)).thenReturn(Optional.of(entity));

        ResponseSuccessfullyDto resp = service.deleteLicensePlateBlockRequest(7);

        assertThat(resp.getCode()).isEqualTo(HttpStatus.OK);
        assertThat(resp.getMessage()).isEqualTo("Registro eliminado con Éxito");
        verify(licensePlateBlockRequestCrud).delete(entity);
    }

    // ==============================
    // ResponseSuccessfullyDto Getters
    // ==============================

    @Test
    void getResponses_success() {
        LicensePlateBlockRequest entity = GENERATOR.nextObject(LicensePlateBlockRequest.class);
        List<LicensePlateBlockRequest> list = List.of(entity);

        when(licensePlateBlockRequestCrud.findById(1)).thenReturn(Optional.of(entity));
        when(licensePlateBlockRequestCrud.findAll()).thenReturn(list);
        when(licensePlateBlockRequestCrud.findAllByStatus(2)).thenReturn(list);
        when(licensePlateBlockRequestCrud.findById_contract(3)).thenReturn(list);
        when(licensePlateBlockRequestCrud.findById_assigned(4)).thenReturn(list);

        ResponseSuccessfullyDto single = service.getLicensePlateBlockRequest(1);
        assertThat(single.getCode()).isEqualTo(HttpStatus.FOUND);

        ResponseSuccessfullyDto all = service.getAllLicensePlateBlockRequestListResponse();
        assertThat(all.getBody()).isEqualTo(list);

        ResponseSuccessfullyDto byStatus = service.getAllLicensePlateBlockRequestListByStatusResponse(2);
        assertThat(byStatus.getBody()).isEqualTo(list);

        ResponseSuccessfullyDto byContract = service.getById_contractResponse(3);
        assertThat(byContract.getBody()).isEqualTo(list);

        ResponseSuccessfullyDto byAssigned = service.getById_assignedResponse(4);
        assertThat(byAssigned.getBody()).isEqualTo(list);
    }
}
