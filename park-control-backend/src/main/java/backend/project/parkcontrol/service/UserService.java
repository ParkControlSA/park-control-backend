package backend.project.parkcontrol.service;


import backend.project.parkcontrol.dto.request.*;
import backend.project.parkcontrol.dto.request.LoginDto;
import backend.project.parkcontrol.dto.request.NewUserDto;
import backend.project.parkcontrol.dto.request.UserUpdateDto;
import backend.project.parkcontrol.dto.request.ValidateCodeDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.dto.response.UserDto;
import backend.project.parkcontrol.dto.response.UserInfoDto;
import backend.project.parkcontrol.exception.BusinessException;
import backend.project.parkcontrol.repository.crud.UserCrud;
import backend.project.parkcontrol.repository.entities.RoleEntity;
import backend.project.parkcontrol.repository.entities.UserEntity;
import backend.project.parkcontrol.repository.entities.ValidationCode;
import backend.project.parkcontrol.utils.GeneralUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@EnableCaching
@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserCrud userCrud;

    private final RolService rolService;

    private final GeneralUtils utils;

    private final EmailService emailService;

    private final ValidationCodeService validationCodeService;

    private final ValidationCodeRedisService validationCodeRedisService;

    private final long TWO_FA_TTL_MINUTES = 5;

    public ResponseSuccessfullyDto createUser(NewUserDto newUserDto){
        UserEntity user = new UserEntity();
        user.setName(newUserDto.getNombre());
        user.setEmail(newUserDto.getEmail());
        user.setPhone(newUserDto.getTelefono());
        user.setUsername(newUserDto.getUsername());
        user.setPassword(utils.hashPassword(newUserDto.getPassword()));
        user.setPhone(newUserDto.getTelefono());
        user.setAuthentication(Boolean.TRUE);
        RoleEntity role = rolService.getRoleById(newUserDto.getRol());
        user.setRol(role);

        try{
            UserEntity savedUser = userCrud.save(user);
            log.info("User was saved successfully.");
            return ResponseSuccessfullyDto
                    .builder()
                    .code(HttpStatus.CREATED)
                    .body(Map.of("id", savedUser.getId()))
                    .message("El usuario fué creado correctamente")
                    .build();
        }catch (Exception exception){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"Error al intentar guardar al usuario.");
        }
    }

    public ResponseSuccessfullyDto updateUser(UserUpdateDto userDto) {
        UserEntity existingUser = getUserById(userDto.getId());

        // Actualizar campos
        existingUser.setName(userDto.getNombre());
        existingUser.setUsername(userDto.getUsername());
        existingUser.setEmail(userDto.getEmail());
        existingUser.setPhone(userDto.getTelefono());
        existingUser.setRol(rolService.getRoleById(userDto.getRol()));

        userCrud.save(existingUser);

        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("Usuario actualizado correctamente")
                .build();
    }

    // ==========================================
    // DELETE
    // ==========================================
    public ResponseSuccessfullyDto deleteUser(Integer id) {
        UserEntity user = getUserById(id);
        userCrud.delete(user);

        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("Usuario eliminado correctamente")
                .build();
    }

    // ==========================================
    // GETTERS
    // ==========================================
    public UserEntity getUserById(Integer id) {
        return userCrud.findById(id).get();
    }

    public ResponseSuccessfullyDto getUserByIdResponse(Integer id) {
        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.FOUND)
                .message("Usuario encontrado con éxito")
                .body(getUserById(id))
                .build();
    }

    public List<UserEntity> getAllUsersList() {
        return userCrud.findAll();
    }

    public ResponseSuccessfullyDto getAllUsersListResponse() {
        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.FOUND)
                .message("Usuarios encontrados con éxito")
                .body(getAllUsersList())
                .build();
    }

    public List<UserEntity> getUsersByRolId(Integer idRol) {
        return  userCrud.findById_role(idRol).get();
    }

    public ResponseSuccessfullyDto getUsersByRolIdResponse(Integer idRol) {
        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.FOUND)
                .message("Usuarios encontrados con éxito por rol")
                .body(getUsersByRolId(idRol))
                .build();
    }


    public ResponseSuccessfullyDto login(LoginDto loginDto){
        String message = "Inicio de sesión exitoso";
        Optional<UserEntity> optionalUser = userCrud.getUserByUsername(loginDto.getUsername());

        if(optionalUser.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND, "Credenciales incorrectas");
        }

        UserEntity user = optionalUser.get();

        if(!utils.validatePassword(loginDto.getPassword(), user.getPassword())){
            throw new BusinessException(HttpStatus.NOT_FOUND,"Crendenciales incorrectas");
        }

        if(user.getAuthentication()){
            log.info("Send code to user...");
            sendCodeToUser(user);
            message = "Se ha enviado un código a su correo electrónico, ingresarlo para confirmar el inicio de swsión";
        }

        UserInfoDto userInfoDto = UserInfoDto.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .role(user.getRol().getRol())
                .autentication(user.getAuthentication())
                .build();

        return ResponseSuccessfullyDto.builder().code(HttpStatus.OK).message(message).body(userInfoDto).build();
    }

    public void sendCodeToUser(UserEntity user){
        String code = utils.generateVerificationCode();
        emailService.sendEmail(user.getEmail(),"Código de Verificación","El codigo es: "+code);
        validationCodeRedisService.saveCode(user.getId(), code, TWO_FA_TTL_MINUTES);
    }

    public ResponseSuccessfullyDto validateCode(ValidateCodeDto validateCodeDto){
        // validateCodeDto debe traer userId y code
        Integer userId = validateCodeDto.getUserId();
        String code = validateCodeDto.getCode();

        boolean ok = validationCodeRedisService.validateAndDelete(userId, code);

        if(!ok){
            throw new BusinessException(HttpStatus.UNAUTHORIZED, "Código inválido o expirado");
        }

        Map<String, Object> body = new HashMap<>();
        body.put("userId", userId);
        body.put("message", "Código validado correctamente. Autenticación completada.");

        return ResponseSuccessfullyDto.builder().code(HttpStatus.OK).message("Código validado correctamente").body(body).build();
    }

    public ResponseSuccessfullyDto updateAuthenticationStatus(Integer userId, Boolean status){

        UserEntity user = getUserById(userId);
        user.setAuthentication(status);

        emailService.sendEmail(user.getEmail(),"Actualización de Estado de Autenticación en 2 Pasos","La Autenticación se encuentra: " + ((status) ? "Activado" : "Desactivado"));
        try{
            userCrud.save(user);
            return ResponseSuccessfullyDto.builder()
                    .code(HttpStatus.OK)
                    .message(status ? "Autenticacion en 2 pasos fué activado" : "Autenticacion en 2 pasos ha sido desactivado").build();
        }catch (Exception exception){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"Error al actualizar los permisos de autenticación en 2 pasos.");
        }
    }

    public ResponseSuccessfullyDto userForgotPassword(UserForgotPasswordDto userForgotPasswordDto){

        Optional<UserEntity> userOptional = userCrud.getUserByUsername(userForgotPasswordDto.getUsername());

        if(userOptional.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND,"El usuario no ha sido encontrado");
        }

        UserEntity user = userOptional.get();
        sendCodeToUser(user);

        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("Se ha enviado un codigo a tu correo para la recuperación de contraseña")
                .body(UserInfoDto.builder().userId(user.getId()).build())
                .build();
    }

    public ResponseSuccessfullyDto recoveryPassword(RecoveryPasswordDto recoveryPasswordDto, Integer userId){

        if(!recoveryPasswordDto.getNewPassword().equals(recoveryPasswordDto.getConfirmNewPassword())){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"La nueva contraseña y la confirmación no coinciden");
        }

        Optional<UserEntity> userOptional = userCrud.findById(userId);

        if(userOptional.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND,"El usuario no ha sido encontrado");
        }
        String passwordHashed = utils.hashPassword(recoveryPasswordDto.getNewPassword());

        UserEntity user = userOptional.get();
        user.setPassword(passwordHashed);
        userCrud.save(user);
        emailService.sendEmail(user.getEmail(),"Cambio de Contraseña","Se ha actualizado tu contraseña, si no has sido tú, por favor, responde a este correo.");
        return ResponseSuccessfullyDto.builder().code(HttpStatus.OK)
                .message("La contraseña ha sido actualizada, inicie sesión nuevamente")
                .build();
    }


}
