package com.vb.loginsystem.api.controllers;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Hidden
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/oauth")
public class OauthController {

    @GetMapping(value = "/login", produces = "application/json")
    public OAuth2AuthenticationToken login(OAuth2AuthenticationToken request) {
        return request;
    }

}
