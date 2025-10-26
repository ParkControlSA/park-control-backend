package backend.project.parkcontrol.controller;

import backend.project.parkcontrol.controller.api.UserApi;
import backend.project.parkcontrol.dto.request.*;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.dto.response.UserDto;
import backend.project.parkcontrol.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RequiredArgsConstructor
@RestController
public class UserController implements UserApi {

    private final UserService userService;

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> createUser(NewUserDto newUserDto) {
        log.info("POST /user  -- create user");
        ResponseSuccessfullyDto responseSuccessfullyDto = userService.createUser(newUserDto);
        return new ResponseEntity<>(responseSuccessfullyDto, responseSuccessfullyDto.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> updateUser(UserUpdateDto userDto) {
        log.info("PUT /user -- update user");
        ResponseSuccessfullyDto response = userService.updateUser(userDto);
        return new ResponseEntity<>(response, response.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> deleteUser(Integer id) {
        log.info("DELETE /user/{}", id);
        ResponseSuccessfullyDto response = userService.deleteUser(id);
        return new ResponseEntity<>(response, response.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getUserById(Integer id) {
        log.info("GET /user/{}", id);
        ResponseSuccessfullyDto response = userService.getUserByIdResponse(id);
        return new ResponseEntity<>(response, response.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getAllUsers() {
        log.info("GET /user -- get all users");
        ResponseSuccessfullyDto response = userService.getAllUsersListResponse();
        return new ResponseEntity<>(response, response.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getUsersByRol(Integer idRol) {
        log.info("GET /user/role/{}", idRol);
        ResponseSuccessfullyDto response = userService.getUsersByRolIdResponse(idRol);
        return new ResponseEntity<>(response, response.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> recoveryPassword(RecoveryPasswordDto recoveryPasswordDto, Integer userId) {
        log.info("POST user/recovery_password");
        ResponseSuccessfullyDto responseSuccessfullyDto = userService.recoveryPassword(recoveryPasswordDto,userId);
        return new ResponseEntity<>(responseSuccessfullyDto, responseSuccessfullyDto.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> userForgotPassword(UserForgotPasswordDto userForgotPasswordDto) {
        log.info("POST user/forgot_password");
        ResponseSuccessfullyDto responseSuccessfullyDto = userService.userForgotPassword(userForgotPasswordDto);
        return new ResponseEntity<>(responseSuccessfullyDto,responseSuccessfullyDto.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> login(LoginDto loginDto) {
        log.info("POST user/login");
        ResponseSuccessfullyDto responseSuccessfullyDto = userService.login(loginDto);
        return new ResponseEntity<>(responseSuccessfullyDto, responseSuccessfullyDto.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> validateToken(ValidateCodeDto validateCodeDto) {
        log.info("GET user/code");
        ResponseSuccessfullyDto responseSuccessfullyDto = userService.validateCode(validateCodeDto);
        return new ResponseEntity<>(responseSuccessfullyDto, responseSuccessfullyDto.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> updateAuthStatus(UpdateAuthStatusDto updateAuthStatusDto, Integer userId) {
        log.info("PUT user/authentication/status");
        ResponseSuccessfullyDto responseSuccessfullyDto = userService.updateAuthenticationStatus(userId,updateAuthStatusDto.getStatus());
        return new ResponseEntity<ResponseSuccessfullyDto>(responseSuccessfullyDto, responseSuccessfullyDto.getCode());
    }
}
