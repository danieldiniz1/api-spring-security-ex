package br.com.train.springsecurityexample.security.jwt;

import br.com.train.springsecurityexample.model.dto.TokenDTO;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;

@Component
public class JwtTokenProvider {

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expire-length}")
    private int expireLengthInMillisSeconds;

    private final UserDetailsService userDetailsService;
    private Algorithm algorithm;


    public JwtTokenProvider(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        algorithm = Algorithm.HMAC256(secretKey.getBytes());
    }

    public TokenDTO generateToken(String username, List<String> roles) {


        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime expiration = createdAt.plus(expireLengthInMillisSeconds, java.time.temporal.ChronoUnit.MILLIS);
        Boolean authenticated = null;
        String refreshToken= null;
        String token = getAcessToken(username, roles, createdAt, expiration);
        return new TokenDTO(username,token, refreshToken, authenticated, expiration, createdAt);
    }

    private String getAcessToken(String username, List<String> roles, LocalDateTime createdAt, LocalDateTime expiration) {
        String issueUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        return Base64.getEncoder().encodeToString(createdAt.toString().getBytes());
    }
}
