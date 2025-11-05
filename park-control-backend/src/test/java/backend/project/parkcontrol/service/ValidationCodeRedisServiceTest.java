package backend.project.parkcontrol.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.time.Duration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ValidationCodeRedisServiceTest {

    @Mock
    private StringRedisTemplate redisTemplate;

    @Mock
    private ValueOperations<String, String> valueOps;

    @InjectMocks
    private ValidationCodeRedisService service;

    private static final String PREFIX = "2fa:";

    @Test
    void saveCode_storesWithTTL() {
        Integer userId = 5;
        String code = "123456";
        long ttl = 3L;

        when(redisTemplate.opsForValue()).thenReturn(valueOps);

        service.saveCode(userId, code, ttl);

        verify(redisTemplate).opsForValue();
        verify(valueOps).set(PREFIX + userId, code, Duration.ofMinutes(ttl));
    }

    @Test
    void getCode_returnsStoredValue() {
        Integer userId = 7;
        String expected = "654321";
        when(redisTemplate.opsForValue()).thenReturn(valueOps);
        when(valueOps.get(PREFIX + userId)).thenReturn(expected);

        String result = service.getCode(userId);

        assertThat(result).isEqualTo(expected);
        verify(redisTemplate).opsForValue();
        verify(valueOps).get(PREFIX + userId);
    }

    @Test
    void deleteCode_removesKey() {
        Integer userId = 9;

        service.deleteCode(userId);

        verify(redisTemplate).delete(PREFIX + userId);
    }

    @Test
    void validateAndDelete_returnsFalse_whenNoStoredCode() {
        Integer userId = 11;
        when(redisTemplate.opsForValue()).thenReturn(valueOps);
        when(valueOps.get(PREFIX + userId)).thenReturn(null);

        boolean ok = service.validateAndDelete(userId, "000000");

        assertThat(ok).isFalse();
        verify(redisTemplate).opsForValue();
        verify(valueOps).get(PREFIX + userId);
        verify(redisTemplate, never()).delete(anyString());
    }

    @Test
    void validateAndDelete_returnsTrue_andDeletes_whenCodesMatch() {
        Integer userId = 13;
        String code = "111111";
        when(redisTemplate.opsForValue()).thenReturn(valueOps);
        when(valueOps.get(PREFIX + userId)).thenReturn(code);

        boolean ok = service.validateAndDelete(userId, code);

        assertThat(ok).isTrue();
        verify(redisTemplate).opsForValue();
        verify(valueOps).get(PREFIX + userId);
        verify(redisTemplate).delete(PREFIX + userId);
    }

    @Test
    void validateAndDelete_returnsFalse_andDoesNotDelete_whenCodesDoNotMatch() {
        Integer userId = 15;
        when(redisTemplate.opsForValue()).thenReturn(valueOps);
        when(valueOps.get(PREFIX + userId)).thenReturn("222222");

        boolean ok = service.validateAndDelete(userId, "333333");

        assertThat(ok).isFalse();
        verify(redisTemplate).opsForValue();
        verify(valueOps).get(PREFIX + userId);
        verify(redisTemplate, never()).delete(anyString());
    }
}


