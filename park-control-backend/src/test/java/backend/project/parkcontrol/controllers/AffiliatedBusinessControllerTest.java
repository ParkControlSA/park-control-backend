package backend.project.parkcontrol.controllers;

import backend.project.parkcontrol.controller.AffiliatedBusinessController;
import backend.project.parkcontrol.dto.request.NewAffiliatedBusinessDto;
import backend.project.parkcontrol.dto.response.AffiliatedBusinessDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.service.AffiliatedBusinessService;
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
class AffiliatedBusinessControllerTest {

    @Mock
    private AffiliatedBusinessService affiliatedBusinessService;

    @InjectMocks
    private AffiliatedBusinessController controller;

    private static final EasyRandom GENERATOR = new EasyRandom();

    @Test
    void createAffiliatedBusiness_shouldReturnCreatedResponse() {
        NewAffiliatedBusinessDto dto = GENERATOR.nextObject(NewAffiliatedBusinessDto.class);
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.CREATED)
                .message("Afiliado creado con éxito")
                .body(null)
                .build();
        when(affiliatedBusinessService.createAffiliatedBusiness(dto)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.createAffiliatedBusiness(dto);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(result.getBody()).isEqualTo(response);
        verify(affiliatedBusinessService).createAffiliatedBusiness(dto);
    }

    @Test
    void updateAffiliatedBusiness_shouldReturnAcceptedResponse() {
        AffiliatedBusinessDto dto = GENERATOR.nextObject(AffiliatedBusinessDto.class);
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.ACCEPTED)
                .message("Afiliado actualizado con éxito")
                .body(null)
                .build();
        when(affiliatedBusinessService.updateAffiliatedBusiness(dto)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.updateAffiliatedBusiness(dto);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        assertThat(result.getBody()).isEqualTo(response);
        verify(affiliatedBusinessService).updateAffiliatedBusiness(dto);
    }

    @Test
    void deleteAffiliatedBusiness_shouldReturnAcceptedResponse() {
        Integer id = 10;
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.ACCEPTED)
                .message("Afiliado eliminado con éxito")
                .body(null)
                .build();
        when(affiliatedBusinessService.deleteAffiliatedBusiness(id)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.deleteAffiliatedBusiness(id);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        assertThat(result.getBody()).isEqualTo(response);
        verify(affiliatedBusinessService).deleteAffiliatedBusiness(id);
    }

    @Test
    void getAllAffiliatedBusinesss_shouldReturnOkResponse() {
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("Lista de afiliados obtenida")
                .body(null)
                .build();
        when(affiliatedBusinessService.getAllAffiliatedBusinessListResponse()).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.getAllAffiliatedBusinesss();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(response);
        verify(affiliatedBusinessService).getAllAffiliatedBusinessListResponse();
    }

    @Test
    void getAffiliatedBusinessById_shouldReturnOkResponse() {
        Integer id = 5;
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("Afiliado encontrado")
                .body(null)
                .build();
        when(affiliatedBusinessService.getAffiliatedBusiness(id)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.getAffiliatedBusinessById(id);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(response);
        verify(affiliatedBusinessService).getAffiliatedBusiness(id);
    }

    @Test
    void getAffiliatedBusinessByIdUser_shouldReturnOkResponse() {
        Integer idUser = 7;
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("Afiliado por usuario encontrado")
                .body(null)
                .build();
        when(affiliatedBusinessService.getById_userResponse(idUser)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.getAffiliatedBusinessByIdUser(idUser);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(response);
        verify(affiliatedBusinessService).getById_userResponse(idUser);
    }
}
