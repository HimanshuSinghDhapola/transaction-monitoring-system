package com.fintech.transaction_monitoring_system.exception;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class ErrorResponse {
    private String code;
    private int status;
    private String error;
    private String message;
    private String path;
    private LocalDateTime timestamp;
    private List<String> details;
}
