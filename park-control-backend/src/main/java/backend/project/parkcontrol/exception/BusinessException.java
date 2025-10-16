package backend.project.parkcontrol.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
public class BusinessException extends RuntimeException{

    private HttpStatus code;

    private String message;

    public BusinessException(HttpStatus code, String message){
        this.code = code;
        this.message = message;
    }







}
