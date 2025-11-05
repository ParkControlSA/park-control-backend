package backend.project.parkcontrol.controllers;

import backend.project.parkcontrol.controller.BranchController;
import backend.project.parkcontrol.dto.request.NewBranchDto;
import backend.project.parkcontrol.dto.response.BranchDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.service.BranchService;
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
class BranchControllerTest {

    @Mock
    private BranchService branchService;

    @InjectMocks
    private BranchController controller;

    private static final EasyRandom GENERATOR = new EasyRandom();

    // ==============================
    // TESTS CRUD
    // ==============================

    @Test
    void createBranch_shouldReturnCreatedResponse() {
        NewBranchDto dto = GENERATOR.nextObject(NewBranchDto.class);
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.CREATED)
                .message("Branch creado con éxito")
                .body(null)
                .build();
        when(branchService.createBranch(dto)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.createBranch(dto);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(result.getBody()).isEqualTo(response);
        verify(branchService).createBranch(dto);
    }

    @Test
    void updateBranch_shouldReturnAcceptedResponse() {
        BranchDto dto = GENERATOR.nextObject(BranchDto.class);
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.ACCEPTED)
                .message("Branch actualizado con éxito")
                .body(null)
                .build();
        when(branchService.updateBranch(dto)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.updateBranch(dto);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        assertThat(result.getBody()).isEqualTo(response);
        verify(branchService).updateBranch(dto);
    }

    @Test
    void deleteBranch_shouldReturnAcceptedResponse() {
        Integer id = 7;
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.ACCEPTED)
                .message("Branch eliminado con éxito")
                .body(null)
                .build();
        when(branchService.deleteBranch(id)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.deleteBranch(id);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        assertThat(result.getBody()).isEqualTo(response);
        verify(branchService).deleteBranch(id);
    }

    // ==============================
    // TESTS GETTERS
    // ==============================

    @Test
    void getAllbranch_shouldReturnOkResponse() {
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("Lista de branches obtenida con éxito")
                .body(null)
                .build();
        when(branchService.getAllBranchListResponse()).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.getAllbranch();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(response);
        verify(branchService).getAllBranchListResponse();
    }

    @Test
    void getBranchById_shouldReturnOkResponse() {
        Integer id = 3;
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("Branch encontrado con éxito")
                .body(null)
                .build();
        when(branchService.getBranch(id)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.getBranchById(id);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(response);
        verify(branchService).getBranch(id);
    }
}
