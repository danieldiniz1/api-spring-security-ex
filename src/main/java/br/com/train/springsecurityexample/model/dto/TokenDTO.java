package br.com.train.springsecurityexample.model.dto;

import java.time.LocalDateTime;

public record TokenDTO(String username,
                       String token,
                       String refreshToken,
                       Boolean authenticated,
                       LocalDateTime expiration,
                       LocalDateTime createdAt) {


}
