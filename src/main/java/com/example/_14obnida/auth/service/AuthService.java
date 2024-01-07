package com.example._14obnida.auth.service;

import com.example._14obnida.auth.dto.AuthRequest;
import com.example._14obnida.auth.dto.AuthResponse;;
import com.example._14obnida.auth.security.config.JwtProvider;
import com.example._14obnida.common.exception.DdubukException;
import com.example._14obnida.common.exception.ErrorCode;
import com.example._14obnida.user.domain.User;
import com.example._14obnida.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Transactional
    public AuthResponse login(AuthRequest authRequest) {
        //닉네임으로 유저 찾기
        User user = checkNickname(authRequest.getNickname());
        verifyPassword(authRequest.getPassword(), user.getPassword());
        return createAuthResponse(user);
    }

    private User checkNickname(String nickname){
        //해당 닉네임인 유저가 있는지 확인한 후, 없으면 예외 발생
        return userRepository.findByNickname(nickname)
                .orElseThrow(() -> new DdubukException(ErrorCode.NOT_EXIST_NICKNAME_ERROR));
    }

    private void verifyPassword(String request, String password){
        // 비밀번호가 맞는지 확인
        if(!passwordEncoder.matches(request, password))
            throw new DdubukException(ErrorCode.NOT_MATCH_PASSWORD_ERROR);

    }

    private AuthResponse createAuthResponse(User user){
        return AuthResponse.of(
                resolveAccessToken(user.getId()),
                user.getId(),
                jwtProvider.getAccessTokenTTlSecond()
        );
    }

    private String resolveAccessToken(Long userId){
        return jwtProvider.generateAccessToken(userId);
    }

}
