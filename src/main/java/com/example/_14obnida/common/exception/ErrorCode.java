package com.example._14obnida.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    TOKEN_EXPIRED_ERROR(HttpStatus.UNAUTHORIZED, "Access Token이 만료되었습니다.","Access Token이 만료되었으므로 재발급받아야 합니다." ),
    SECURITY_CONTEXT_NOT_FOUND_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Secutiry context를 찾지 못합니다.", "Security Context 에러입니다."),
    INVALID_TOKEN_ERROR(HttpStatus.UNAUTHORIZED, "잘못된 토큰입니다.", "잘못된 토큰이므로 다시 로그인 해주세요."),
    DUPLICATE_NICKNAME_ERROR(HttpStatus.BAD_REQUEST, "중복된 닉네임입니다.", "중복되지 않은 닉네임으로 다시 회원가입해주세요."),
    NOT_EXIST_NICKNAME_ERROR(HttpStatus.BAD_REQUEST, "존재하지 않는 닉네임입니다.", "가입된 닉네임을 다시 확인해주세요."),
    NOT_MATCH_PASSWORD_ERROR(HttpStatus.BAD_REQUEST, "비밀번호가 맞지 않습니다.", "비밀번호를 다시 확인해주세요."),
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "회원 조회 시 해당 회원을 찾을 수 없습니다.", "존재하지 않는 사용자이므로 로그인 정보를 다시 확인해주세요.");

    private final HttpStatus httpStatus;
    private final String message;
    private final String solution;
}
