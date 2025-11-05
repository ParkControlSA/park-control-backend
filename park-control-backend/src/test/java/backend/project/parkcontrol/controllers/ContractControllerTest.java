package backend.project.parkcontrol.controllers;

import backend.project.parkcontrol.controller.ContractController;
import backend.project.parkcontrol.dto.request.NewContractDto;
import backend.project.parkcontrol.dto.response.ContractDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.service.ContractService;
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
class ContractControllerTest {

    @Mock
    private ContractService contractService;

    @InjectMocks
    private ContractController controller;

    private static final EasyRandom GENERATOR = new EasyRandom();

    // ==============================
    // CRUD
    // ==============================

    @Test
    void createContract_shouldReturnCreatedResponse() {
        NewContractDto dto = GENERATOR.nextObject(NewContractDto.class);
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.CREATED)
                .message("Contrato creado exitosamente")
                .body(null)
                .build();
        when(contractService.createContract(dto)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.createContract(dto);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(result.getBody()).isEqualTo(response);
        verify(contractService).createContract(dto);
    }

    @Test
    void updateContract_shouldReturnAcceptedResponse() {
        ContractDto dto = GENERATOR.nextObject(ContractDto.class);
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.ACCEPTED)
                .message("Contrato actualizado exitosamente")
                .body(null)
                .build();
        when(contractService.updateContract(dto)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.updateContract(dto);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        assertThat(result.getBody()).isEqualTo(response);
        verify(contractService).updateContract(dto);
    }

    @Test
    void deleteContract_shouldReturnAcceptedResponse() {
        Integer id = 10;
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.ACCEPTED)
                .message("Contrato eliminado exitosamente")
                .body(null)
                .build();
        when(contractService.deleteContract(id)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.deleteContract(id);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        assertThat(result.getBody()).isEqualTo(response);
        verify(contractService).deleteContract(id);
    }

    // ==============================
    // GETTERS
    // ==============================

    @Test
    void getAllContracts_shouldReturnOkResponse() {
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("Lista de contratos obtenida")
                .body(null)
                .build();
        when(contractService.getAllContractListResponse()).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.getAllContracts();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(response);
        verify(contractService).getAllContractListResponse();
    }

    @Test
    void getContractById_shouldReturnOkResponse() {
        Integer id = 1;
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("Contrato encontrado")
                .body(null)
                .build();
        when(contractService.getContract(id)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.getContractById(id);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(response);
        verify(contractService).getContract(id);
    }

    @Test
    void getContractsByUserId_shouldReturnOkResponse() {
        Integer userId = 5;
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("Contratos por usuario obtenidos")
                .body(null)
                .build();
        when(contractService.getById_userResponse(userId)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.getContractsByUserId(userId);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(response);
        verify(contractService).getById_userResponse(userId);
    }

    @Test
    void getContractsByPlate_shouldReturnOkResponse() {
        String plate = "P123ABC";
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("Contratos por placa obtenidos")
                .body(null)
                .build();
        when(contractService.getByLicense_plateResponse(plate)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.getContractsByPlate(plate);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(response);
        verify(contractService).getByLicense_plateResponse(plate);
    }
}
