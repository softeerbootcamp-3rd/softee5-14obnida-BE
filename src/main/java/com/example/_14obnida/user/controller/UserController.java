package com.example._14obnida.user.controller;

import com.example._14obnida.common.dto.BaseResponse;
import com.example._14obnida.user.dto.SignUpRequest;
import com.example._14obnida.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/app/signup")
public class UserController {
    private final UserService userService;

    @PostMapping
    public BaseResponse<Long> signUp(@RequestBody SignUpRequest request) {
        return userService.signUp(request);
    }
}
