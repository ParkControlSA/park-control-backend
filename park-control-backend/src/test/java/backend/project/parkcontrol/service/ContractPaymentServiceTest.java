package backend.project.parkcontrol.service;

import backend.project.parkcontrol.dto.request.NewContractPaymentDto;
import backend.project.parkcontrol.dto.response.ContractPaymentDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.repository.crud.ContractPaymentCrud;
import backend.project.parkcontrol.repository.entities.Contract;
import backend.project.parkcontrol.repository.entities.ContractPayment;
import backend.project.parkcontrol.repository.entities.RateAssignment;
import backend.project.parkcontrol.repository.entities.SubscriptionPlan;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ContractPaymentServiceTest {

    @Mock
    private ContractPaymentCrud contractpaymentCrud;

    @Mock
    private ContractService contractService;

    @Mock
    private RateAssignmentService rateAssignmentService;

    @Mock
    private SubscriptionPlanService subscriptionPlanService;

    @InjectMocks
    private ContractPaymentService service;

    @Test
    void getById_contract() {
        Integer contractId = 1;
        List<ContractPayment> expected = Arrays.asList(new ContractPayment(), new ContractPayment());

        when(contractpaymentCrud.findById_contract(contractId)).thenReturn(expected);

        List<ContractPayment> result = service.getById_contract(contractId);

        assertThat(result).isEqualTo(expected);
        assertThat(result.size()).isEqualTo(2);
        verify(contractpaymentCrud).findById_contract(contractId);
    }

    @Test
    void getAllContractPaymentList() {
        List<ContractPayment> expected = Arrays.asList(new ContractPayment(), new ContractPayment());
        when(contractpaymentCrud.findAll()).thenReturn(expected);

        List<ContractPayment> result = service.getAllContractPaymentList();

        assertThat(result).isEqualTo(expected);
        assertThat(result.size()).isEqualTo(2);
        verify(contractpaymentCrud).findAll();
    }

    @Test
    void getContractPaymentById() {
        Integer id = 7;
        ContractPayment entity = new ContractPayment();
        entity.setId(id);
        when(contractpaymentCrud.findById(id)).thenReturn(Optional.of(entity));

        ContractPayment result = service.getContractPaymentById(id);

        assertThat(result).isEqualTo(entity);
        verify(contractpaymentCrud).findById(id);
    }

    @Test
    void deleteContractPayment_success() {
        Integer id = 9;
        ContractPayment entity = new ContractPayment();
        entity.setId(id);
        when(contractpaymentCrud.findById(id)).thenReturn(Optional.of(entity));

        ResponseSuccessfullyDto resp = service.deleteContractPayment(id);

        assertThat(resp.getCode()).isEqualTo(HttpStatus.OK);
        assertThat(resp.getMessage()).isEqualTo("Registro eliminado con Éxito");
        verify(contractpaymentCrud).delete(entity);
    }

    @Test
    void createContractPayment_success_monthly() {
        // Arrange
        NewContractPaymentDto dto = new NewContractPaymentDto();
        dto.setId_contract(100);
        dto.setPayment_method(1); // tipo entero esperado por entidad/dto

        SubscriptionPlan plan = new SubscriptionPlan();
        plan.setMonth_hours(20);         // horas por mes
        plan.setTotal_discount(10.0);      // 10% descuento mensual
        plan.setAnnual_discount(12.0);     // 12% anual (se prorratea internamente si aplica)

        Contract contract = new Contract();
        contract.setId(dto.getId_contract());
        contract.setMonths(3);             // 3 meses
        contract.setSubscriptionPlan(plan);
        contract.setIs_anual(false);       // caso mensual, sin descuento anual

        RateAssignment rate = new RateAssignment();
        rate.setHourly_rate(5.0);          // Q5 por hora

        when(contractService.getContractById(dto.getId_contract())).thenReturn(contract);
        when(rateAssignmentService.getRateAssignamentById_branchIsActive(1)).thenReturn(Arrays.asList(rate));

        ArgumentCaptor<ContractPayment> captor = ArgumentCaptor.forClass(ContractPayment.class);

        // Act
        ResponseSuccessfullyDto resp = service.createContractPayment(dto);

        // Assert
        assertThat(resp.getCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(resp.getMessage()).isEqualTo("Registro creado con Éxito");
        verify(contractpaymentCrud).save(captor.capture());
        ContractPayment saved = captor.getValue();

        double expectedSubtotal = contract.getMonths() * rate.getHourly_rate() * plan.getMonth_hours(); // 3*5*20 = 300
        double expectedMonthlyDiscount = expectedSubtotal * (plan.getTotal_discount() / 100.0);        // 30
        double expectedTotal = expectedSubtotal - expectedMonthlyDiscount;                              // 270

        assertThat(saved.getContract()).isEqualTo(contract);
        assertThat(saved.getSubtotal()).isEqualTo(expectedSubtotal);
        assertThat(saved.getMonthly_discount()).isEqualTo(expectedMonthlyDiscount);
        assertThat(saved.getAnnual_discount()).isEqualTo(0.0);
        assertThat(saved.getTotal()).isEqualTo(expectedTotal);
        assertThat(saved.getPayment_method()).isEqualTo(1);
    }

    @Test
    void createContractPayment_success_annual() {
        // Arrange
        NewContractPaymentDto dto = new NewContractPaymentDto();
        dto.setId_contract(200);
        dto.setPayment_method(2);

        SubscriptionPlan plan = new SubscriptionPlan();
        plan.setMonth_hours(40);
        plan.setTotal_discount(5.0);     // 5% mensual
        plan.setAnnual_discount(24.0);   // 24% anual total (2% mensual prorrateado)

        Contract contract = new Contract();
        contract.setId(dto.getId_contract());
        contract.setMonths(12);
        contract.setSubscriptionPlan(plan);
        contract.setIs_anual(true);

        RateAssignment rate = new RateAssignment();
        rate.setHourly_rate(10.0);

        when(contractService.getContractById(dto.getId_contract())).thenReturn(contract);
        when(rateAssignmentService.getRateAssignamentById_branchIsActive(1)).thenReturn(Arrays.asList(rate));

        ArgumentCaptor<ContractPayment> captor = ArgumentCaptor.forClass(ContractPayment.class);

        // Act
        ResponseSuccessfullyDto resp = service.createContractPayment(dto);

        // Assert
        assertThat(resp.getCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(resp.getMessage()).isEqualTo("Registro creado con Éxito");
        verify(contractpaymentCrud).save(captor.capture());
        ContractPayment saved = captor.getValue();

        double subtotal = contract.getMonths() * rate.getHourly_rate() * plan.getMonth_hours(); // 12*10*40 = 4800
        double monthlyDisc = subtotal * (plan.getTotal_discount() / 100.0);                     // 240
        double annualDisc = subtotal * ((plan.getAnnual_discount() / 12.0) / 100.0);            // 4800*(2%/100)=0.02? -> 4800*0.02=96
        double expectedTotal = subtotal - monthlyDisc - annualDisc;                              // 4800-240-96 = 4464

        assertThat(saved.getSubtotal()).isEqualTo(subtotal);
        assertThat(saved.getMonthly_discount()).isEqualTo(monthlyDisc);
        assertThat(saved.getAnnual_discount()).isEqualTo(annualDisc);
        assertThat(saved.getTotal()).isEqualTo(expectedTotal);
        assertThat(saved.getPayment_method()).isEqualTo(2);
    }

    @Test
    void updateContractPayment_success() {
        // Arrange
        ContractPaymentDto dto = new ContractPaymentDto();
        dto.setId(50);
        dto.setId_contract(60);
        dto.setSubtotal(1000.0);
        dto.setMonthly_discount(50.0);
        dto.setAnnual_discount(25.0);
        dto.setTotal(925.0);
        dto.setDate(LocalDateTime.now());
        dto.setPayment_method(1);

        ContractPayment existing = new ContractPayment();
        existing.setId(dto.getId());

        Contract contract = new Contract();
        contract.setId(dto.getId_contract());

        when(contractpaymentCrud.findById(dto.getId())).thenReturn(Optional.of(existing));
        when(contractService.getContractById(dto.getId_contract())).thenReturn(contract);
        when(contractpaymentCrud.save(any(ContractPayment.class))).thenReturn(existing);

        // Act
        ResponseSuccessfullyDto resp = service.updateContractPayment(dto);

        // Assert
        assertThat(resp.getCode()).isEqualTo(HttpStatus.OK);
        assertThat(resp.getMessage()).isEqualTo("Registro actualizado con Éxito");
        verify(contractpaymentCrud).save(any(ContractPayment.class));
    }

    @Test
    void responseGetters() {
        Integer id = 77;
        ContractPayment entity = new ContractPayment();
        entity.setId(id);
        when(contractpaymentCrud.findById(id)).thenReturn(Optional.of(entity));

        ResponseSuccessfullyDto single = service.getContractPayment(id);
        assertThat(single.getCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(single.getMessage()).isEqualTo("Registro encontrado con Éxito");
        assertThat(single.getBody()).isEqualTo(entity);

        List<ContractPayment> list = Arrays.asList(entity);
        when(contractpaymentCrud.findAll()).thenReturn(list);
        ResponseSuccessfullyDto all = service.getAllContractPaymentListResponse();
        assertThat(all.getCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(all.getMessage()).isEqualTo("Registros encontrados con Éxito");
        assertThat(all.getBody()).isEqualTo(list);

        when(contractpaymentCrud.findById_contract(id)).thenReturn(list);
        ResponseSuccessfullyDto byContract = service.getById_contractResponse(id);
        assertThat(byContract.getCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(byContract.getMessage()).isEqualTo("Registros encontrados con Éxito");
        assertThat(byContract.getBody()).isEqualTo(list);
    }
}


