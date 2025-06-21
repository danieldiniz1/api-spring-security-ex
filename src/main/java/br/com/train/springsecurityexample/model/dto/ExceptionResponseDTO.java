package br.com.train.springsecurityexample.model.dto;

import java.time.LocalDateTime;

public record ExceptionResponseDTO(LocalDateTime timestamp, String errorCode, String details) {
}
