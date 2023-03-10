package com.vb.loginbancario.api.v1.controllers;

import com.vb.loginbancario.api.v1.dto.LoginRequestDto;
import com.vb.loginbancario.api.v1.dto.LoginResponseDto;
import com.vb.loginbancario.api.v1.dto.RegisterRequestDto;
import com.vb.loginbancario.security.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService service;

//    @PostMapping("/register")
    @PostMapping(value = "/register", produces = "application/json")
    public String register(@RequestBody RegisterRequestDto request) {
        return service.register(request);
    }

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto request) {
        return service.login(request);
    }

    @PostMapping("/confirm")
    public String confirm(@RequestParam("token") String token) {
        return service.confirmToken(token);
    }
//
//    @PostMapping("/refresh")
//    public LoginResponseDto refresh(@RequestParam("refreshToken") String refreshToken) {
//        return service.refreshToken(refreshToken);
//    }
//
//    @PostMapping("/logout")
//    public String logout(@RequestParam("refreshToken") String refreshToken) {
//        return service.logout(refreshToken);
//    }
//
//    @PostMapping("/logoutAll")
//    public String logoutAll(@RequestParam("refreshToken") String refreshToken) {
//        return service.logoutAll(refreshToken);
//    }
//
//    @PostMapping("/changePassword")
//    public String changePassword(@RequestParam("refreshToken") String refreshToken, @RequestParam("newPassword") String newPassword) {
//        return service.changePassword(refreshToken, newPassword);
//    }
//
//    @PostMapping("/changeEmail")
//    public String changeEmail(@RequestParam("refreshToken") String refreshToken, @RequestParam("newEmail") String newEmail) {
//        return service.changeEmail(refreshToken, newEmail);
//    }
//
//    @PostMapping("/resetPassword")
//    public String resetPassword(@RequestParam("token") String token, @RequestParam("newPassword") String newPassword) {
//        return service.resetPassword(token, newPassword);
//    }
}
