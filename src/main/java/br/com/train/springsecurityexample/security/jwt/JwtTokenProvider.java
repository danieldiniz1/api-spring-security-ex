package br.com.train.springsecurityexample.security.jwt;

import br.com.train.springsecurityexample.exception.InvalidJWTAuthenticationException;
import br.com.train.springsecurityexample.model.dto.TokenDTO;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenProvider {

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expire-length}")
    private int expireLengthInMillisSeconds;

    @Value("${security.jwt.refresh-expire-length}")
    private int refreshExpireLengthInDays;

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
        Boolean authenticated = true;
        String refreshToken = getRefreshToken(username, roles, createdAt, expiration);
        String token = getAcessToken(username, roles, createdAt, expiration);
        return new TokenDTO(username, token, refreshToken, authenticated, expiration, createdAt);
    }

    private String getRefreshToken(String username, List<String> roles, LocalDateTime createdAt, LocalDateTime expiration) {
        LocalDateTime refreshExpiration = expiration.plusDays(refreshExpireLengthInDays);
        return JWT.create()
                .withClaim("roles", roles)
                .withIssuedAt(createdAt.atZone(ZoneId.systemDefault()).toInstant())
                .withExpiresAt(refreshExpiration.atZone(ZoneId.systemDefault()).toInstant())
                .withSubject(username)
                .sign(algorithm);
    }

    private String getAcessToken(String username, List<String> roles, LocalDateTime createdAt, LocalDateTime expiration) {
        String issueUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        return JWT.create()
                .withClaim("roles", roles)
                .withIssuedAt(createdAt.atZone(ZoneId.systemDefault()).toInstant())
                .withExpiresAt(expiration.atZone(ZoneId.systemDefault()).toInstant())
                .withSubject(username)
                .withIssuer(issueUrl)
                .sign(algorithm);
    }

    public Authentication getAuthentication(String token) {
        DecodedJWT decodedJWT = decodedToken(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(decodedJWT.getSubject());
        return  new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private DecodedJWT decodedToken(String token) {
        JWTVerifier verifier = JWT.require(algorithm)
                .build();
        return verifier.verify(token);
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.isNoneBlank(bearerToken)) {
            if(bearerToken.startsWith("Bearer ")) return bearerToken.substring("Bearer ".length());
        }
        return null;
    }

    public boolean isTokenValid(String token) {
        try {
            DecodedJWT decodedJWT = decodedToken(token);
            return !decodedJWT.getExpiresAt().before(new Date());
        } catch (Exception e) {

            throw new InvalidJWTAuthenticationException("Invalid JWT token");
        }
    }

}
