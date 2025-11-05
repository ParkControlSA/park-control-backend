package backend.project.parkcontrol.service;

import backend.project.parkcontrol.exception.BusinessException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private EmailService emailService;

    @Test
    void sendEmail_success() {
        String to = "test@example.com";
        String subject = "Subject";
        String body = "Body";

        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);

        emailService.sendEmail(to, subject, body);

        verify(mailSender).createMimeMessage();
        verify(mailSender).send(mimeMessage);
    }

    @Test
    void sendEmail_messagingException_throwsBusinessException() throws Exception {
        String to = "test@example.com";
        String subject = "Subject";
        String body = "Body";

        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);
        // Simular que al establecer el subject ocurre un MessagingException dentro del helper
        doThrow(new MessagingException("error")).when(mimeMessage).setSubject(anyString());

        BusinessException ex = assertThrows(BusinessException.class, () ->
                emailService.sendEmail(to, subject, body));

        assertThat(ex.getCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(ex.getMessage()).isEqualTo("Error sending email...");
        verify(mailSender).createMimeMessage();
        // No se llega a enviar debido a la excepción
        verify(mailSender, never()).send(mimeMessage);
    }
}


