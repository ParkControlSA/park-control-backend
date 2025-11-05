package backend.project.parkcontrol.service;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Service
public class ValidationCodeRedisService {

    private final StringRedisTemplate redisTemplate;
    private static final String KEY_PREFIX = "2fa:";

    public ValidationCodeRedisService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * Guarda un código para un userId con TTL en minutos.
     */
    public void saveCode(Integer userId, String code, long ttlMinutes) {
        String key = KEY_PREFIX + userId;
        redisTemplate.opsForValue().set(key, code, Duration.ofMinutes(ttlMinutes));
    }

    /**
     * Recupera el código (o null si no existe o expiró).
     */
    public String getCode(Integer userId) {
        String key = KEY_PREFIX + userId;
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * Elimina el código (por ejemplo al validar correctamente).
     */
    public void deleteCode(Integer userId) {
        String key = KEY_PREFIX + userId;
        redisTemplate.delete(key);
    }

    /**
     * Verifica el código: devuelve true si coincide (y elimina el key).
     */
    public boolean validateAndDelete(Integer userId, String code) {
        String key = KEY_PREFIX + userId;
        String stored = redisTemplate.opsForValue().get(key);
        if(stored == null) return false;
        boolean ok = stored.equals(code);
        if(ok) redisTemplate.delete(key);
        return ok;
    }
}
