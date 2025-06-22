package br.com.train.springsecurityexample.config.security;

import br.com.train.springsecurityexample.security.jwt.JwtTokenFilter;
import br.com.train.springsecurityexample.security.jwt.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.HashMap;
import java.util.Map;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        JwtTokenFilter customFilter = new JwtTokenFilter(jwtTokenProvider);
        return http
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(authorizationMatchers ->
                        authorizationMatchers
                                .requestMatchers("/api/v1/auth/**","/api/v1/refresh/**","/api/swagger-ui", "/V3/api-docs/**").permitAll()
                                .requestMatchers("/api/users/**").hasRole("USER")
                                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                                .requestMatchers("/api/actuator/**").hasRole("ACTUATOR")
                                .requestMatchers("/api/health/**").hasRole("HEALTH")
                                .requestMatchers("/users").denyAll()
                                .anyRequest().authenticated()
                )
                .cors( cors -> {})
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        PasswordEncoder pbkdfeEncoder = new Pbkdf2PasswordEncoder("",8,185000, Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256);

        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put("pbkdf2", pbkdfeEncoder);

        DelegatingPasswordEncoder encoder = new DelegatingPasswordEncoder("pbkdf2", encoders);
        encoder.setDefaultPasswordEncoderForMatches(encoder);
        return encoder;
    }
}
