package backend.project.parkcontrol.service;

import backend.project.parkcontrol.dto.request.NewAffiliatedBusinessDto;
import backend.project.parkcontrol.dto.response.AffiliatedBusinessDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.exception.BusinessException;
import backend.project.parkcontrol.repository.crud.AffiliatedBusinessCrud;
import backend.project.parkcontrol.repository.crud.UserCrud;
import backend.project.parkcontrol.repository.entities.AffiliatedBusiness;
import backend.project.parkcontrol.repository.entities.UserEntity;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AffiliatedBusinessServiceTest {

    @Mock
    private AffiliatedBusinessCrud affiliatedBusinessCrud;

    @Mock
    private UserCrud userCrud;

    @Mock
    private ValidationService validationService;

    @InjectMocks
    private AffiliatedBusinessService service;

    private static final EasyRandom GENERATOR = new EasyRandom();

    @Test
    void getById_user() {
        Integer userId = 1;
        List<AffiliatedBusiness> expected = Arrays.asList(
                GENERATOR.nextObject(AffiliatedBusiness.class),
                GENERATOR.nextObject(AffiliatedBusiness.class)
        );

        when(affiliatedBusinessCrud.findById_user(userId)).thenReturn(expected);

        List<AffiliatedBusiness> result = service.getById_user(userId);

        assertThat(result).isEqualTo(expected);
        assertThat(result.size()).isEqualTo(2);
        verify(affiliatedBusinessCrud).findById_user(userId);
    }

    @Test
    void getAllAffiliatedBusinessList() {
        List<AffiliatedBusiness> expected = Arrays.asList(
                GENERATOR.nextObject(AffiliatedBusiness.class),
                GENERATOR.nextObject(AffiliatedBusiness.class)
        );

        when(affiliatedBusinessCrud.findAll()).thenReturn(expected);

        List<AffiliatedBusiness> result = service.getAllAffiliatedBusinessList();

        assertThat(result).isEqualTo(expected);
        assertThat(result.size()).isEqualTo(2);
        verify(affiliatedBusinessCrud).findAll();
    }

    @Test
    void getAffiliatedBusinessById() {
        Integer id = 5;
        AffiliatedBusiness entity = GENERATOR.nextObject(AffiliatedBusiness.class);
        entity.setId(id);

        when(affiliatedBusinessCrud.findById(id)).thenReturn(Optional.of(entity));

        AffiliatedBusiness result = service.getAffiliatedBusinessById(id);

        assertThat(result).isEqualTo(entity);
        verify(affiliatedBusinessCrud).findById(id);
    }

    @Test
    void deleteAffiliatedBusiness() {
        Integer id = 7;
        AffiliatedBusiness entity = GENERATOR.nextObject(AffiliatedBusiness.class);
        entity.setId(id);

        when(affiliatedBusinessCrud.findById(id)).thenReturn(Optional.of(entity));

        ResponseSuccessfullyDto resp = service.deleteAffiliatedBusiness(id);

        assertThat(resp.getCode()).isEqualTo(HttpStatus.OK);
        assertThat(resp.getMessage()).isEqualTo("Registro eliminado con Éxito");
        verify(affiliatedBusinessCrud).findById(id);
        verify(affiliatedBusinessCrud).delete(entity);
    }

    @Test
    void createAffiliatedBusiness_success() {
        NewAffiliatedBusinessDto dto = new NewAffiliatedBusinessDto();
        dto.setBusiness_name("Test Business");
        dto.setGranted_hours(10);
        dto.setId_user(1);

        UserEntity user = GENERATOR.nextObject(UserEntity.class);
        when(userCrud.findById(dto.getId_user())).thenReturn(Optional.of(user));
        when(affiliatedBusinessCrud.save(any(AffiliatedBusiness.class))).thenReturn(GENERATOR.nextObject(AffiliatedBusiness.class));

        ResponseSuccessfullyDto resp = service.createAffiliatedBusiness(dto);

        assertThat(resp.getCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(resp.getMessage()).isEqualTo("Registro creado con Éxito");
        verify(validationService).validatePositiveNumber(dto.getGranted_hours(), "Horas Otorgadas");
        verify(affiliatedBusinessCrud).save(any(AffiliatedBusiness.class));
    }

    @Test
    void createAffiliatedBusiness_userNotFound() {
        NewAffiliatedBusinessDto dto = new NewAffiliatedBusinessDto();
        dto.setBusiness_name("Test Business");
        dto.setGranted_hours(10);
        dto.setId_user(999);

        when(userCrud.findById(dto.getId_user())).thenReturn(Optional.empty());

        BusinessException ex = assertThrows(BusinessException.class, () ->
                service.createAffiliatedBusiness(dto));

        assertThat(ex.getCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(ex.getMessage()).isEqualTo("User not found");
    }

    @Test
    void updateAffiliatedBusiness_success() {
        AffiliatedBusinessDto dto = new AffiliatedBusinessDto();
        dto.setId(1);
        dto.setBusiness_name("Updated Business");
        dto.setGranted_hours(20);
        dto.setId_user(2);

        AffiliatedBusiness existing = GENERATOR.nextObject(AffiliatedBusiness.class);
        UserEntity user = GENERATOR.nextObject(UserEntity.class);

        when(affiliatedBusinessCrud.findById(dto.getId())).thenReturn(Optional.of(existing));
        when(userCrud.findById(dto.getId_user())).thenReturn(Optional.of(user));
        when(affiliatedBusinessCrud.save(any(AffiliatedBusiness.class))).thenReturn(existing);

        ResponseSuccessfullyDto resp = service.updateAffiliatedBusiness(dto);

        assertThat(resp.getCode()).isEqualTo(HttpStatus.OK);
        assertThat(resp.getMessage()).isEqualTo("Registro actualizado con Éxito");
        verify(affiliatedBusinessCrud).findById(dto.getId());
        verify(validationService).validatePositiveNumber(dto.getGranted_hours(), "Horas Otorgadas");
        verify(userCrud).findById(dto.getId_user());
        verify(affiliatedBusinessCrud).save(any(AffiliatedBusiness.class));
    }

    @Test
    void updateAffiliatedBusiness_userNotFound() {
        AffiliatedBusinessDto dto = new AffiliatedBusinessDto();
        dto.setId(1);
        dto.setBusiness_name("Updated Business");
        dto.setGranted_hours(20);
        dto.setId_user(999);

        AffiliatedBusiness existing = GENERATOR.nextObject(AffiliatedBusiness.class);

        when(affiliatedBusinessCrud.findById(dto.getId())).thenReturn(Optional.of(existing));
        when(userCrud.findById(dto.getId_user())).thenReturn(Optional.empty());

        BusinessException ex = assertThrows(BusinessException.class, () ->
                service.updateAffiliatedBusiness(dto));

        assertThat(ex.getCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(ex.getMessage()).isEqualTo("User not found");
    }

    @Test
    void getAffiliatedBusiness_responses() {
        Integer id = 33;
        AffiliatedBusiness entity = GENERATOR.nextObject(AffiliatedBusiness.class);
        entity.setId(id);

        when(affiliatedBusinessCrud.findById(id)).thenReturn(Optional.of(entity));

        ResponseSuccessfullyDto single = service.getAffiliatedBusiness(id);
        assertThat(single.getCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(single.getMessage()).isEqualTo("Registro encontrado con Éxito");
        assertThat(single.getBody()).isEqualTo(entity);

        List<AffiliatedBusiness> list = Arrays.asList(entity);
        when(affiliatedBusinessCrud.findAll()).thenReturn(list);
        ResponseSuccessfullyDto all = service.getAllAffiliatedBusinessListResponse();
        assertThat(all.getCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(all.getMessage()).isEqualTo("Registros encontrados con Éxito");
        assertThat(all.getBody()).isEqualTo(list);

        when(affiliatedBusinessCrud.findById_user(id)).thenReturn(list);
        ResponseSuccessfullyDto byUser = service.getById_userResponse(id);
        assertThat(byUser.getCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(byUser.getMessage()).isEqualTo("Registros encontrados con Éxito");
        assertThat(byUser.getBody()).isEqualTo(list);
    }
}