package com.vb.loginsystem.security.auth;

import com.vb.loginsystem.api.dto.LoginRequestDto;
import com.vb.loginsystem.api.dto.LoginResponseDto;
import com.vb.loginsystem.api.dto.RegisterRequestDto;
import com.vb.loginsystem.exceptions.*;
import com.vb.loginsystem.mail.Mail;
import com.vb.loginsystem.mail.MailService;
import com.vb.loginsystem.security.tokens.confirmtoken.ConfirmToken;
import com.vb.loginsystem.security.tokens.confirmtoken.ConfirmTokenService;
import com.vb.loginsystem.security.jwt.JwtService;
import com.vb.loginsystem.security.tokens.logtoken.LogToken;
import com.vb.loginsystem.security.tokens.logtoken.LogTokenService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
    private final LogTokenService logTokenService;
    private final MailService mailService;
    private final JwtService jwtService;
    @Value("${spring.mail.username}")
    private String EMAIL_FROM;
    @Value("${auth.url.confirm}")
    private String CONFIRM_URL;

    public String register(RegisterRequestDto request) {
        if (repository.findByEmail(request.getEmail()).isPresent()) {
            throw new AccountAlreadyExistsException();
        }

        final Auth auth = createAuth(request);
        final ConfirmToken confirmToken = createConfirmToken(auth);

//        return confirmToken.getToken();

        sendConfirmationEmail(auth, confirmToken);

        return "Please check your email to confirm your account";
    }

    public LoginResponseDto login(LoginRequestDto request) {
        final var token = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());

        authenticationManager.authenticate(token);

        Auth auth = repository.findByEmail(request.getEmail()).orElseThrow(AccountNotFoundException::new);

        if (!auth.isEnabled()) {
            throw new EmailAlreadyConfirmedException();
        }

        final String jwt = jwtService.generateToken(auth);
        final LogToken logToken = createLogToken(auth, jwt);

        logTokenService.revokeAllAuthTokens(auth);
        logTokenService.save(logToken);

        return LoginResponseDto.builder().token(jwt).build();
    }

    private LogToken createLogToken(Auth auth, String jwt) {
        return LogToken.builder()
                .auth(auth)
                .token(jwt)
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
        enableUser(confirmToken.getAuth().getEmail());

        return "Your account has been confirmed";
    }

    private void enableUser(String accountNumber) {
        final Auth auth = repository.findByEmail(accountNumber).orElseThrow(AccountNotFoundException::new);

        auth.setEnabled(true);

        repository.save(auth);
    }

    private Auth createAuth(RegisterRequestDto request) {
        Auth auth = Auth.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(AuthRole.USER)
                .build();

        repository.save(auth);

        return auth;
    }

    private ConfirmToken createConfirmToken(Auth auth) {
        final ConfirmToken confirmToken = ConfirmToken.builder()
                .token(UUID.randomUUID().toString())
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .auth(auth)
                .build();

        confirmTokenService.save(confirmToken);

        return confirmToken;
    }

    private void sendConfirmationEmail(Auth auth, ConfirmToken confirmToken) {
        final Mail mail = Mail.builder()
                .ownerRef(auth.getId())
                .emailFrom(EMAIL_FROM)
                .emailTo(auth.getEmail())
                .subject("Confirm Your Registration")
                .text(createEmailText(confirmToken.getToken()))
                .build();

        mailService.sendEmail(mail);
    }

    private String createEmailText(String confirmToken) {
        return String.format(
                """
                 Confirm Your Registration.
                 
                 Thank you for registering. We're excited to have you on board! Before we can get started, we need to verify your email address. Please click the link below to confirm your registration.
                 
                 Confirm Registration: %s%s
                 
                 Once you've confirmed your email address, you'll be able to log in to your account and start using our platform.""",
                CONFIRM_URL, confirmToken
        );
    }

    public String lockAuthAccount(String email) {
        final Auth auth = repository.findByEmail(email).orElseThrow(AccountNotFoundException::new);

        auth.setLocked(true);

        repository.save(auth);

        return "Account has been locked";
    }

    public String unlockAuthAccount(String email) {
        final Auth auth = repository.findByEmail(email).orElseThrow(AccountNotFoundException::new);

        auth.setLocked(false);

        repository.save(auth);

        return "Account has been unlocked";
    }
}
