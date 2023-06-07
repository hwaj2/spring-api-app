package com.app.global.error;

import feign.FeignException;
import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

// HTTP통신시 에러 발생시 처리해 줄 ErrorDecoder 파일

@Slf4j //로깅
public class FeignClientExceptionErrorDecoder implements ErrorDecoder {

    private ErrorDecoder errorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        log.error("{} 요청실패, status : {}, reason : {} ", methodKey, response.status(), response.reason());

        FeignException exception = FeignException.errorStatus(methodKey, response);
        HttpStatus httpStatus = HttpStatus.valueOf(response.status()); // 안에있는 응답코드의 반환값을 이용해 HttpStatus생성

        // 서버가 일시적부하가 발생하면 나타날수 있는 500번대 http status 를 반환 받을 경우, http 요청 재시도
        if(httpStatus.is5xxServerError()) {
            return new RetryableException(
                    response.status(),
                    exception.getMessage(),
                    response.request().httpMethod(),
                    exception,
                    null,
                    response.request()
            );
        }
        return errorDecoder.decode(methodKey,response);
    }
}
