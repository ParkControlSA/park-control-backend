package backend.project.parkcontrol.controller.api;

import backend.project.parkcontrol.dto.request.*;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.dto.response.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/user")
public interface UserApi {

    @PostMapping
    ResponseEntity<ResponseSuccessfullyDto> createUser(@RequestBody NewUserDto newUserDto);

    @PostMapping("/login")
    ResponseEntity<ResponseSuccessfullyDto> login(@RequestBody LoginDto loginDto);


    @PostMapping("/code")
    ResponseEntity<ResponseSuccessfullyDto> validateToken(@RequestBody ValidateCodeDto validateCodeDto);

    @PutMapping("/authentication/status")
    ResponseEntity<ResponseSuccessfullyDto> updateAuthStatus(@RequestBody UpdateAuthStatusDto updateAuthStatusDto, @RequestHeader(value = "user") Integer userId);

    @PutMapping
    ResponseEntity<ResponseSuccessfullyDto> updateUser(@RequestBody UserDto userDto);

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseSuccessfullyDto> deleteUser(@PathVariable Integer id);

    @GetMapping("/{id}")
    ResponseEntity<ResponseSuccessfullyDto> getUserById(@PathVariable Integer id);

    @GetMapping
    ResponseEntity<ResponseSuccessfullyDto> getAllUsers();

    @GetMapping("/role/{idRol}")
    ResponseEntity<ResponseSuccessfullyDto> getUsersByRol(@PathVariable Integer idRol);

    @PostMapping("/recovery_password")
    ResponseEntity<ResponseSuccessfullyDto> recoveryPassword(@RequestBody RecoveryPasswordDto recoveryPasswordDto,
                                                             @RequestHeader(value = "user") Integer userId);
    @PostMapping("/forgot_password")
    ResponseEntity<ResponseSuccessfullyDto> userForgotPassword(@RequestBody UserForgotPasswordDto userForgotPasswordDto);

}
