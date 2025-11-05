package backend.project.parkcontrol.controllers;

import backend.project.parkcontrol.controller.ContractHistoryController;
import backend.project.parkcontrol.dto.response.ContractHistoryDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.service.ContractHistoryService;
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
class ContractHistoryControllerTest {

    @Mock
    private ContractHistoryService contractHistoryService;

    @InjectMocks
    private ContractHistoryController controller;

    private static final EasyRandom GENERATOR = new EasyRandom();

    @Test
    void updateContractHistory_shouldReturnAcceptedResponse() {
        ContractHistoryDto dto = GENERATOR.nextObject(ContractHistoryDto.class);
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.ACCEPTED)
                .message("Contract history updated successfully")
                .body(null)
                .build();
        when(contractHistoryService.updateContractHistory(dto)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.updateContractHistory(dto);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        assertThat(result.getBody()).isEqualTo(response);
        verify(contractHistoryService).updateContractHistory(dto);
    }

    @Test
    void deleteContractHistory_shouldReturnAcceptedResponse() {
        Integer id = 10;
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.ACCEPTED)
                .message("Contract history deleted successfully")
                .body(null)
                .build();
        when(contractHistoryService.deleteContractHistory(id)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.deleteContractHistory(id);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        assertThat(result.getBody()).isEqualTo(response);
        verify(contractHistoryService).deleteContractHistory(id);
    }

    @Test
    void getAllContractHistorys_shouldReturnOkResponse() {
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("List of contract histories")
                .body(null)
                .build();
        when(contractHistoryService.getAllContractHistoryListResponse()).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.getAllContractHistorys();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(response);
        verify(contractHistoryService).getAllContractHistoryListResponse();
    }

    @Test
    void getContractHistoryById_shouldReturnOkResponse() {
        Integer id = 5;
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("Contract history found")
                .body(null)
                .build();
        when(contractHistoryService.getContractHistory(id)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.getContractHistoryById(id);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(response);
        verify(contractHistoryService).getContractHistory(id);
    }

    @Test
    void getContractHistoryByContractId_shouldReturnOkResponse() {
        Integer contractId = 3;
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("Contract history by contract ID")
                .body(null)
                .build();
        when(contractHistoryService.getById_contractResponse(contractId)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.getContractHistoryByContractId(contractId);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(response);
        verify(contractHistoryService).getById_contractResponse(contractId);
    }
}
