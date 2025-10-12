package backend.project.parkcontrol.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Date;

@Service
public class GeneralUtils {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom random = new SecureRandom();


    public String hashPassword(String password){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    public Boolean validatePassword(String passwordOriginal, String passwordHashed){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(passwordOriginal, passwordHashed);
    }

    public String generateVerificationCode(){
        StringBuilder sb = new StringBuilder(5);
        for (int i = 0; i < 5; i++) {
            int index = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }
        return sb.toString();
    }

    public Date createExpirationDate(Integer minutes) {
        Date actualDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(actualDate);
        calendar.add(Calendar.MINUTE, minutes);
        return calendar.getTime();
    }



}
