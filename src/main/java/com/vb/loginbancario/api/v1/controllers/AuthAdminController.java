package com.vb.loginbancario.api.v1.controllers;

import com.vb.loginbancario.security.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
