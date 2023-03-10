package com.api.loginbancario.services;

import com.api.loginbancario.dto.LoginRequestDto;
import com.api.loginbancario.dto.LoginResponseDto;
import com.api.loginbancario.dto.RegisterRequestDto;
import com.api.loginbancario.enums.AppRole;
import com.api.loginbancario.exceptions.AccountAlreadyExistsException;
import com.api.loginbancario.exceptions.InvalidAccountLoginException;
import com.api.loginbancario.models.AppUser;
import com.api.loginbancario.repositories.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public String register(RegisterRequestDto request) {
        if (repository.findByAccountNumber(request.getAccountNumber()).isPresent()) {
            throw new AccountAlreadyExistsException();
        }

        final AppUser appUser = AppUser.builder()
                .accountNumber(request.getAccountNumber())
                .password(passwordEncoder.encode(request.getPassword()))
                .appRole(AppRole.USER)
                .build();

        repository.save(appUser);

        return "registered";
    }

    public LoginResponseDto login(LoginRequestDto request) {
        final var token = new UsernamePasswordAuthenticationToken(request.getAccountNumber(), request.getPassword());

        authenticationManager.authenticate(token);

        AppUser appUser = repository.findByAccountNumber(request.getAccountNumber())
                .orElseThrow(InvalidAccountLoginException::new);

        return LoginResponseDto.builder()
                .token(jwtService.generateToken(appUser))
                .build();
    }
}
