package backend.project.parkcontrol.service;

import backend.project.parkcontrol.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class ValidationService {


    public void validatePositiveNumber(Integer value, String fieldName) {
        if (value == null || value <= 0) {
            throw new BusinessException(HttpStatus.BAD_REQUEST,
                    "El valor del campo '" + fieldName + "' debe ser mayor que 0.");
        }
    }

    public void validateCurrentOrFutureDate(LocalDate date, String fieldName) {
        if (date == null || date.isBefore(LocalDate.now())) {
            throw new BusinessException(HttpStatus.BAD_REQUEST,
                    "La fecha del campo '" + fieldName + "' debe ser hoy o una fecha futura.");
        }
    }

    public void validateCurrentOrFutureDateTime(LocalDateTime dateTime, String fieldName) {
        if (dateTime == null || dateTime.isBefore(LocalDateTime.now())) {
            throw new BusinessException(HttpStatus.BAD_REQUEST,
                    "La fecha y hora del campo '" + fieldName + "' deben ser actuales o futuras.");
        }
    }
}
