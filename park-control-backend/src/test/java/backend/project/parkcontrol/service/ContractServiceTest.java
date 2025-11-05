package backend.project.parkcontrol.service;

import backend.project.parkcontrol.dto.request.NewContractDto;
import backend.project.parkcontrol.dto.response.ContractDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.exception.BusinessException;
import backend.project.parkcontrol.repository.crud.ContractCrud;
import backend.project.parkcontrol.repository.crud.UserCrud;
import backend.project.parkcontrol.repository.entities.Contract;
import backend.project.parkcontrol.repository.entities.SubscriptionPlan;
import backend.project.parkcontrol.repository.entities.UserEntity;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
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
class ContractServiceTest {

    @Mock
    private ContractCrud contractCrud;

    @Mock
    private UserCrud userCrud;

    @Mock
    private SubscriptionPlanService subscriptionPlanService;

    @Mock
    private ValidationService validationService;

    @Mock
    private ContractHistoryService contractHistoryService;

    @InjectMocks
    private ContractService service;

    private static final EasyRandom GENERATOR = new EasyRandom();

    private Contract contract;
    private UserEntity user;
    private SubscriptionPlan plan;

    @BeforeEach
    void setup() {
        contract = GENERATOR.nextObject(Contract.class);
        user = GENERATOR.nextObject(UserEntity.class);
        plan = GENERATOR.nextObject(SubscriptionPlan.class);
    }

    @Test
    void getById_user_success() {
        List<Contract> expected = List.of(contract);
        when(contractCrud.findById_user(1)).thenReturn(expected);

        List<Contract> result = service.getById_user(1);

        assertThat(result).isEqualTo(expected);
        verify(contractCrud).findById_user(1);
    }

    @Test
    void getByLicense_plate_success() {
        List<Contract> expected = List.of(contract);
        when(contractCrud.findByLicense_plate("ABC123")).thenReturn(expected);

        List<Contract> result = service.getByLicense_plate("ABC123");

        assertThat(result).isEqualTo(expected);
        verify(contractCrud).findByLicense_plate("ABC123");
    }

    @Test
    void getByLicense_plateIsActive_success() {
        List<Contract> expected = List.of(contract);
        when(contractCrud.findByLicense_plateActive("XYZ999", true)).thenReturn(expected);

        List<Contract> result = service.getByLicense_plateIsActive("XYZ999");

        assertThat(result).isEqualTo(expected);
        verify(contractCrud).findByLicense_plateActive("XYZ999", true);
    }

    @Test
    void getAllContractList_success() {
        List<Contract> expected = Arrays.asList(GENERATOR.nextObject(Contract.class), GENERATOR.nextObject(Contract.class));
        when(contractCrud.findAll()).thenReturn(expected);

        List<Contract> result = service.getAllContractList();

        assertThat(result).isEqualTo(expected);
        verify(contractCrud).findAll();
    }

    @Test
    void getContractById_success() {
        when(contractCrud.findById(5)).thenReturn(Optional.of(contract));

        Contract result = service.getContractById(5);

        assertThat(result).isEqualTo(contract);
        verify(contractCrud).findById(5);
    }

    @Test
    void deleteContract_success() {
        when(contractCrud.findById(7)).thenReturn(Optional.of(contract));

        ResponseSuccessfullyDto resp = service.deleteContract(7);

        assertThat(resp.getCode()).isEqualTo(HttpStatus.OK);
        assertThat(resp.getMessage()).isEqualTo("Registro eliminado con Éxito");
        verify(contractCrud).delete(contract);
    }

    @Test
    void createContract_success() {
        NewContractDto dto = new NewContractDto();
        dto.setId_user(1);
        dto.setId_plan(2);
        dto.setIs_4r(true);
        dto.setLicense_plate("NEW123");
        dto.setMonths(6);

        when(userCrud.findById(1)).thenReturn(Optional.of(user));
        when(subscriptionPlanService.getSubscriptionPlanById(2)).thenReturn(plan);
        when(contractCrud.findByLicense_plate("NEW123")).thenReturn(Collections.emptyList());
        when(contractCrud.save(any(Contract.class))).thenAnswer(inv -> {
            Contract saved = inv.getArgument(0);
            saved.setId(100);
            return saved;
        });

        ResponseSuccessfullyDto resp = service.createContract(dto);

        assertThat(resp.getCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(resp.getMessage()).isEqualTo("Registro creado con Éxito");
        verify(contractCrud).save(any(Contract.class));
        verify(contractHistoryService).createContractHistory(any(Contract.class));
        verify(validationService).validatePositiveNumber(dto.getMonths(), "meses");
    }

    @Test
    void createContract_shouldThrowIfPlateExists() {
        NewContractDto dto = new NewContractDto();
        dto.setId_user(1);
        dto.setId_plan(2);
        dto.setLicense_plate("DUPLICATE");
        dto.setMonths(6);

        lenient().when(contractCrud.findByLicense_plate("DUPLICATE")).thenReturn(List.of(contract));

        assertThrows(BusinessException.class, () -> service.createContract(dto));
    }

    @Test
    void createContract_shouldThrowIfMonthsExceedLimit() {
        NewContractDto dto = new NewContractDto();
        dto.setId_user(1);
        dto.setId_plan(2);
        dto.setLicense_plate("VALID123");
        dto.setMonths(15);

        when(contractCrud.findByLicense_plate("VALID123")).thenReturn(Collections.emptyList());
        when(userCrud.findById(1)).thenReturn(Optional.of(user));
        when(subscriptionPlanService.getSubscriptionPlanById(2)).thenReturn(plan);
        doNothing().when(validationService).validatePositiveNumber(anyInt(), anyString());

        assertThrows(BusinessException.class, () -> service.createContract(dto));
    }

    @Test
    void updateContract_success() {
        ContractDto dto = new ContractDto();
        dto.setId(10);
        dto.setId_user(1);
        dto.setId_plan(2);
        dto.setIs_4r(true);
        dto.setLicense_plate("UPDATED123");
        dto.setStart_date(LocalDateTime.now());
        dto.setEnd_date(LocalDateTime.now().plusMonths(3));
        dto.setMonths(3);
        dto.setIs_anual(false);
        dto.setActive(true);

        when(contractCrud.findById(10)).thenReturn(Optional.of(contract));
        when(userCrud.findById(1)).thenReturn(Optional.of(user));
        when(subscriptionPlanService.getSubscriptionPlanById(2)).thenReturn(plan);
        when(contractCrud.save(any(Contract.class))).thenReturn(contract);

        ResponseSuccessfullyDto resp = service.updateContract(dto);

        assertThat(resp.getCode()).isEqualTo(HttpStatus.OK);
        assertThat(resp.getMessage()).isEqualTo("Registro actualizado con Éxito");
        verify(contractCrud).save(any(Contract.class));
    }

    @Test
    void getContract_responses() {
        Integer id = 11;
        when(contractCrud.findById(id)).thenReturn(Optional.of(contract));
        when(contractCrud.findAll()).thenReturn(List.of(contract));
        when(contractCrud.findById_user(1)).thenReturn(List.of(contract));
        when(contractCrud.findByLicense_plate("AAA111")).thenReturn(List.of(contract));

        ResponseSuccessfullyDto one = service.getContract(id);
        assertThat(one.getCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(one.getMessage()).isEqualTo("Registro encontrado con Éxito");
        assertThat(one.getBody()).isEqualTo(contract);

        ResponseSuccessfullyDto all = service.getAllContractListResponse();
        assertThat(all.getCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(all.getBody()).isEqualTo(List.of(contract));

        ResponseSuccessfullyDto byUserEntity = service.getById_userResponse(1);
        assertThat(byUserEntity.getCode()).isEqualTo(HttpStatus.FOUND);

        ResponseSuccessfullyDto byPlate = service.getByLicense_plateResponse("AAA111");
        assertThat(byPlate.getCode()).isEqualTo(HttpStatus.FOUND);
    }
}
