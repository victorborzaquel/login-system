package com.vb.loginbancario.security.auth;

import com.vb.loginbancario.data.dto.v1.LoginRequestDto;
import com.vb.loginbancario.data.dto.v1.LoginResponseDto;
import com.vb.loginbancario.data.dto.v1.RegisterRequestDto;
import com.vb.loginbancario.data.enums.AppRole;
import com.vb.loginbancario.exceptions.AccountAlreadyExistsException;
import com.vb.loginbancario.exceptions.AccountNotFoundException;
import com.vb.loginbancario.security.jwt.JwtService;
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

        final Auth auth = Auth.builder()
                .accountNumber(request.getAccountNumber())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(AppRole.USER)
                .build();

        repository.save(auth);

        return "registered";
    }

    public LoginResponseDto login(LoginRequestDto request) {
        final var token = new UsernamePasswordAuthenticationToken(request.getAccountNumber(), request.getPassword());

        authenticationManager.authenticate(token);

        Auth auth = repository.findByAccountNumber(request.getAccountNumber()).orElseThrow(AccountNotFoundException::new);

        return LoginResponseDto.builder()
                .token(jwtService.generateToken(auth))
                .build();
    }
}
