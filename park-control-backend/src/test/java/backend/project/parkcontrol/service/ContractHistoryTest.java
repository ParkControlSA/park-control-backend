package backend.project.parkcontrol.service;

import backend.project.parkcontrol.dto.response.ContractHistoryDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.repository.crud.ContractHistoryCrud;
import backend.project.parkcontrol.repository.entities.Contract;
import backend.project.parkcontrol.repository.entities.ContractHistory;
import backend.project.parkcontrol.repository.entities.SubscriptionPlan;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContractHistoryTest {

    @Mock
    private ContractHistoryCrud contracthistoryCrud;

    @InjectMocks
    private ContractHistoryService service;

    private static final EasyRandom GENERATOR = new EasyRandom();

    @Test
    void getById_contract() {
        Integer contractId = 1;
        List<ContractHistory> expected = Arrays.asList(
                GENERATOR.nextObject(ContractHistory.class),
                GENERATOR.nextObject(ContractHistory.class)
        );

        when(contracthistoryCrud.findById_contract(contractId)).thenReturn(expected);

        List<ContractHistory> result = service.getById_contract(contractId);

        assertThat(result).isEqualTo(expected);
        assertThat(result.size()).isEqualTo(2);
        verify(contracthistoryCrud).findById_contract(contractId);
    }

    @Test
    void findByContractAndDate() {
        Integer contractId = 1;
        Date date = Date.valueOf("2024-01-15");
        List<ContractHistory> expected = Arrays.asList(
                GENERATOR.nextObject(ContractHistory.class)
        );

        when(contracthistoryCrud.findByContractAndDate(contractId, date)).thenReturn(expected);

        List<ContractHistory> result = service.findByContractAndDate(contractId, date);

        assertThat(result).isEqualTo(expected);
        assertThat(result.size()).isEqualTo(1);
        verify(contracthistoryCrud).findByContractAndDate(contractId, date);
    }

    @Test
    void getAllContractHistoryList() {
        List<ContractHistory> expected = Arrays.asList(
                GENERATOR.nextObject(ContractHistory.class),
                GENERATOR.nextObject(ContractHistory.class)
        );

        when(contracthistoryCrud.findAll()).thenReturn(expected);

        List<ContractHistory> result = service.getAllContractHistoryList();

        assertThat(result).isEqualTo(expected);
        assertThat(result.size()).isEqualTo(2);
        verify(contracthistoryCrud).findAll();
    }

    @Test
    void getContractHistoryById() {
        Integer id = 5;
        ContractHistory entity = GENERATOR.nextObject(ContractHistory.class);
        entity.setId(id);

        when(contracthistoryCrud.findById(id)).thenReturn(Optional.of(entity));

        ContractHistory result = service.getContractHistoryById(id);

        assertThat(result).isEqualTo(entity);
        verify(contracthistoryCrud).findById(id);
    }

    @Test
    void deleteContractHistory() {
        Integer id = 7;
        ContractHistory entity = GENERATOR.nextObject(ContractHistory.class);
        entity.setId(id);

        when(contracthistoryCrud.findById(id)).thenReturn(Optional.of(entity));

        ResponseSuccessfullyDto resp = service.deleteContractHistory(id);

        assertThat(resp.getCode()).isEqualTo(HttpStatus.OK);
        assertThat(resp.getMessage()).isEqualTo("Registro eliminado con Éxito");
        verify(contracthistoryCrud).delete(entity);
    }

    @Test
    void createContractHistory_planId2_allDays() {
        Contract contract = new Contract();
        contract.setId(1);
        contract.setMonths(1);

        SubscriptionPlan plan = new SubscriptionPlan();
        plan.setId(2);
        plan.setDaily_hours(8);
        contract.setSubscriptionPlan(plan);

        ArgumentCaptor<ContractHistory> captor = ArgumentCaptor.forClass(ContractHistory.class);
        when(contracthistoryCrud.save(captor.capture())).thenAnswer(invocation -> {
            ContractHistory ch = invocation.getArgument(0);
            ch.setId(GENERATOR.nextInt());
            return ch;
        });

        ResponseSuccessfullyDto resp = service.createContractHistory(contract);

        assertThat(resp.getCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(resp.getMessage()).isEqualTo("Registros creados con Éxito");
        
        // Verificar que se guardaron múltiples registros (aproximadamente 30 días)
        verify(contracthistoryCrud, atLeast(28)).save(any(ContractHistory.class));
    }

    @Test
    void createContractHistory_planId5_allDays() {
        Contract contract = new Contract();
        contract.setId(1);
        contract.setMonths(1);

        SubscriptionPlan plan = new SubscriptionPlan();
        plan.setId(5);
        plan.setDaily_hours(10);
        contract.setSubscriptionPlan(plan);

        when(contracthistoryCrud.save(any(ContractHistory.class))).thenAnswer(invocation -> {
            ContractHistory ch = invocation.getArgument(0);
            ch.setId(GENERATOR.nextInt());
            return ch;
        });

        ResponseSuccessfullyDto resp = service.createContractHistory(contract);

        assertThat(resp.getCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(resp.getMessage()).isEqualTo("Registros creados con Éxito");
        
        verify(contracthistoryCrud, atLeast(28)).save(any(ContractHistory.class));
    }

    @Test
    void createContractHistory_planId3_weekdaysOnly() {
        Contract contract = new Contract();
        contract.setId(1);
        contract.setMonths(1);

        SubscriptionPlan plan = new SubscriptionPlan();
        plan.setId(3);
        plan.setDaily_hours(6);
        contract.setSubscriptionPlan(plan);

        ArgumentCaptor<ContractHistory> captor = ArgumentCaptor.forClass(ContractHistory.class);
        when(contracthistoryCrud.save(captor.capture())).thenAnswer(invocation -> {
            ContractHistory ch = invocation.getArgument(0);
            ch.setId(GENERATOR.nextInt());
            return ch;
        });

        ResponseSuccessfullyDto resp = service.createContractHistory(contract);

        assertThat(resp.getCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(resp.getMessage()).isEqualTo("Registros creados con Éxito");
        
        // Verificar que se guardaron registros (solo días laborables, aproximadamente 20-22 días)
        verify(contracthistoryCrud, atLeast(20)).save(any(ContractHistory.class));
        
        // Verificar que los días guardados no son sábado ni domingo
        List<ContractHistory> saved = captor.getAllValues();
        assertThat(saved.size()).isGreaterThan(0);
        for (ContractHistory ch : saved) {
            DayOfWeek dayOfWeek = ch.getDate().getDayOfWeek();
            assertThat(dayOfWeek).isNotEqualTo(DayOfWeek.SATURDAY);
            assertThat(dayOfWeek).isNotEqualTo(DayOfWeek.SUNDAY);
        }
    }

    @Test
    void createContractHistory_planId4_weekdaysOnly() {
        Contract contract = new Contract();
        contract.setId(1);
        contract.setMonths(1);

        SubscriptionPlan plan = new SubscriptionPlan();
        plan.setId(4);
        plan.setDaily_hours(4);
        contract.setSubscriptionPlan(plan);

        when(contracthistoryCrud.save(any(ContractHistory.class))).thenAnswer(invocation -> {
            ContractHistory ch = invocation.getArgument(0);
            ch.setId(GENERATOR.nextInt());
            return ch;
        });

        ResponseSuccessfullyDto resp = service.createContractHistory(contract);

        assertThat(resp.getCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(resp.getMessage()).isEqualTo("Registros creados con Éxito");
        
        verify(contracthistoryCrud, atLeast(20)).save(any(ContractHistory.class));
    }

    @Test
    void createContractHistory_planNocturno() {
        Contract contract = new Contract();
        contract.setId(1);
        contract.setMonths(1);

        SubscriptionPlan plan = new SubscriptionPlan();
        plan.setId(1); // Plan diferente (nocturno)
        plan.setDaily_hours(12);
        contract.setSubscriptionPlan(plan);

        ResponseSuccessfullyDto resp = service.createContractHistory(contract);

        assertThat(resp.getCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(resp.getMessage()).isEqualTo("Registros creados con Éxito");
        
        // Para el plan nocturno, no se guardan registros (lógica no implementada)
        verify(contracthistoryCrud, never()).save(any(ContractHistory.class));
    }

    @Test
    void updateContractHistory() {
        ContractHistoryDto dto = new ContractHistoryDto();
        dto.setId(1);
        dto.setIncluded_hours(8);
        dto.setConsumed_hours(2);
        dto.setDate(LocalDateTime.now());

        ContractHistory existing = GENERATOR.nextObject(ContractHistory.class);
        existing.setId(dto.getId());

        when(contracthistoryCrud.findById(dto.getId())).thenReturn(Optional.of(existing));
        when(contracthistoryCrud.save(any(ContractHistory.class))).thenReturn(existing);

        ResponseSuccessfullyDto resp = service.updateContractHistory(dto);

        assertThat(resp.getCode()).isEqualTo(HttpStatus.OK);
        assertThat(resp.getMessage()).isEqualTo("Registro actualizado con Éxito");
        assertThat(existing.getIncluded_hours()).isEqualTo(dto.getIncluded_hours());
        assertThat(existing.getConsumed_hours()).isEqualTo(dto.getConsumed_hours());
        assertThat(existing.getDate()).isEqualTo(dto.getDate());
        verify(contracthistoryCrud).save(existing);
    }

    @Test
    void getContractHistory() {
        Integer id = 33;
        ContractHistory entity = GENERATOR.nextObject(ContractHistory.class);
        entity.setId(id);

        when(contracthistoryCrud.findById(id)).thenReturn(Optional.of(entity));

        ResponseSuccessfullyDto resp = service.getContractHistory(id);

        assertThat(resp.getCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(resp.getMessage()).isEqualTo("Registro encontrado con Éxito");
        assertThat(resp.getBody()).isEqualTo(entity);
    }

    @Test
    void getAllContractHistoryListResponse() {
        List<ContractHistory> list = Arrays.asList(
                GENERATOR.nextObject(ContractHistory.class),
                GENERATOR.nextObject(ContractHistory.class)
        );

        when(contracthistoryCrud.findAll()).thenReturn(list);

        ResponseSuccessfullyDto resp = service.getAllContractHistoryListResponse();

        assertThat(resp.getCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(resp.getMessage()).isEqualTo("Registros encontrados con Éxito");
        assertThat(resp.getBody()).isEqualTo(list);
    }

    @Test
    void getById_contractResponse() {
        Integer contractId = 10;
        List<ContractHistory> list = Arrays.asList(
                GENERATOR.nextObject(ContractHistory.class)
        );

        when(contracthistoryCrud.findById_contract(contractId)).thenReturn(list);

        ResponseSuccessfullyDto resp = service.getById_contractResponse(contractId);

        assertThat(resp.getCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(resp.getMessage()).isEqualTo("Registros encontrados con Éxito");
        assertThat(resp.getBody()).isEqualTo(list);
    }

    @Test
    void createContractHistory_saveContractHistory_verifiesFields() {
        Contract contract = new Contract();
        contract.setId(1);
        contract.setMonths(1);

        SubscriptionPlan plan = new SubscriptionPlan();
        plan.setId(2);
        plan.setDaily_hours(8);
        contract.setSubscriptionPlan(plan);

        ArgumentCaptor<ContractHistory> captor = ArgumentCaptor.forClass(ContractHistory.class);
        when(contracthistoryCrud.save(captor.capture())).thenAnswer(invocation -> {
            ContractHistory ch = invocation.getArgument(0);
            ch.setId(GENERATOR.nextInt());
            return ch;
        });

        service.createContractHistory(contract);

        // Verificar que al menos un registro fue guardado con los campos correctos
        List<ContractHistory> saved = captor.getAllValues();
        assertThat(saved.size()).isGreaterThan(0);
        
        ContractHistory first = saved.get(0);
        assertThat(first.getContract()).isEqualTo(contract);
        assertThat(first.getIncluded_hours()).isEqualTo(plan.getDaily_hours());
        assertThat(first.getConsumed_hours()).isEqualTo(0);
        assertThat(first.getDate()).isNotNull();
    }
}

