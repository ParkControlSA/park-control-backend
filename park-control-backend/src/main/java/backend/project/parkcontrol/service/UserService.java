package backend.project.parkcontrol.service;


import backend.project.parkcontrol.dto.request.LoginDto;
import backend.project.parkcontrol.dto.request.NewUserDto;
import backend.project.parkcontrol.dto.request.ValidateCodeDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.dto.response.UserInfoDto;
import backend.project.parkcontrol.exception.BusinessException;
import backend.project.parkcontrol.repository.crud.UserCrud;
import backend.project.parkcontrol.repository.entities.RoleEntity;
import backend.project.parkcontrol.repository.entities.UserEntity;
import backend.project.parkcontrol.repository.entities.ValidationCode;
import backend.project.parkcontrol.utils.GeneralUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserCrud userCrud;

    private final RolService rolService;

    private final GeneralUtils utils;

    private final EmailService emailService;

    private final ValidationCodeService validationCodeService;

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
                    .message("El usuario fué creado correctamente")
                    .build();
        }catch (Exception exception){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"Error al intentar guardar al usuario.");
        }
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

        return ResponseSuccessfullyDto.builder().code(HttpStatus.ACCEPTED).message(message).body(userInfoDto).build();
    }

    public void sendCodeToUser(UserEntity user){
        String code = utils.generateVerificationCode();
        emailService.sendEmail(user.getEmail(),"Código de Verificación","El codigo es: "+code);

        ValidationCode validationCode = new ValidationCode();
        validationCode.setUser(user);
        validationCode.setCode(code);
        validationCode.setExpirationTime(utils.createExpirationDate(2));
        validationCodeService.createValidationCode(validationCode);
    }

    public ResponseSuccessfullyDto validateCode(ValidateCodeDto validateCodeDto){
        return validationCodeService.getValidationCodeByUser(validateCodeDto);
    }

    public UserEntity getUserById(Integer id){

        Optional<UserEntity> optionalUser = userCrud.findById(id);

        if(optionalUser.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND, "El usuario no ha sido encontrado");
        }

        return optionalUser.get();
    }


}
