package com.api.loginbancario.services;

import com.api.loginbancario.dto.AppUserDto;
import com.api.loginbancario.enums.AppRole;
import com.api.loginbancario.models.AppUser;
import com.api.loginbancario.repositories.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthRepository repository;
    private final PasswordEncoder passwordEncoder;

    public String register(AppUserDto request) {
        var user = AppUser.builder()
                .accountNumber(request.getAccountNumber())
                .password(passwordEncoder.encode(request.getPassword()))
                .appRole(AppRole.USER)
                .build();

        repository.save(user);

        return "registered";
    }
}
