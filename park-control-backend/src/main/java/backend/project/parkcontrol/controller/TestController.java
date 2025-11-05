package backend.project.parkcontrol.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {


    @Autowired
    private StringRedisTemplate redisTemplate;

    @GetMapping("/test/redis")
    public String testRedis() {
        redisTemplate.opsForValue().set("test:key", "Hola Redis desde Spring!");
        return redisTemplate.opsForValue().get("test:key");
    }

    @GetMapping("test/endpoint")
    ResponseEntity<String> testEndpoint(@RequestHeader(value = "x-authorization") String token){
        String response = "El header es "+ token;
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
