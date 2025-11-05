
package backend.project.parkcontrol.controllers;

import backend.project.parkcontrol.controller.BranchTemporaryPermitController;
import backend.project.parkcontrol.dto.request.NewBranchTemporaryPermitDto;
import backend.project.parkcontrol.dto.response.BranchTemporaryPermitDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.service.BranchTemporaryPermitService;
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
class BranchTemporaryPermitControllerTest {

    @Mock
    private BranchTemporaryPermitService branchTemporaryPermitService;

    @InjectMocks
    private BranchTemporaryPermitController controller;

    private static final EasyRandom GENERATOR = new EasyRandom();

    @Test
    void createBranchTemporaryPermit_shouldReturnCreatedResponse() {
        NewBranchTemporaryPermitDto dto = GENERATOR.nextObject(NewBranchTemporaryPermitDto.class);
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.CREATED)
                .message("Permiso temporal creado con éxito")
                .body(null)
                .build();
        when(branchTemporaryPermitService.createBranchTemporaryPermit(dto)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.createBranchTemporaryPermit(dto);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(result.getBody()).isEqualTo(response);
        verify(branchTemporaryPermitService).createBranchTemporaryPermit(dto);
    }

    @Test
    void updateBranchTemporaryPermit_shouldReturnOkResponse() {
        BranchTemporaryPermitDto dto = GENERATOR.nextObject(BranchTemporaryPermitDto.class);
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("Permiso temporal actualizado con éxito")
                .body(null)
                .build();
        when(branchTemporaryPermitService.updateBranchTemporaryPermit(dto)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.updateBranchTemporaryPermit(dto);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(response);
        verify(branchTemporaryPermitService).updateBranchTemporaryPermit(dto);
    }

    @Test
    void deleteBranchTemporaryPermit_shouldReturnOkResponse() {
        Integer id = 15;
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("Permiso temporal eliminado con éxito")
                .body(null)
                .build();
        when(branchTemporaryPermitService.deleteBranchTemporaryPermit(id)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.deleteBranchTemporaryPermit(id);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(response);
        verify(branchTemporaryPermitService).deleteBranchTemporaryPermit(id);
    }

    @Test
    void getAllbranchTemporaryPermit_shouldReturnOkResponse() {
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("Lista de permisos temporales obtenida con éxito")
                .body(null)
                .build();
        when(branchTemporaryPermitService.getAllBranchTemporaryPermitListResponse()).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.getAllbranchTemporaryPermit();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(response);
        verify(branchTemporaryPermitService).getAllBranchTemporaryPermitListResponse();
    }

    @Test
    void getBranchTemporaryPermitById_shouldReturnOkResponse() {
        Integer id = 7;
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("Permiso temporal encontrado con éxito")
                .body(null)
                .build();
        when(branchTemporaryPermitService.getBranchTemporaryPermit(id)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.getBranchTemporaryPermitById(id);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(response);
        verify(branchTemporaryPermitService).getBranchTemporaryPermit(id);
    }

    @Test
    void getByIdBranch_shouldReturnOkResponse() {
        Integer id = 4;
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("Permisos temporales por sucursal")
                .body(null)
                .build();
        when(branchTemporaryPermitService.getById_branchResponse(id)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.getByIdBranch(id);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(response);
        verify(branchTemporaryPermitService).getById_branchResponse(id);
    }

    @Test
    void getByIdTemporaryPermit_shouldReturnOkResponse() {
        Integer id = 9;
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("Sucursales asociadas al permiso temporal")
                .body(null)
                .build();
        when(branchTemporaryPermitService.getById_temporary_permitResponse(id)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.getByIdTemporaryPermit(id);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(response);
        verify(branchTemporaryPermitService).getById_temporary_permitResponse(id);
    }
}
