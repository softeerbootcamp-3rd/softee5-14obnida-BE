package com.example._14obnida.auth.controller;

import com.example._14obnida.auth.dto.AuthRequest;
import com.example._14obnida.auth.dto.AuthResponse;
import com.example._14obnida.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/app/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")    //로그인
    public AuthResponse login(@RequestBody AuthRequest authRequest){
        return authService.login(authRequest);
    }
}
