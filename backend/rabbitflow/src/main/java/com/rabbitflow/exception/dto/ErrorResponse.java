package com.rabbitflow.exception.dto;
import java.time.LocalDateTime;

public record ErrorResponse(
                  LocalDateTime timestamp,
                  Integer status,
                  String erro) {
}
