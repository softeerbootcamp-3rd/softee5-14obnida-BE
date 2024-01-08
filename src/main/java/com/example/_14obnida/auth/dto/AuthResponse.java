package com.example._14obnida.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthResponse {
    private String accessToken;

    private Long userId;

    private long accessTokenExpireDate;
    public static AuthResponse of(
            String accessToken, Long userId, long accessTokenExpireDate) {
        return AuthResponse.builder()
                .accessToken(accessToken)
                .userId(userId)
                .accessTokenExpireDate(accessTokenExpireDate)
                .build();
    }

}
