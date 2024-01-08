package com.example._14obnida.common.exception;

import lombok.Getter;

@Getter
public class DdubukException extends RuntimeException{
    private int status;

    private String message;

    private String solution;

    public DdubukException(ErrorCode errorCode){
        this.message = errorCode.getMessage();
        this.status = errorCode.getHttpStatus().value();
        this.solution = errorCode.getSolution();
    }

    public DdubukException(ErrorCode errorCode, String message) {
        this.message = message;
        this.status = errorCode.getHttpStatus().value();
        this.solution = errorCode.getSolution();
    }

    public DdubukException(ErrorCode errorCode, String message, String solution) {
        this.message = message;
        this.status = errorCode.getHttpStatus().value();
        this.solution = solution;
    }
}
