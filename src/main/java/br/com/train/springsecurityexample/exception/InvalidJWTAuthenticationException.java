package br.com.train.springsecurityexample.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class InvalidJWTAuthenticationException extends AuthenticationException {

    public InvalidJWTAuthenticationException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public InvalidJWTAuthenticationException(String msg) {
        super(msg);
    }
}
