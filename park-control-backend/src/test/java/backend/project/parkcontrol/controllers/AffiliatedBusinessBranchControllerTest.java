package backend.project.parkcontrol.controllers;

import backend.project.parkcontrol.controller.AffiliatedBusinessBranchController;
import backend.project.parkcontrol.dto.request.NewAffiliatedBusinessBranchDto;
import backend.project.parkcontrol.dto.response.AffiliatedBusinessBranchDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.service.AffiliatedBusinessBranchService;
import backend.project.parkcontrol.service.TokenService;
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
class AffiliatedBusinessBranchControllerTest {

    @Mock
    private AffiliatedBusinessBranchService affiliatedBusinessBranchService;

    @Mock
    private TokenService tokenService;

    @InjectMocks
    private AffiliatedBusinessBranchController controller;

    private static final EasyRandom GENERATOR = new EasyRandom();

    @Test
    void createAffiliatedBusinessBranch_shouldReturnCreatedResponse() {
        NewAffiliatedBusinessBranchDto dto = GENERATOR.nextObject(NewAffiliatedBusinessBranchDto.class);
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.CREATED)
                .message("Afiliación creada con éxito")
                .body(null)
                .build();

        when(affiliatedBusinessBranchService.createAffiliatedBusinessBranch(dto)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.createAffiliatedBusinessBranch(dto);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(result.getBody()).isEqualTo(response);
        verify(affiliatedBusinessBranchService).createAffiliatedBusinessBranch(dto);
    }

    @Test
    void updateAffiliatedBusinessBranch_shouldReturnOkResponse() {
        AffiliatedBusinessBranchDto dto = GENERATOR.nextObject(AffiliatedBusinessBranchDto.class);
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("Afiliación actualizada con éxito")
                .body(null)
                .build();

        when(affiliatedBusinessBranchService.updateAffiliatedBusinessBranch(dto)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.updateAffiliatedBusinessBranch(dto);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(response);
        verify(affiliatedBusinessBranchService).updateAffiliatedBusinessBranch(dto);
    }

    @Test
    void deleteAffiliatedBusinessBranch_shouldReturnOkResponse() {
        Integer id = 10;
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("Afiliación eliminada con éxito")
                .body(null)
                .build();

        when(affiliatedBusinessBranchService.deleteAffiliatedBusinessBranch(id)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.deleteAffiliatedBusinessBranch(id);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(response);
        verify(affiliatedBusinessBranchService).deleteAffiliatedBusinessBranch(id);
    }

    @Test
    void getAllAffiliatedBusinessBranchs_shouldReturnFoundResponse() {
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.FOUND)
                .message("Lista obtenida con éxito")
                .body(null)
                .build();

        when(affiliatedBusinessBranchService.getAllAffiliatedBusinessBranchListResponse()).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.getAllAffiliatedBusinessBranchs();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(result.getBody()).isEqualTo(response);
        verify(affiliatedBusinessBranchService).getAllAffiliatedBusinessBranchListResponse();
    }

    @Test
    void getAffiliatedBusinessBranchById_shouldReturnFoundResponse() {
        Integer id = 5;
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.FOUND)
                .message("Afiliación encontrada")
                .body(null)
                .build();

        when(affiliatedBusinessBranchService.getAffiliatedBusinessBranch(id)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.getAffiliatedBusinessBranchById(id);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(result.getBody()).isEqualTo(response);
        verify(affiliatedBusinessBranchService).getAffiliatedBusinessBranch(id);
    }

    @Test
    void getByIdBranch_shouldReturnFoundResponse() {
        Integer id = 7;
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.FOUND)
                .message("Afiliaciones por sucursal")
                .body(null)
                .build();

        when(affiliatedBusinessBranchService.getById_branchResponse(id)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.getByIdBranch(id);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(result.getBody()).isEqualTo(response);
        verify(affiliatedBusinessBranchService).getById_branchResponse(id);
    }

    @Test
    void getByIdAffiliatedBusiness_shouldReturnFoundResponse() {
        Integer id = 12;
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.FOUND)
                .message("Afiliaciones por empresa afiliada")
                .body(null)
                .build();

        when(affiliatedBusinessBranchService.getById_affiliated_businessResponse(id)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.getByIdAffiliatedBusiness(id);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(result.getBody()).isEqualTo(response);
        verify(affiliatedBusinessBranchService).getById_affiliated_businessResponse(id);
    }
}
