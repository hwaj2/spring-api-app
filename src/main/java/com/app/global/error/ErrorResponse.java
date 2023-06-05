package com.app.global.error;

import lombok.Builder;
import lombok.Getter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

// 클라이언트에게 일정한 형태의 에러메세지를 반환하기 위한 클래스
@Getter
@Builder
public class ErrorResponse {

    private String errorCode;
    private String errorMessage;

    // 정적 메소드 생성
    public static ErrorResponse of(String errorCode, String errorMessage){
        return ErrorResponse.builder()
                .errorCode(errorCode)
                .errorMessage(errorMessage)
                .build();
    }

    // 입력값을 검증하기 위한 BindingResult 이용한 정적메서드생성
    public static ErrorResponse of(String errorCode, BindingResult bindingResult){
        return ErrorResponse.builder()
                .errorCode(errorCode)
                .errorMessage(createErrorMessage(bindingResult))
                .build();
    }


    // 입력값에서 어떤 필드에서 어떤 오류가 발생했는지 반환하기 위한 정적메서드
    private static String createErrorMessage(BindingResult bindingResult) {
        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;

        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            if(!isFirst) {
                sb.append(", ");
            } else {
                isFirst = false;
            }
            sb.append("[");
            sb.append(fieldError.getField());
            sb.append("] ");
            sb.append(fieldError.getDefaultMessage());
        }

        return sb.toString();
    }
}
