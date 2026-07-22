package com.fintech.transaction_monitoring_system.controller;

import com.fintech.transaction_monitoring_system.dto.request.ChangePasswordRequest;
import com.fintech.transaction_monitoring_system.dto.request.LoginRequest;
import com.fintech.transaction_monitoring_system.dto.request.RegisterRequest;
import com.fintech.transaction_monitoring_system.dto.response.ApiResponse;
import com.fintech.transaction_monitoring_system.dto.response.LoginResponse;
import com.fintech.transaction_monitoring_system.dto.response.RegisterResponse;
import com.fintech.transaction_monitoring_system.enums.SuccessCode;
import com.fintech.transaction_monitoring_system.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<RegisterResponse>> register(@Valid @RequestBody RegisterRequest request){
        RegisterResponse response = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.success(SuccessCode.AUTH_S002, response)
        );
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request){
        LoginResponse response = authService.login(request);
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success(SuccessCode.AUTH_S001, response)
        );
    }

    @PatchMapping("/change-password")
    public ResponseEntity<ApiResponse<Void>> changePassword(
            @Valid @RequestBody ChangePasswordRequest request,
            @AuthenticationPrincipal UserDetails userDetails){
        authService.changePassword(userDetails.getUsername(), request);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.AUTH_S003, null));
    }
}
