package com.vb.loginsystem.security.tokens.logtoken;

import com.vb.loginsystem.exceptions.TokenNotFoundException;
import com.vb.loginsystem.security.auth.Auth;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LogTokenService {
    private final LogTokenRepository repository;

    public void save(LogToken logToken) {
        repository.save(logToken);
    }

    public LogToken getToken(String token) {
        return repository.findByToken(token).orElseThrow(TokenNotFoundException::new);
    }

    public void revokeAllAuthTokens(Auth auth) {
        List<LogToken> logTokens = repository.findAllByValidTokensByUser(auth.getId());

        logTokens.forEach(it -> {
            it.setRevoked(true);
            it.setExpired(true);
        });

        repository.saveAll(logTokens);
    }

    public boolean isTokenValid(String token) {
        return repository.findByToken(token).map(it -> !it.isExpired() && !it.isRevoked()).orElse(false);
    }
}
