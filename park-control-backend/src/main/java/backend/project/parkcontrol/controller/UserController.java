package backend.project.parkcontrol.controller;

import backend.project.parkcontrol.controller.api.UserApi;
import backend.project.parkcontrol.dto.request.LoginDto;
import backend.project.parkcontrol.dto.request.NewUserDto;
import backend.project.parkcontrol.dto.request.UpdateAuthStatusDto;
import backend.project.parkcontrol.dto.request.ValidateCodeDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
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
