package backend.project.parkcontrol.controllers;

import backend.project.parkcontrol.controller.ContractPaymentController;
import backend.project.parkcontrol.dto.request.NewContractPaymentDto;
import backend.project.parkcontrol.dto.response.ContractPaymentDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.service.ContractPaymentService;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ContractPaymentControllerTest {

    @Mock
    private ContractPaymentService contractPaymentService;

    @InjectMocks
    private ContractPaymentController controller;

    private static final EasyRandom GENERATOR = new EasyRandom();

    @Test
    void createContractPayment_shouldReturnCreatedResponse() {
        NewContractPaymentDto dto = GENERATOR.nextObject(NewContractPaymentDto.class);
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.CREATED)
                .message("Pago de contrato creado con éxito")
                .body(null)
                .build();

        when(contractPaymentService.createContractPayment(dto)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.createContractPayment(dto);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(result.getBody()).isEqualTo(response);
        verify(contractPaymentService).createContractPayment(dto);
    }

    @Test
    void updateContractPayment_shouldReturnAcceptedResponse() {
        ContractPaymentDto dto = GENERATOR.nextObject(ContractPaymentDto.class);
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.ACCEPTED)
                .message("Pago de contrato actualizado con éxito")
                .body(null)
                .build();

        when(contractPaymentService.updateContractPayment(dto)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.updateContractPayment(dto);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        assertThat(result.getBody()).isEqualTo(response);
        verify(contractPaymentService).updateContractPayment(dto);
    }

    @Test
    void deleteContractPayment_shouldReturnAcceptedResponse() {
        Integer id = 7;
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.ACCEPTED)
                .message("Pago de contrato eliminado con éxito")
                .body(null)
                .build();

        when(contractPaymentService.deleteContractPayment(id)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.deleteContractPayment(id);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        assertThat(result.getBody()).isEqualTo(response);
        verify(contractPaymentService).deleteContractPayment(id);
    }

    @Test
    void getAllContractPayments_shouldReturnOkResponse() {
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("Lista de pagos de contratos obtenida correctamente")
                .body(null)
                .build();

        when(contractPaymentService.getAllContractPaymentListResponse()).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.getAllContractPayments();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(response);
        verify(contractPaymentService).getAllContractPaymentListResponse();
    }

    @Test
    void getContractPaymentById_shouldReturnOkResponse() {
        Integer id = 12;
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("Pago de contrato encontrado")
                .body(null)
                .build();

        when(contractPaymentService.getContractPayment(id)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.getContractPaymentById(id);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(response);
        verify(contractPaymentService).getContractPayment(id);
    }

    @Test
    void getContractPaymentsByContractId_shouldReturnOkResponse() {
        Integer id = 20;
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("Pagos asociados al contrato obtenidos correctamente")
                .body(null)
                .build();

        when(contractPaymentService.getById_contractResponse(id)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.getContractPaymentsByContractId(id);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(response);
        verify(contractPaymentService).getById_contractResponse(id);
    }
}
