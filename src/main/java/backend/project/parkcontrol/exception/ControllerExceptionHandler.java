package backend.project.parkcontrol.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Service
@Slf4j
public class ControllerExceptionHandler {


    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorDto> handleControllerException(BusinessException businessException){
        ErrorDto errorDto = ErrorDto.builder()
                                    .code(businessException.getCode()).message(businessException.getMessage())
                                    .build();

        return createResponseEntity(errorDto);
    }


    private ResponseEntity<ErrorDto> createResponseEntity(ErrorDto errorDto){
        return new ResponseEntity<>(errorDto, errorDto.getCode());
    }


}
