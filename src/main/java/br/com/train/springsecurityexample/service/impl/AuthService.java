package br.com.train.springsecurityexample.service.impl;

import br.com.train.springsecurityexample.model.dto.TokenDTO;
import br.com.train.springsecurityexample.model.form.AccountCredentialsForm;
import br.com.train.springsecurityexample.repository.UserRepository;
import br.com.train.springsecurityexample.security.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    public AuthService(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
    }

    public TokenDTO signIn(AccountCredentialsForm credentials) {
        validateCredentials(credentials);
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(credentials.userName(), credentials.password()));
        var user = userRepository.findByUsername(credentials.userName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + credentials.userName()));
        return jwtTokenProvider.generateToken(user.getUsername(), user.getRoles());
    }

    public TokenDTO refreshToken(HttpServletRequest request, String username) {
        userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        return jwtTokenProvider.refreshToken(request);
    }


    private void validateCredentials(AccountCredentialsForm credentials) {
        if (Objects.isNull(credentials) || StringUtils.isBlank(credentials.userName()) || StringUtils.isBlank(credentials.password())) {
            throw new UsernameNotFoundException("Invalid credentials");
        }

    }


}
