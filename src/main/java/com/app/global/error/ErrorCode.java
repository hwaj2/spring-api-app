package com.app.global.error;


import lombok.Getter;
import org.springframework.http.HttpStatus;

// 반환할 HTTP status 값, 에러 코드, 에러메세지를 관리하는 Enum 클래스
@Getter
public enum ErrorCode {

    TEST(HttpStatus.INTERNAL_SERVER_ERROR, "001", "businessExceptionTest!!!"),

    // 인증
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "A-001", "토큰이 만료되었습니다."),
    NOT_VALID_TOKEN(HttpStatus.UNAUTHORIZED, "A-002", "유효하지 않는 토큰입니다."),
    ;


    ErrorCode(HttpStatus httpStatus, String errorCode, String message) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.message = message;
    }

    private HttpStatus httpStatus;
    private String errorCode;
    private String message;


}
