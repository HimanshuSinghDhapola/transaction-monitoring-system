package com.fintech.transaction_monitoring_system.dto.response;

import com.fintech.transaction_monitoring_system.enums.UserRole;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponse {
    private String token;
    private String username;
    private UserRole role;
    private boolean passwordChangeRequired;
}
