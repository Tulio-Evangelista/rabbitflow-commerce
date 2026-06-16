package com.rabbitflow.exception.dto;

import java.time.LocalDateTime;
import java.util.Map;

public record ValidationErrorResponse(

        LocalDateTime timestamp,
        Integer status,
        Map<String, String> errors


) {
}
