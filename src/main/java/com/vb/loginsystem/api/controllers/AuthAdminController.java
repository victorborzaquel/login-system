package com.vb.loginsystem.api.controllers;

import com.vb.loginsystem.security.auth.AuthService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Hidden
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AuthAdminController {
    private final AuthService service;

    @PostMapping("/lock")
    public String lockAuthAccount(@RequestParam("id") String email) {
        return service.lockAuthAccount(email);
    }

    @PostMapping("/unlock")
    public String unlockAuthAccount(@RequestParam("id") String email) {
        return service.unlockAuthAccount(email);
    }
}
