package com.vb.loginbancario.security.auth;

import com.vb.loginbancario.api.v1.dto.LoginRequestDto;
import com.vb.loginbancario.api.v1.dto.LoginResponseDto;
import com.vb.loginbancario.api.v1.dto.RegisterRequestDto;
import com.vb.loginbancario.exceptions.AccountAlreadyExistsException;
import com.vb.loginbancario.exceptions.AccountNotFoundException;
import com.vb.loginbancario.exceptions.EmailAlreadyConfirmedException;
import com.vb.loginbancario.exceptions.TokenExpiredException;
import com.vb.loginbancario.mail.Mail;
import com.vb.loginbancario.mail.MailService;
import com.vb.loginbancario.security.confirmtoken.ConfirmToken;
import com.vb.loginbancario.security.confirmtoken.ConfirmTokenService;
import com.vb.loginbancario.security.jwt.JwtService;
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
    private final MailService mailService;
    private final JwtService jwtService;
    @Value("${spring.mail.username}")
    private String EMAIL_FROM;

    public String register(RegisterRequestDto request) {
        if (repository.findByEmail(request.getEmail()).isPresent()) {
            throw new AccountAlreadyExistsException();
        }

        final Auth auth = createAuth(request);
        final ConfirmToken confirmToken = createConfirmToken(auth);

        return confirmToken.getToken();

//        sendConfirmationEmail(auth, confirmToken);
//
//        return "Please check your email to confirm your account";
    }

    public LoginResponseDto login(LoginRequestDto request) {
        final var token = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());

        authenticationManager.authenticate(token);

        Auth auth = repository.findByEmail(request.getEmail()).orElseThrow(AccountNotFoundException::new);

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
                .subject("Confirm your email")
                .text("Thank you for signing up to our application. Please click on the below url to activate your account : http://localhost:8080/api/v1/auth/confirm?token=" + confirmToken.getToken())
                .build();

        mailService.sendEmail(mail);
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
