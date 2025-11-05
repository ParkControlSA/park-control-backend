package backend.project.parkcontrol.service;

import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.dto.response.RoleInfoDto;
import backend.project.parkcontrol.exception.BusinessException;
import backend.project.parkcontrol.repository.crud.RolCrud;
import backend.project.parkcontrol.repository.entities.RoleEntity;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RolServiceTest {

    @Mock
    private RolCrud rolCrud;

    @InjectMocks
    private RolService rolService;

    private static final EasyRandom GENERATOR = new EasyRandom();

    @Test
    void getRoleById_success() {
        Integer id = 5;
        RoleEntity role = new RoleEntity();
        role.setId(id);
        role.setRol("ADMIN");

        when(rolCrud.findById(id)).thenReturn(Optional.of(role));

        RoleEntity result = rolService.getRoleById(id);

        assertThat(result).isEqualTo(role);
        verify(rolCrud).findById(id);
    }

    @Test
    void getRoleById_notFound() {
        Integer id = 99;

        when(rolCrud.findById(id)).thenReturn(Optional.empty());

        BusinessException ex = assertThrows(BusinessException.class, () ->
                rolService.getRoleById(id));

        assertThat(ex.getCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(ex.getMessage()).isEqualTo("Role not exists");
        verify(rolCrud).findById(id);
    }

    @Test
    void getAllRoles_success() {
        RoleEntity r1 = new RoleEntity();
        r1.setId(1);
        r1.setRol("ADMIN");
        RoleEntity r2 = new RoleEntity();
        r2.setId(2);
        r2.setRol("USER");
        List<RoleEntity> roles = Arrays.asList(r1, r2);

        when(rolCrud.findAll()).thenReturn(roles);

        ResponseSuccessfullyDto resp = rolService.getAllRoles();

        assertThat(resp.getCode()).isEqualTo(HttpStatus.OK);
        @SuppressWarnings("unchecked")
        List<RoleInfoDto> body = (List<RoleInfoDto>) resp.getBody();
        assertThat(body.size()).isEqualTo(2);

        RoleInfoDto info1 = body.get(0);
        RoleInfoDto info2 = body.get(1);
        assertThat(info1.getRoleId()).isEqualTo(1);
        assertThat(info1.getRoleName()).isEqualTo("ADMIN");
        assertThat(info2.getRoleId()).isEqualTo(2);
        assertThat(info2.getRoleName()).isEqualTo("USER");

        verify(rolCrud).findAll();
    }
}


