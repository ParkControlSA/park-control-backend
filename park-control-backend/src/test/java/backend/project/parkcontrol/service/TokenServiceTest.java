package backend.project.parkcontrol.service;

import backend.project.parkcontrol.exception.BusinessException;
import backend.project.parkcontrol.repository.crud.UserCrud;
import backend.project.parkcontrol.repository.entities.RoleEntity;
import backend.project.parkcontrol.repository.entities.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TokenServiceTest {

    @Mock
    private UserCrud userCrud;

    @InjectMocks
    private TokenService tokenService;

    @Test
    void validateAuthorizationHeader_adminUser_success() {
        Integer userId = 10;

        RoleEntity role = new RoleEntity();
        role.setId(1); // Admin role id

        UserEntity user = new UserEntity();
        user.setRol(role);

        when(userCrud.findById(userId)).thenReturn(Optional.of(user));

        tokenService.validateAuthorizationHeader(userId);

        verify(userCrud).findById(userId);
    }

    @Test
    void validateAuthorizationHeader_userNotFound_throwsNotFound() {
        Integer userId = 99;

        when(userCrud.findById(userId)).thenReturn(Optional.empty());

        BusinessException ex = assertThrows(BusinessException.class, () ->
                tokenService.validateAuthorizationHeader(userId));

        assertThat(ex.getCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(ex.getMessage()).isEqualTo("Credenciales incorrectas");
        verify(userCrud).findById(userId);
    }

    @Test
    void validateAuthorizationHeader_nonAdminUser_throwsUnauthorized() {
        Integer userId = 11;

        RoleEntity role = new RoleEntity();
        role.setId(2); // Non-admin role id

        UserEntity user = new UserEntity();
        user.setRol(role);

        when(userCrud.findById(userId)).thenReturn(Optional.of(user));

        BusinessException ex = assertThrows(BusinessException.class, () ->
                tokenService.validateAuthorizationHeader(userId));

        assertThat(ex.getCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(ex.getMessage()).isEqualTo("El usuario no cuenta con permisos para realizar la acción");
        verify(userCrud).findById(userId);
    }
}


