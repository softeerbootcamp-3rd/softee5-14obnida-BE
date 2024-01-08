package com.example._14obnida.user.service;

import com.example._14obnida.common.dto.BaseResponse;
import com.example._14obnida.common.exception.DdubukException;
import com.example._14obnida.common.exception.ErrorCode;
import com.example._14obnida.user.dto.SignUpRequest;
import com.example._14obnida.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public BaseResponse<Long> signUp(SignUpRequest request) {
        //중복 이메일 체크
        if(userRepository.existsByNickname(request.getNickname()))
            throw new DdubukException(ErrorCode.DUPLICATE_NICKNAME_ERROR);

        Long userId = userRepository
                .save(request.toEntity(passwordEncoder))
                .getId();
        return BaseResponse.of("회원가입 되었습니다.", userId);
    }

}
