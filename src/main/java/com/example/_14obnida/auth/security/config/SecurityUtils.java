package com.example._14obnida.auth.security.config;

import com.example._14obnida.common.exception.DdubukException;
import com.example._14obnida.common.exception.ErrorCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {
    private static SimpleGrantedAuthority anonymous = new SimpleGrantedAuthority("ROLE_ANONYMOUS");
    public static Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new DdubukException(ErrorCode.SECURITY_CONTEXT_NOT_FOUND_ERROR);
        }

        if (authentication.isAuthenticated() && !authentication.getAuthorities().equals(anonymous)) {
            return Long.valueOf(authentication.getName());
        }

        return 0L;
    }
}
