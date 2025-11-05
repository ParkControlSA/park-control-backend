package backend.project.parkcontrol.service;

import backend.project.parkcontrol.exception.BusinessException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ValidationServiceTest {

    private final ValidationService validationService = new ValidationService();

    // ==============================
    // validatePositiveNumber
    // ==============================
    @Test
    void validatePositiveNumber_success_withPositiveValue() {
        assertDoesNotThrow(() -> validationService.validatePositiveNumber(5, "CampoX"));
    }

    @Test
    void validatePositiveNumber_throws_whenZero() {
        BusinessException ex = assertThrows(BusinessException.class, () ->
                validationService.validatePositiveNumber(0, "CampoX"));
        assertThat(ex.getCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(ex.getMessage()).isEqualTo("El valor del campo 'CampoX' debe ser mayor que 0.");
    }

    @Test
    void validatePositiveNumber_throws_whenNegative() {
        BusinessException ex = assertThrows(BusinessException.class, () ->
                validationService.validatePositiveNumber(-1, "CampoY"));
        assertThat(ex.getCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(ex.getMessage()).isEqualTo("El valor del campo 'CampoY' debe ser mayor que 0.");
    }

    @Test
    void validatePositiveNumber_throws_whenNull() {
        BusinessException ex = assertThrows(BusinessException.class, () ->
                validationService.validatePositiveNumber(null, "CampoZ"));
        assertThat(ex.getCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(ex.getMessage()).isEqualTo("El valor del campo 'CampoZ' debe ser mayor que 0.");
    }

    // ==============================
    // validateCurrentOrFutureDate
    // ==============================
    @Test
    void validateCurrentOrFutureDate_success_today() {
        assertDoesNotThrow(() -> validationService.validateCurrentOrFutureDate(LocalDate.now(), "FechaX"));
    }

    @Test
    void validateCurrentOrFutureDate_success_future() {
        assertDoesNotThrow(() -> validationService.validateCurrentOrFutureDate(LocalDate.now().plusDays(1), "FechaY"));
    }

    @Test
    void validateCurrentOrFutureDate_throws_whenPastOrNull() {
        BusinessException exPast = assertThrows(BusinessException.class, () ->
                validationService.validateCurrentOrFutureDate(LocalDate.now().minusDays(1), "FechaZ"));
        assertThat(exPast.getCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(exPast.getMessage()).isEqualTo("La fecha del campo 'FechaZ' debe ser hoy o una fecha futura.");

        BusinessException exNull = assertThrows(BusinessException.class, () ->
                validationService.validateCurrentOrFutureDate(null, "FechaN"));
        assertThat(exNull.getCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(exNull.getMessage()).isEqualTo("La fecha del campo 'FechaN' debe ser hoy o una fecha futura.");
    }

    // ==============================
    // validateCurrentOrFutureDateTime
    // ==============================
    @Test
    void validateCurrentOrFutureDateTime_success_nowOrFuture() {
        assertDoesNotThrow(() -> validationService.validateCurrentOrFutureDateTime(LocalDateTime.now().plusSeconds(1), "FechaHoraX"));
        assertDoesNotThrow(() -> validationService.validateCurrentOrFutureDateTime(LocalDateTime.now().plusMinutes(1), "FechaHoraY"));
    }

    @Test
    void validateCurrentOrFutureDateTime_throws_whenPastOrNull() {
        BusinessException exPast = assertThrows(BusinessException.class, () ->
                validationService.validateCurrentOrFutureDateTime(LocalDateTime.now().minusMinutes(1), "FechaHoraZ"));
        assertThat(exPast.getCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(exPast.getMessage()).isEqualTo("La fecha y hora del campo 'FechaHoraZ' deben ser actuales o futuras.");

        BusinessException exNull = assertThrows(BusinessException.class, () ->
                validationService.validateCurrentOrFutureDateTime(null, "FechaHoraN"));
        assertThat(exNull.getCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(exNull.getMessage()).isEqualTo("La fecha y hora del campo 'FechaHoraN' deben ser actuales o futuras.");
    }
}


