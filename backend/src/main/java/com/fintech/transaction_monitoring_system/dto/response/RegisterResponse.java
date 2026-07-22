package com.fintech.transaction_monitoring_system.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RegisterResponse {
    private String username;
    private String email;
    private String temporaryPassword;
    private String message;
}
