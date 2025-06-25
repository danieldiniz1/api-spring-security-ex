package br.com.train.springsecurityexample;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class SpringSecurityExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityExampleApplication.class, args);
    }

    @Bean
    public ApplicationRunner run(ApplicationArguments args) throws Exception {
         return args1 -> {
             PasswordEncoder encoder = passwordEncoder();
             String result = encoder.encode("admin1234");
             System.out.println("Encoded password: " + result);
         };
    }

    private PasswordEncoder passwordEncoder() {

            PasswordEncoder pbkdfeEncoder = new Pbkdf2PasswordEncoder("",8,18500, Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256);

            Map<String, PasswordEncoder> encoders = new HashMap<>();
            encoders.put("pbkdf2", pbkdfeEncoder);

            DelegatingPasswordEncoder encoder = new DelegatingPasswordEncoder("pbkdf2", encoders);
            encoder.setDefaultPasswordEncoderForMatches(encoder);

        return encoder;
    }

    private String generatePasswordHash(String password) {
        PasswordEncoder encoder = passwordEncoder();
        return encoder.encode(password);
    }

}
