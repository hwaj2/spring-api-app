package com.app.api.exceptiontest.controller;

import com.app.api.exceptiontest.dto.BindExceptionTestDto;
import com.app.api.exceptiontest.dto.TestEnum;
import com.app.global.error.ErrorCode;
import com.app.global.error.exception.BusinessException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

// @RestController에서 전역적으로 발생하는 예외처리를 위한 Test
@RestController
@RequestMapping("/api/exception")
public class ExceptionTestController {

    // 입력값 @Validated binding error가 발생한 경우
    @GetMapping("/bind-exception-test")
    public String bindExceptionTest(@Valid BindExceptionTestDto bindExceptionTestDto){
        return "OK!";
    }

    // @RequestParam enum으로 binding 못했을 경우
    @GetMapping("/type-exception-test")
    public String typeMismatchExceptionTest(TestEnum testEnum){
        return "OK!";
    }

    // 비즈니스 로직 실행 중 오류
    @GetMapping("/business-exception-test")
    public String businessExceptionTest(String isError){
        if("true".equals(isError)){
            throw new BusinessException(ErrorCode.TEST);
        }
        return "OK!";
    }

    // 나머지 예외 발생
    @GetMapping("/exception-test")
    public String exceptionTest(String isError){
        if("true".equals(isError)){
            throw new IllegalArgumentException("나머지 예외 발생 test!");
        }
        return "OK!";
    }
}
