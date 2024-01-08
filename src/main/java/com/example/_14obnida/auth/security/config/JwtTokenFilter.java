package com.example._14obnida.auth.security.config;

import com.example._14obnida.auth.security.AuthDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import java.io.IOException;


@RequiredArgsConstructor
@Component
public class JwtTokenFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;

    private final String BEARER = "Bearer ";

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain
    ) throws ServletException, IOException {
        String token = resolveToken(request);

        if(token != null && jwtProvider.isAccessToken(token)){
            Authentication authentication = getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);

        }

        filterChain.doFilter(request, response);
    }


    private String resolveToken(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");

        /*
        Bearer 토큰인지 검증한 후 리턴 || 아니면 null
         */
        if (authorization != null
                && authorization.length() > BEARER.length()
                && authorization.startsWith(BEARER)) {
            return authorization.substring(BEARER.length());
        }
        return null;

    }

    public Authentication getAuthentication(String token){
        Long userId = jwtProvider.parseAccessToken(token);

        UserDetails userDetails = new AuthDetails(
                userId.toString()
        );

        return new UsernamePasswordAuthenticationToken(
                userDetails, "user", userDetails.getAuthorities()
        );
    }
}
