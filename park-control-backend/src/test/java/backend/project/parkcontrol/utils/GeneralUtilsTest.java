package backend.project.parkcontrol.utils;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class GeneralUtilsTest {

    private final GeneralUtils utils = new GeneralUtils();

    @Test
    void hashPassword_ShouldGenerateDifferentHashEachTime_AndMatchSuccessfully() {
        // Arrange
        String password = "StrongPassword123!";

        // Act
        String hashed1 = utils.hashPassword(password);
        String hashed2 = utils.hashPassword(password);

        // Assert
        assertNotNull(hashed1);
        assertNotNull(hashed2);
        assertNotEquals(hashed1, hashed2); // bcrypt generates a new salt each time
        assertTrue(utils.validatePassword(password, hashed1));
        assertTrue(utils.validatePassword(password, hashed2));
        assertFalse(utils.validatePassword("WrongPass", hashed1));
    }

    @Test
    void validatePassword_ShouldReturnTrueWhenMatches() {
        // Arrange
        String password = "Test123!";
        String hashed = utils.hashPassword(password);

        // Act
        Boolean result = utils.validatePassword(password, hashed);

        // Assert
        assertTrue(result);
    }

    @Test
    void validatePassword_ShouldReturnFalseWhenNotMatching() {
        // Arrange
        String password = "CorrectPass";
        String wrongPassword = "IncorrectPass";
        String hashed = utils.hashPassword(password);

        // Act
        Boolean result = utils.validatePassword(wrongPassword, hashed);

        // Assert
        assertFalse(result);
    }

    @Test
    void generateVerificationCode_ShouldReturnStringOfFiveCharacters() {
        // Act
        String code = utils.generateVerificationCode();

        // Assert
        assertNotNull(code);
        assertThat(code.length()).isEqualTo(5);
        assertTrue(code.matches("^[A-Za-z0-9]{5}$"));
    }

    @Test
    void generateVerificationCode_ShouldGenerateDifferentCodes() {
        // Act
        String code1 = utils.generateVerificationCode();
        String code2 = utils.generateVerificationCode();

        // Assert
        assertNotEquals(code1, code2);
    }

    @Test
    void createExpirationDate_ShouldAddMinutesCorrectly() {
        // Arrange
        int minutesToAdd = 10;
        Date now = new Date();

        // Act
        Date expirationDate = utils.createExpirationDate(minutesToAdd);

        // Assert
        assertNotNull(expirationDate);
        long diffInMillis = expirationDate.getTime() - now.getTime();
        long diffInMinutes = diffInMillis / (60 * 1000);
        assertThat(diffInMinutes).isBetween(9L, 11L);
    }

    @Test
    void createExpirationDate_ShouldHandleZeroMinutes() {
        // Act
        Date expirationDate = utils.createExpirationDate(0);

        // Assert
        assertNotNull(expirationDate);
        Date now = new Date();
        long diff = expirationDate.getTime() - now.getTime();
        assertThat(Math.abs(diff)).isLessThan(2000L); // tolerance of 2 seconds
    }
}
