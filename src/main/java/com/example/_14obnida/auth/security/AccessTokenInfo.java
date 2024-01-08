package com.example._14obnida.auth.security;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AccessTokenInfo {
    private final Long userId;
    private final String role;
}
