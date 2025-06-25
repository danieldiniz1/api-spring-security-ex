package br.com.train.springsecurityexample.config.handler;

import br.com.train.springsecurityexample.exception.InvalidJWTAuthenticationException;
import br.com.train.springsecurityexample.model.dto.ExceptionResponseDTO;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.TimeZone;

@Hidden
@RestControllerAdvice
public class ControllerAdviceHandler {

    @ExceptionHandler(InvalidJWTAuthenticationException.class)
    public ResponseEntity<ExceptionResponseDTO> handleInvalidJWTAuthenticationException(InvalidJWTAuthenticationException e) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ExceptionResponseDTO(LocalDateTime.now(TimeZone.getTimeZone("America/Sao_Paulo").toZoneId()),
                        HttpStatus.FORBIDDEN.toString(), e.getMessage()));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ExceptionResponseDTO> handleUsernameNotFoundException(UsernameNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ExceptionResponseDTO(LocalDateTime.now(TimeZone.getTimeZone("America/Sao_Paulo").toZoneId()),
                        HttpStatus.NOT_FOUND.toString(), e.getMessage()));
    }
}
