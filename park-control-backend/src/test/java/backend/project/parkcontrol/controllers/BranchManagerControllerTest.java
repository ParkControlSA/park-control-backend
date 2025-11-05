package backend.project.parkcontrol.controllers;

import backend.project.parkcontrol.controller.BranchManagerController;
import backend.project.parkcontrol.dto.request.NewBranchManagerDto;
import backend.project.parkcontrol.dto.response.BranchManagerDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.service.BranchManagerService;
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
class BranchManagerControllerTest {

    @Mock
    private BranchManagerService branchManagerService;

    @InjectMocks
    private BranchManagerController controller;

    private static final EasyRandom GENERATOR = new EasyRandom();

    @Test
    void createBranchManager_shouldReturnCreatedResponse() {
        NewBranchManagerDto dto = GENERATOR.nextObject(NewBranchManagerDto.class);
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.CREATED)
                .message("BranchManager creado con éxito")
                .body(null)
                .build();

        when(branchManagerService.createBranchManager(dto)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.createBranchManager(dto);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(result.getBody()).isEqualTo(response);
        verify(branchManagerService).createBranchManager(dto);
    }

    @Test
    void updateBranchManager_shouldReturnAcceptedResponse() {
        BranchManagerDto dto = GENERATOR.nextObject(BranchManagerDto.class);
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.ACCEPTED)
                .message("BranchManager actualizado con éxito")
                .body(null)
                .build();

        when(branchManagerService.updateBranchManager(dto)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.updateBranchManager(dto);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        assertThat(result.getBody()).isEqualTo(response);
        verify(branchManagerService).updateBranchManager(dto);
    }

    @Test
    void deleteBranchManager_shouldReturnAcceptedResponse() {
        Integer id = 5;
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.ACCEPTED)
                .message("BranchManager eliminado con éxito")
                .body(null)
                .build();

        when(branchManagerService.deleteBranchManager(id)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.deleteBranchManager(id);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        assertThat(result.getBody()).isEqualTo(response);
        verify(branchManagerService).deleteBranchManager(id);
    }

    @Test
    void getAllBranchManagers_shouldReturnOkResponse() {
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("Lista de BranchManagers obtenida con éxito")
                .body(null)
                .build();

        when(branchManagerService.getAllBranchManagerListResponse()).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.getAllBranchManagers();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(response);
        verify(branchManagerService).getAllBranchManagerListResponse();
    }

    @Test
    void getBranchManagerById_shouldReturnOkResponse() {
        Integer id = 1;
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("BranchManager encontrado")
                .body(null)
                .build();

        when(branchManagerService.getBranchManager(id)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.getBranchManagerById(id);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(response);
        verify(branchManagerService).getBranchManager(id);
    }

    @Test
    void getByIdBranch_shouldReturnOkResponse() {
        Integer id = 2;
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("BranchManager por sucursal obtenido")
                .body(null)
                .build();

        when(branchManagerService.getByIdBranchResponse(id)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.getByIdBranch(id);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(response);
        verify(branchManagerService).getByIdBranchResponse(id);
    }

    @Test
    void getByIdUser_shouldReturnOkResponse() {
        Integer id = 3;
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("BranchManager por usuario obtenido")
                .body(null)
                .build();

        when(branchManagerService.getByIdUserResponse(id)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.getByIdUser(id);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(response);
        verify(branchManagerService).getByIdUserResponse(id);
    }
}
