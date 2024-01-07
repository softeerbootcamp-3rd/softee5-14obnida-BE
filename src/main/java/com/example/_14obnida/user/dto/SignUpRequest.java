package com.example._14obnida.user.dto;

import com.example._14obnida.user.domain.User;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
public class SignUpRequest {

    private String nickname;

    private String password;

    public User toEntity(PasswordEncoder passwordEncoder) {
        return User.builder()
                .password(passwordEncoder.encode(password))
                .nickname(nickname)
                .total(0)
                .build();
    }
}
