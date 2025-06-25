package br.com.train.springsecurityexample.util;

import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

import java.util.HashMap;
import java.util.Map;

public class EncriptUtil {


    public static String generatePasswordHash(String password) {
        return passwordEncoder().encode(password);
    }

    private static PasswordEncoder passwordEncoder() {

        PasswordEncoder pbkdfeEncoder = new Pbkdf2PasswordEncoder("",8,18500, Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256);

        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put("pbkdf2", pbkdfeEncoder);

        DelegatingPasswordEncoder encoder = new DelegatingPasswordEncoder("pbkdf2", encoders);
        encoder.setDefaultPasswordEncoderForMatches(encoder);

        return encoder;
    }
}
