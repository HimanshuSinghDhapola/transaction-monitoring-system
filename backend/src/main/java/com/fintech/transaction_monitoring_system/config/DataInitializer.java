package com.fintech.transaction_monitoring_system.config;

import com.fintech.transaction_monitoring_system.entity.User;
import com.fintech.transaction_monitoring_system.enums.UserRole;
import com.fintech.transaction_monitoring_system.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AdminProperties adminProperties;

    @Override
    public void run(String... args) {
        if(userRepository.existsByUsername(adminProperties.getUsername())){
            log.info("Admin already exists, skipping seed");
            return;
        }

        User admin = User.builder()
                .username(adminProperties.getUsername())
                .email(adminProperties.getEmail())
                .passwordHash(passwordEncoder.encode(adminProperties.getPassword()))
                .passwordChangeRequired(false)
                .role(UserRole.ADMIN)
                .build();

        userRepository.save(admin);

        log.info("Default admin create: {}", adminProperties.getUsername());
    }
}
