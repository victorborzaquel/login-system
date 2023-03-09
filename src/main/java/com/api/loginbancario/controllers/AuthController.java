package com.api.loginbancario.controllers;

import com.api.loginbancario.dto.AppUserDto;
import com.api.loginbancario.services.AuthService;
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
    public String register(@RequestBody AppUserDto request) {
        return service.register(request);
    }
}
