package com.vb.loginbancario.api.v1;

import com.vb.loginbancario.data.dto.v1.LoginRequestDto;
import com.vb.loginbancario.data.dto.v1.LoginResponseDto;
import com.vb.loginbancario.data.dto.v1.RegisterRequestDto;
import com.vb.loginbancario.security.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService service;

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequestDto request) {
        return service.register(request);
    }

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto request) {
        return service.login(request);
    }

}
