package com.utils.ssecurityjwt.security.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.utils.ssecurityjwt.security.service.TokenService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class AuthController {


    private final TokenService tokenService;

    public AuthController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping("/token")
    public String token(Authentication authentication) {
        log.info("Token requested for user: '{}'", authentication.getName());
        String token = tokenService.generateToken(authentication);
        log.info("Token granted: {}", token);
        return token;
    }

}
