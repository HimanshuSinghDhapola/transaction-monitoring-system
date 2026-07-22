package com.fintech.transaction_monitoring_system.service;

import com.fintech.transaction_monitoring_system.dto.request.ChangePasswordRequest;
import com.fintech.transaction_monitoring_system.dto.request.LoginRequest;
import com.fintech.transaction_monitoring_system.dto.request.RegisterRequest;
import com.fintech.transaction_monitoring_system.dto.response.LoginResponse;
import com.fintech.transaction_monitoring_system.dto.response.RegisterResponse;

public interface AuthService {
    RegisterResponse register(RegisterRequest request);
    LoginResponse login(LoginRequest request);
    void changePassword(String username, ChangePasswordRequest request);
}
