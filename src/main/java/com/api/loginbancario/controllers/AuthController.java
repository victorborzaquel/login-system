package com.api.loginbancario.controllers;

import com.api.loginbancario.dto.LoginRequestDto;
import com.api.loginbancario.dto.RegisterRequestDto;
import com.api.loginbancario.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    public String login(@RequestBody LoginRequestDto request) {
        return service.login(request);
    }

}
