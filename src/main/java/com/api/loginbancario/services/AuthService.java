package com.api.loginbancario.services;

import com.api.loginbancario.dto.LoginRequestDto;
import com.api.loginbancario.dto.RegisterRequestDto;
import com.api.loginbancario.enums.AppRole;
import com.api.loginbancario.exceptions.InvalidAccountLoginException;
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

    public String register(RegisterRequestDto request) {
        var user = AppUser.builder()
                .accountNumber(request.getAccountNumber())
                .password(passwordEncoder.encode(request.getPassword()))
                .appRole(AppRole.USER)
                .build();

        repository.save(user);

        return "registered";
    }

    public String login(LoginRequestDto request) {
        AppUser appUser = repository.findByAccountNumber(request.getAccountNumber())
                .filter(it -> passwordEncoder.matches(request.getPassword(), it.getPassword()))
                .orElseThrow(InvalidAccountLoginException::new);

        return String.format("logged: %s", appUser.getAccountNumber());
    }
}
