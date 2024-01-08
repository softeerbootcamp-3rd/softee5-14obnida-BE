package com.example._14obnida.auth.security.config;

import com.example._14obnida.common.exception.DdubukException;
import com.example._14obnida.common.exception.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JwtExceptionFilter extends OncePerRequestFilter {
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (DdubukException e) {

            responseToClient(
                    response,
                    resolveErrorCode(e));
        }
    }


    private ErrorCode resolveErrorCode(DdubukException e) {
        for(ErrorCode errorCode : ErrorCode.values()){
            if(errorCode.getMessage().equals(e.getMessage())
            && errorCode.getSolution().equals(e.getSolution()))
                return errorCode;
        }

        return null;
    }
    private void responseToClient(HttpServletResponse response, ErrorCode errorCode)
            throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(errorCode.getHttpStatus().value());
        response.getWriter().write(objectMapper.writeValueAsString(errorCode));
    }

}
