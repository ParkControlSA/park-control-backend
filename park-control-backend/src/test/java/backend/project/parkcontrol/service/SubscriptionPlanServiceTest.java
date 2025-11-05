package backend.project.parkcontrol.service;

import backend.project.parkcontrol.dto.request.NewSubscriptionPlanDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.dto.response.SubscriptionPlanDto;
import backend.project.parkcontrol.exception.BusinessException;
import backend.project.parkcontrol.repository.crud.SubscriptionPlanCrud;
import backend.project.parkcontrol.repository.entities.SubscriptionPlan;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SubscriptionPlanServiceTest {

    @Mock
    private SubscriptionPlanCrud subscriptionPlanCrud;

    @InjectMocks
    private SubscriptionPlanService service;

    @Test
    void getAllSubscriptionPlanList() {
        List<SubscriptionPlan> expected = Arrays.asList(new SubscriptionPlan(), new SubscriptionPlan());
        when(subscriptionPlanCrud.findAll()).thenReturn(expected);

        List<SubscriptionPlan> result = service.getAllSubscriptionPlanList();

        assertThat(result).isEqualTo(expected);
        assertThat(result.size()).isEqualTo(2);
        verify(subscriptionPlanCrud).findAll();
    }

    @Test
    void getSubscriptionPlanById() {
        Integer id = 5;
        SubscriptionPlan entity = new SubscriptionPlan();
        entity.setId(id);
        when(subscriptionPlanCrud.findById(id)).thenReturn(Optional.of(entity));

        SubscriptionPlan result = service.getSubscriptionPlanById(id);

        assertThat(result).isEqualTo(entity);
        verify(subscriptionPlanCrud).findById(id);
    }

    @Test
    void createSubscriptionPlan_success() {
        NewSubscriptionPlanDto dto = new NewSubscriptionPlanDto();
        dto.setName("Plan A");
        dto.setMonth_hours(20);
        dto.setDaily_hours(2);
        dto.setTotal_discount(10.0);
        dto.setAnnual_discount(12.0);

        when(subscriptionPlanCrud.save(any(SubscriptionPlan.class))).thenAnswer(inv -> inv.getArgument(0));

        ResponseSuccessfullyDto resp = service.createSubscriptionPlan(dto);

        assertThat(resp.getCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(resp.getMessage()).isEqualTo("Registro creado con Éxito");
        verify(subscriptionPlanCrud).save(any(SubscriptionPlan.class));
    }

    @Test
    void createSubscriptionPlan_dataIntegrityViolationWith45000_throwsBadRequest() {
        NewSubscriptionPlanDto dto = new NewSubscriptionPlanDto();
        dto.setName("Dup");
        dto.setMonth_hours(10);
        dto.setDaily_hours(1);
        dto.setTotal_discount(5.0);
        dto.setAnnual_discount(10.0);

        RuntimeException root = new RuntimeException("error 45000 duplicate name");
        DataIntegrityViolationException dive = new DataIntegrityViolationException("dive", root);
        when(subscriptionPlanCrud.save(any(SubscriptionPlan.class))).thenThrow(dive);

        BusinessException ex = assertThrows(BusinessException.class, () -> service.createSubscriptionPlan(dto));
        assertThat(ex.getCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(ex.getMessage()).contains("45000");
    }

    @Test
    void createSubscriptionPlan_otherException_throwsInternalServerError() {
        NewSubscriptionPlanDto dto = new NewSubscriptionPlanDto();
        dto.setName("X");
        dto.setMonth_hours(5);
        dto.setDaily_hours(1);
        dto.setTotal_discount(0.0);
        dto.setAnnual_discount(0.0);

        when(subscriptionPlanCrud.save(any(SubscriptionPlan.class))).thenThrow(new RuntimeException("boom"));

        BusinessException ex = assertThrows(BusinessException.class, () -> service.createSubscriptionPlan(dto));
        assertThat(ex.getCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(ex.getMessage()).contains("Error inesperado");
    }

    @Test
    void updateSubscriptionPlan_success() {
        SubscriptionPlanDto dto = new SubscriptionPlanDto();
        dto.setId(7);
        dto.setName("Plan B");
        dto.setMonth_hours(30);
        dto.setDaily_hours(3);
        dto.setTotal_discount(15.0);
        dto.setAnnual_discount(20.0);

        SubscriptionPlan existing = new SubscriptionPlan();
        existing.setId(dto.getId());
        when(subscriptionPlanCrud.findById(dto.getId())).thenReturn(Optional.of(existing));
        when(subscriptionPlanCrud.save(any(SubscriptionPlan.class))).thenReturn(existing);

        ResponseSuccessfullyDto resp = service.updateSubscriptionPlan(dto);

        assertThat(resp.getCode()).isEqualTo(HttpStatus.OK);
        assertThat(resp.getMessage()).isEqualTo("Registro actualizado con Éxito");
        verify(subscriptionPlanCrud).save(any(SubscriptionPlan.class));
    }

    @Test
    void updateSubscriptionPlan_wrapsExceptionMessage_badRequest() {
        SubscriptionPlanDto dto = new SubscriptionPlanDto();
        dto.setId(8);
        dto.setName("Plan C");
        dto.setMonth_hours(12);
        dto.setDaily_hours(2);
        dto.setTotal_discount(1.0);
        dto.setAnnual_discount(2.0);

        SubscriptionPlan existing = new SubscriptionPlan();
        existing.setId(dto.getId());
        when(subscriptionPlanCrud.findById(dto.getId())).thenReturn(Optional.of(existing));

        RuntimeException root = new RuntimeException("validation error");
        RuntimeException wrapper = new RuntimeException("wrapper", root);
        when(subscriptionPlanCrud.save(any(SubscriptionPlan.class))).thenThrow(wrapper);

        BusinessException ex = assertThrows(BusinessException.class, () -> service.updateSubscriptionPlan(dto));
        assertThat(ex.getCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(ex.getMessage()).isEqualTo("validation error");
    }

    @Test
    void updateSubscriptionPlan_otherException_internalServerError() {
        SubscriptionPlanDto dto = new SubscriptionPlanDto();
        dto.setId(9);
        dto.setName("Plan D");
        dto.setMonth_hours(8);
        dto.setDaily_hours(1);
        dto.setTotal_discount(0.0);
        dto.setAnnual_discount(0.0);

        SubscriptionPlan existing = new SubscriptionPlan();
        existing.setId(dto.getId());
        when(subscriptionPlanCrud.findById(dto.getId())).thenReturn(Optional.of(existing));
        when(subscriptionPlanCrud.save(any(SubscriptionPlan.class))).thenThrow(new RuntimeException("boom"));

        BusinessException ex = assertThrows(BusinessException.class, () -> service.updateSubscriptionPlan(dto));
        assertThat(ex.getCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(ex.getMessage()).isEqualTo("boom");
    }

    @Test
    void deleteSubscriptionPlan_success() {
        Integer id = 10;
        SubscriptionPlan entity = new SubscriptionPlan();
        entity.setId(id);
        when(subscriptionPlanCrud.findById(id)).thenReturn(Optional.of(entity));

        ResponseSuccessfullyDto resp = service.deleteSubscriptionPlan(id);

        assertThat(resp.getCode()).isEqualTo(HttpStatus.OK);
        assertThat(resp.getMessage()).isEqualTo("Registro eliminado con Éxito");
        verify(subscriptionPlanCrud).delete(entity);
    }

    @Test
    void responseGetters() {
        Integer id = 11;
        SubscriptionPlan entity = new SubscriptionPlan();
        entity.setId(id);
        when(subscriptionPlanCrud.findById(id)).thenReturn(Optional.of(entity));

        ResponseSuccessfullyDto single = service.getSubscriptionPlan(id);
        assertThat(single.getCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(single.getMessage()).isEqualTo("Registro encontrado con Éxito");
        assertThat(single.getBody()).isEqualTo(entity);

        List<SubscriptionPlan> list = Arrays.asList(entity);
        when(subscriptionPlanCrud.findAll()).thenReturn(list);
        ResponseSuccessfullyDto all = service.getAllSubscriptionPlanListResponse();
        assertThat(all.getCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(all.getMessage()).isEqualTo("Registros encontrados con Éxito");
        assertThat(all.getBody()).isEqualTo(list);
    }
}


