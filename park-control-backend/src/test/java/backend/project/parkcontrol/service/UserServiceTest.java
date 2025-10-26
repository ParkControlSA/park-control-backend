package backend.project.parkcontrol.service;

import backend.project.parkcontrol.dto.request.*;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.dto.response.UserDto;
import backend.project.parkcontrol.dto.response.UserInfoDto;
import backend.project.parkcontrol.exception.BusinessException;
import backend.project.parkcontrol.repository.crud.UserCrud;
import backend.project.parkcontrol.repository.entities.RoleEntity;
import backend.project.parkcontrol.repository.entities.UserEntity;
import backend.project.parkcontrol.utils.GeneralUtils;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyInt;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private  UserCrud userCrud;

    @Mock
    private  RolService rolService;

    @Mock
    private  GeneralUtils utils;

    @Mock
    private  EmailService emailService;

    @Mock
    private  ValidationCodeService validationCodeService;

    @InjectMocks
    private UserService userService;

    private static final EasyRandom GENERATOR = new EasyRandom();
    private static final ResponseSuccessfullyDto RESPONSE_SUCCESS_EXPECTED = GENERATOR.nextObject(ResponseSuccessfullyDto.class);

    @Test
    void createUser() {
        // Arrange
        RoleEntity role = GENERATOR.nextObject(RoleEntity.class);
        NewUserDto newUserDto = GENERATOR.nextObject(NewUserDto.class);
        String hashedPassword = "hashedPassword123";
        
        // Mock the dependencies
        when(rolService.getRoleById(newUserDto.getRol())).thenReturn(role);
        when(utils.hashPassword(newUserDto.getPassword())).thenReturn(hashedPassword);
        when(userCrud.save(any(UserEntity.class))).thenReturn(new UserEntity());

        // Act
        ResponseSuccessfullyDto result = userService.createUser(newUserDto);

        // Assert
        assertThat(result.getCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(result.getMessage()).isEqualTo("El usuario fué creado correctamente");
    }

    @Test
    void updateUser() {
        // Arrange
        UserUpdateDto userDto = GENERATOR.nextObject(UserUpdateDto.class);
        UserEntity existingUser = GENERATOR.nextObject(UserEntity.class);
        RoleEntity role = GENERATOR.nextObject(RoleEntity.class);
        
        when(userCrud.findById(userDto.getId())).thenReturn(Optional.of(existingUser));
        when(rolService.getRoleById(userDto.getRol())).thenReturn(role);
        when(userCrud.save(any(UserEntity.class))).thenReturn(existingUser);

        // Act
        ResponseSuccessfullyDto result = userService.updateUser(userDto);

        // Assert
        assertThat(result.getCode()).isEqualTo(HttpStatus.ACCEPTED);
        assertThat(result.getMessage()).isEqualTo("Usuario actualizado correctamente");
        verify(userCrud).save(any(UserEntity.class));
    }

    @Test
    void deleteUser() {
        // Arrange
        Integer userId = 1;
        UserEntity user = GENERATOR.nextObject(UserEntity.class);
        
        when(userCrud.findById(userId)).thenReturn(Optional.of(user));

        // Act
        ResponseSuccessfullyDto result = userService.deleteUser(userId);

        // Assert
        assertThat(result.getCode()).isEqualTo(HttpStatus.ACCEPTED);
        assertThat(result.getMessage()).isEqualTo("Usuario eliminado correctamente");
        verify(userCrud).delete(user);
    }

    @Test
    void getUserById() {
        // Arrange
        Integer userId = 1;
        UserEntity user = GENERATOR.nextObject(UserEntity.class);
        
        when(userCrud.findById(userId)).thenReturn(Optional.of(user));

        // Act
        UserEntity result = userService.getUserById(userId);

        // Assert
        assertThat(result).isEqualTo(user);
        verify(userCrud).findById(userId);
    }

    @Test
    void getUserByIdResponse() {
        // Arrange
        Integer userId = 1;
        UserEntity user = GENERATOR.nextObject(UserEntity.class);
        
        when(userCrud.findById(userId)).thenReturn(Optional.of(user));

        // Act
        ResponseSuccessfullyDto result = userService.getUserByIdResponse(userId);

        // Assert
        assertThat(result.getCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(result.getMessage()).isEqualTo("Usuario encontrado con éxito");
        assertThat(result.getBody()).isEqualTo(user);
    }

    @Test
    void getAllUsersList() {
        // Arrange
        List<UserEntity> users = Arrays.asList(
            GENERATOR.nextObject(UserEntity.class),
            GENERATOR.nextObject(UserEntity.class)
        );
        
        when(userCrud.findAll()).thenReturn(users);

        // Act
        List<UserEntity> result = userService.getAllUsersList();

        // Assert
        assertThat(result).isEqualTo(users);
        assertThat(result.size()).isEqualTo(2);
        verify(userCrud).findAll();
    }

    @Test
    void getAllUsersListResponse() {
        // Arrange
        List<UserEntity> users = Arrays.asList(
            GENERATOR.nextObject(UserEntity.class),
            GENERATOR.nextObject(UserEntity.class)
        );
        
        when(userCrud.findAll()).thenReturn(users);

        // Act
        ResponseSuccessfullyDto result = userService.getAllUsersListResponse();

        // Assert
        assertThat(result.getCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(result.getMessage()).isEqualTo("Usuarios encontrados con éxito");
        assertThat(result.getBody()).isEqualTo(users);
    }

    @Test
    void getUsersByRolId() {
        // Arrange
        Integer rolId = 1;
        List<UserEntity> users = Arrays.asList(
            GENERATOR.nextObject(UserEntity.class),
            GENERATOR.nextObject(UserEntity.class)
        );
        
        when(userCrud.findById_role(rolId)).thenReturn(users);

        // Act
        List<UserEntity> result = userService.getUsersByRolId(rolId);

        // Assert
        assertThat(result).isEqualTo(users);
        assertThat(result.size()).isEqualTo(2);
        verify(userCrud).findById_role(rolId);
    }

    @Test
    void getUsersByRolIdResponse() {
        // Arrange
        Integer rolId = 1;
        List<UserEntity> users = Arrays.asList(
            GENERATOR.nextObject(UserEntity.class),
            GENERATOR.nextObject(UserEntity.class)
        );
        
        when(userCrud.findById_role(rolId)).thenReturn(users);

        // Act
        ResponseSuccessfullyDto result = userService.getUsersByRolIdResponse(rolId);

        // Assert
        assertThat(result.getCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(result.getMessage()).isEqualTo("Usuarios encontrados con éxito por rol");
        assertThat(result.getBody()).isEqualTo(users);
    }

    @Test
    void login_Successful_WithoutTwoFactorAuth() {
        // Arrange
        LoginDto loginDto = GENERATOR.nextObject(LoginDto.class);
        UserEntity user = GENERATOR.nextObject(UserEntity.class);
        RoleEntity role = GENERATOR.nextObject(RoleEntity.class);
        user.setRol(role);
        user.setAuthentication(false);
        
        when(userCrud.getUserByUsername(loginDto.getUsername())).thenReturn(Optional.of(user));
        when(utils.validatePassword(loginDto.getPassword(), user.getPassword())).thenReturn(true);

        // Act
        ResponseSuccessfullyDto result = userService.login(loginDto);

        // Assert
        assertThat(result.getCode()).isEqualTo(HttpStatus.ACCEPTED);
        assertThat(result.getMessage()).isEqualTo("Inicio de sesión exitoso");
        assertThat(result.getBody()).isInstanceOf(UserInfoDto.class);
        
        UserInfoDto userInfo = (UserInfoDto) result.getBody();
        assertThat(userInfo.getUserId()).isEqualTo(user.getId());
        assertThat(userInfo.getUsername()).isEqualTo(user.getUsername());
        assertThat(userInfo.getRole()).isEqualTo(user.getRol().getRol());
        assertThat(userInfo.getAutentication()).isEqualTo(user.getAuthentication());
    }

    @Test
    void login_Successful_WithTwoFactorAuth() {
        // Arrange
        LoginDto loginDto = GENERATOR.nextObject(LoginDto.class);
        UserEntity user = GENERATOR.nextObject(UserEntity.class);
        RoleEntity role = GENERATOR.nextObject(RoleEntity.class);
        user.setRol(role);
        user.setAuthentication(true);
        
        when(userCrud.getUserByUsername(loginDto.getUsername())).thenReturn(Optional.of(user));
        when(utils.validatePassword(loginDto.getPassword(), user.getPassword())).thenReturn(true);

        // Act
        ResponseSuccessfullyDto result = userService.login(loginDto);

        // Assert
        assertThat(result.getCode()).isEqualTo(HttpStatus.ACCEPTED);
        assertThat(result.getMessage()).isEqualTo("Se ha enviado un código a su correo electrónico, ingresarlo para confirmar el inicio de swsión");
        assertThat(result.getBody()).isInstanceOf(UserInfoDto.class);
        
        // Verify that sendCodeToUser was called
        verify(emailService).sendEmail(anyString(), anyString(), anyString());
        verify(validationCodeService).createValidationCode(any());
    }

    @Test
    void login_UserNotFound_ThrowsException() {
        // Arrange
        LoginDto loginDto = GENERATOR.nextObject(LoginDto.class);
        
        when(userCrud.getUserByUsername(loginDto.getUsername())).thenReturn(Optional.empty());

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            userService.login(loginDto);
        });
        
        assertThat(exception.getCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(exception.getMessage()).isEqualTo("Credenciales incorrectas");
    }

    @Test
    void login_InvalidPassword_ThrowsException() {
        // Arrange
        LoginDto loginDto = GENERATOR.nextObject(LoginDto.class);
        UserEntity user = GENERATOR.nextObject(UserEntity.class);
        
        when(userCrud.getUserByUsername(loginDto.getUsername())).thenReturn(Optional.of(user));
        when(utils.validatePassword(loginDto.getPassword(), user.getPassword())).thenReturn(false);

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            userService.login(loginDto);
        });
        
        assertThat(exception.getCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(exception.getMessage()).isEqualTo("Crendenciales incorrectas");
    }

    @Test
    void sendCodeToUser() {
        // Arrange
        UserEntity user = GENERATOR.nextObject(UserEntity.class);
        String verificationCode = "123456";
        Date expirationDate = new Date();
        
        when(utils.generateVerificationCode()).thenReturn(verificationCode);
        when(utils.createExpirationDate(2)).thenReturn(expirationDate);

        // Act
        userService.sendCodeToUser(user);

        // Assert
        verify(utils).generateVerificationCode();
        verify(emailService).sendEmail(user.getEmail(), "Código de Verificación", "El codigo es: " + verificationCode);
        verify(validationCodeService).createValidationCode(any());
    }

    @Test
    void validateCode() {
        // Arrange
        ValidateCodeDto validateCodeDto = GENERATOR.nextObject(ValidateCodeDto.class);
        ResponseSuccessfullyDto expectedResponse = GENERATOR.nextObject(ResponseSuccessfullyDto.class);
        
        when(validationCodeService.getValidationCodeByUser(validateCodeDto)).thenReturn(expectedResponse);

        // Act
        ResponseSuccessfullyDto result = userService.validateCode(validateCodeDto);

        // Assert
        assertThat(result).isEqualTo(expectedResponse);
        verify(validationCodeService).getValidationCodeByUser(validateCodeDto);
    }

    @Test
    void updateAuthenticationStatus_EnableTwoFactorAuth_Success() {
        // Arrange
        Integer userId = 1;
        Boolean status = true;
        UserEntity user = GENERATOR.nextObject(UserEntity.class);
        
        when(userCrud.findById(userId)).thenReturn(Optional.of(user));
        when(userCrud.save(any(UserEntity.class))).thenReturn(user);

        // Act
        ResponseSuccessfullyDto result = userService.updateAuthenticationStatus(userId, status);

        // Assert
        assertThat(result.getCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getMessage()).isEqualTo("Autenticacion en 2 pasos fué activado");
        verify(userCrud).save(user);
        
        // Verify that the user's authentication status was updated
        assertThat(user.getAuthentication()).isEqualTo(status);
    }

    @Test
    void updateAuthenticationStatus_DisableTwoFactorAuth_Success() {
        // Arrange
        Integer userId = 1;
        Boolean status = false;
        UserEntity user = GENERATOR.nextObject(UserEntity.class);
        user.setAuthentication(true); // Initially enabled
        
        when(userCrud.findById(userId)).thenReturn(Optional.of(user));
        when(userCrud.save(any(UserEntity.class))).thenReturn(user);

        // Act
        ResponseSuccessfullyDto result = userService.updateAuthenticationStatus(userId, status);

        // Assert
        assertThat(result.getCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getMessage()).isEqualTo("Autenticacion en 2 pasos ha sido desactivado");
        verify(userCrud).save(user);
        
        // Verify that the user's authentication status was updated
        assertThat(user.getAuthentication()).isEqualTo(status);
    }

    @Test
    void updateAuthenticationStatus_SaveFails_ThrowsException() {
        // Arrange
        Integer userId = 1;
        Boolean status = true;
        UserEntity user = GENERATOR.nextObject(UserEntity.class);
        
        when(userCrud.findById(userId)).thenReturn(Optional.of(user));
        when(userCrud.save(any(UserEntity.class))).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            userService.updateAuthenticationStatus(userId, status);
        });
        
        assertThat(exception.getCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(exception.getMessage()).isEqualTo("Error al actualizar los permisos de autenticación en 2 pasos.");
    }
}