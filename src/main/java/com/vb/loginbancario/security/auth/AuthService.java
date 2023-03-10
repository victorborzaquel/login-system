package com.vb.loginbancario.security.auth;

import com.vb.loginbancario.data.dto.v1.LoginRequestDto;
import com.vb.loginbancario.data.dto.v1.LoginResponseDto;
import com.vb.loginbancario.data.dto.v1.RegisterRequestDto;
import com.vb.loginbancario.data.enums.AppRole;
import com.vb.loginbancario.exceptions.AccountAlreadyExistsException;
import com.vb.loginbancario.exceptions.AccountNotFoundException;
import com.vb.loginbancario.exceptions.EmailAlreadyConfirmedException;
import com.vb.loginbancario.exceptions.TokenExpiredException;
import com.vb.loginbancario.security.confirmtoken.ConfirmToken;
import com.vb.loginbancario.security.confirmtoken.ConfirmTokenService;
import com.vb.loginbancario.security.jwt.JwtService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
    private final AuthRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final ConfirmTokenService confirmTokenService;
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

        final ConfirmToken confirmToken = ConfirmToken.builder()
                .token(UUID.randomUUID().toString())
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .auth(auth)
                .build();

        confirmTokenService.save(confirmToken);

        return confirmToken.getToken();
    }

    public LoginResponseDto login(LoginRequestDto request) {
        final var token = new UsernamePasswordAuthenticationToken(request.getAccountNumber(), request.getPassword());

        authenticationManager.authenticate(token);

        Auth auth = repository.findByAccountNumber(request.getAccountNumber()).orElseThrow(AccountNotFoundException::new);

        return LoginResponseDto.builder()
                .token(jwtService.generateToken(auth))
                .build();
    }

    public String confirmToken(String token) {
        final ConfirmToken confirmToken = confirmTokenService.getToken(token);

        if (Objects.nonNull(confirmToken.getConfirmedAt())) {
            throw new EmailAlreadyConfirmedException();
        }

        if (confirmToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new TokenExpiredException();
        }

        confirmTokenService.setConfirmedAt(token);
        enableUser(confirmToken.getAuth().getAccountNumber());

        return null;
    }

    private void enableUser(String accountNumber) {
        final Auth auth = repository.findByAccountNumber(accountNumber).orElseThrow(AccountNotFoundException::new);

        auth.setEnabled(true);

        repository.save(auth);
    }
}
