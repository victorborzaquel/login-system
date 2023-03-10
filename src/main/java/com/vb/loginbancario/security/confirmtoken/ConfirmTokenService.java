package com.vb.loginbancario.security.confirmtoken;

import com.vb.loginbancario.exceptions.TokenNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ConfirmTokenService {
    private final ConfirmaTokenRepository repository;

    public void save(ConfirmToken token) {
        repository.save(token);
    }

    public ConfirmToken getToken(String token) {
        return repository.findByToken(token).orElseThrow(TokenNotFoundException::new);
    }

    public void setConfirmedAt(String token) {
        getToken(token).setConfirmedAt(LocalDateTime.now());
    }
}
