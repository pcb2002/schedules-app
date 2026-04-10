package com.schedulesapp.dto;

import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@Builder
public class ErrorResponse {
    private final LocalDateTime timestamp = LocalDateTime.now(); // 에러 발생 시각
    private final int status;
    private final String error;
    private final String message;
}