package com.vb.loginbancario.security.auth;

import com.vb.loginbancario.exceptions.TokenExpiredException;
import com.vb.loginbancario.security.tokens.logtoken.LogToken;
import com.vb.loginbancario.security.tokens.logtoken.LogTokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {
    private final LogTokenService service;

    @Override
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
        final String header = request.getHeader("Authorization");

        if (Objects.isNull(header) || !header.startsWith("Bearer ")) {
            return;
        }

        final String jwt = header.replace("Bearer ", "");

        final LogToken token = service.getToken(jwt);

        if (token.isExpired() && token.isRevoked()) {
            throw new TokenExpiredException();
        }

        token.setRevoked(true);
        token.setExpired(true);
        service.save(token);
    }
}
