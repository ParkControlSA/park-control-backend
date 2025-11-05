package backend.project.parkcontrol.controllers;

import backend.project.parkcontrol.controller.SubscriptionPlanController;
import backend.project.parkcontrol.dto.request.NewSubscriptionPlanDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.dto.response.SubscriptionPlanDto;
import backend.project.parkcontrol.service.SubscriptionPlanService;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SubscriptionPlanControllerTest {

    @Mock
    private SubscriptionPlanService service;

    @InjectMocks
    private SubscriptionPlanController controller;

    private static final EasyRandom GENERATOR = new EasyRandom();

    // ==============================
    // CRUD
    // ==============================

    @Test
    void createSubscriptionPlan_shouldReturnCreatedResponse() {
        NewSubscriptionPlanDto dto = GENERATOR.nextObject(NewSubscriptionPlanDto.class);
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.CREATED)
                .message("Plan de suscripción creado con éxito")
                .body(null)
                .build();

        when(service.createSubscriptionPlan(dto)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.create(dto);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(result.getBody()).isEqualTo(response);
        verify(service).createSubscriptionPlan(dto);
    }

    @Test
    void updateSubscriptionPlan_shouldReturnAcceptedResponse() {
        SubscriptionPlanDto dto = GENERATOR.nextObject(SubscriptionPlanDto.class);
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.ACCEPTED)
                .message("Plan de suscripción actualizado con éxito")
                .body(null)
                .build();

        when(service.updateSubscriptionPlan(dto)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.update(dto);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        assertThat(result.getBody()).isEqualTo(response);
        verify(service).updateSubscriptionPlan(dto);
    }

    @Test
    void deleteSubscriptionPlan_shouldReturnAcceptedResponse() {
        Integer id = 10;
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.ACCEPTED)
                .message("Plan de suscripción eliminado con éxito")
                .body(null)
                .build();

        when(service.deleteSubscriptionPlan(id)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.delete(id);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        assertThat(result.getBody()).isEqualTo(response);
        verify(service).deleteSubscriptionPlan(id);
    }

    // ==============================
    // GETTERS
    // ==============================

    @Test
    void getAllSubscriptionPlans_shouldReturnOkResponse() {
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("Lista de planes obtenida correctamente")
                .body(null)
                .build();

        when(service.getAllSubscriptionPlanListResponse()).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.getAll();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(response);
        verify(service).getAllSubscriptionPlanListResponse();
    }

    @Test
    void getSubscriptionPlanById_shouldReturnOkResponse() {
        Integer id = 5;
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("Plan de suscripción encontrado")
                .body(null)
                .build();

        when(service.getSubscriptionPlan(id)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.getById(id);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(response);
        verify(service).getSubscriptionPlan(id);
    }
}
