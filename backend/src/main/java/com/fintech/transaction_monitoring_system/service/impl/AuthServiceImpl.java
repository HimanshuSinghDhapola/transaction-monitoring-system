package com.fintech.transaction_monitoring_system.service.impl;

import com.fintech.transaction_monitoring_system.dto.request.ChangePasswordRequest;
import com.fintech.transaction_monitoring_system.dto.request.LoginRequest;
import com.fintech.transaction_monitoring_system.dto.request.RegisterRequest;
import com.fintech.transaction_monitoring_system.dto.response.LoginResponse;
import com.fintech.transaction_monitoring_system.dto.response.RegisterResponse;
import com.fintech.transaction_monitoring_system.entity.User;
import com.fintech.transaction_monitoring_system.enums.ErrorCode;
import com.fintech.transaction_monitoring_system.exception.BusinessException;
import com.fintech.transaction_monitoring_system.repository.UserRepository;
import com.fintech.transaction_monitoring_system.security.JwtTokenProvider;
import com.fintech.transaction_monitoring_system.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-;@#$%&*";
    private static final int PASSWORD_LENGTH = 10;
    private static final SecureRandom random = new SecureRandom();

    private String generatePassword(){
        StringBuilder password = new StringBuilder(PASSWORD_LENGTH);
        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            password.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return password.toString();
    }

    @Override
    public RegisterResponse register(RegisterRequest request) {
        if(userRepository.existsByUsername(request.getUsername())){
            throw new BusinessException(ErrorCode.AUTH_E001);
        }
        if(userRepository.existsByEmail(request.getEmail())){
            throw new BusinessException(ErrorCode.AUTH_E002);
        }

        String temporaryPassword = generatePassword();

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(temporaryPassword))
                .build();

        userRepository.save(user);
        return RegisterResponse.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .temporaryPassword(temporaryPassword)
                .message("Account created. Share temporary password with analyst")
                .build();
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String token = jwtTokenProvider.generateToken(userDetails);

        User user = userRepository.findByUsername(request.getUsername()).orElseThrow();

        return LoginResponse.builder()
                .token(token)
                .role(user.getRole())
                .username(user.getUsername())
                .passwordChangeRequired(user.isPasswordChangeRequired())
                .build();
    }

    @Override
    public void changePassword(String username, ChangePasswordRequest request) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new BusinessException(ErrorCode.GEN_E001));

        user.setPasswordHash(passwordEncoder.encode(request.getNewPassword()));
        user.setPasswordChangeRequired(false);
        userRepository.save(user);
    }
}
