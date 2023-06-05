package com.app.global.error.exception;


import com.app.global.error.ErrorCode;
import lombok.Getter;

// 비즈니스 로직 수행시 발생한 예외처리를 위한 BusinessException 생성
@Getter
public class BusinessException extends RuntimeException {

    private ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
