package com.example._14obnida.auth.security.config;

import com.example._14obnida.common.exception.DdubukException;
import com.example._14obnida.common.exception.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Component
@Slf4j
public class AccessDeniedFilter extends OncePerRequestFilter {
    private final ObjectMapper objectMapper;

    private AuthenticationTrustResolver authenticationTrustResolver =
            new AuthenticationTrustResolverImpl();

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (AccessDeniedException e) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated()) {
                log.error("Access denied for user: {} - Request URL: {} ", authentication.getName(), request.getRequestURL(), authentication.getAuthorities());
            } else {
                log.error("Authentication: " + authentication);
                log.error("Access denied for anonymous user - Request URL: {}", request.getRequestURL());
            }
            exceptionHandle(response, ErrorCode.INVALID_TOKEN_ERROR);
        }
    }


    private void exceptionHandle(HttpServletResponse response, ErrorCode errorCode)
            throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(errorCode.getHttpStatus().value());
        response.getWriter().write(objectMapper.writeValueAsString(errorCode));
    }
}
