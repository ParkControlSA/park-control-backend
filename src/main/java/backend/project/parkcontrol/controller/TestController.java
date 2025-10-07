package backend.project.parkcontrol.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {


    @GetMapping("test/endpoint")
    ResponseEntity<String> testEndpoint(@RequestHeader(value = "x-authorization") String token){
        String response = "El header es "+ token;
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
