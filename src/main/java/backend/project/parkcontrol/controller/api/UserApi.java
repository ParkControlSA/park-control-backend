package backend.project.parkcontrol.controller.api;

import backend.project.parkcontrol.dto.request.LoginDto;
import backend.project.parkcontrol.dto.request.NewUserDto;
import backend.project.parkcontrol.dto.request.ValidateCodeDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/user")
public interface UserApi {

    @PostMapping
    ResponseEntity<ResponseSuccessfullyDto> createUser(@RequestBody NewUserDto newUserDto);

    @PostMapping("/login")
    ResponseEntity<ResponseSuccessfullyDto> login(@RequestBody LoginDto loginDto);


    @PostMapping("/code")
    ResponseEntity<ResponseSuccessfullyDto> validateToken(@RequestBody ValidateCodeDto validateCodeDto);


}
