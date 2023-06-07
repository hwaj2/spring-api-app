package com.app.global.config;

import com.app.global.error.FeignClientExceptionErrorDecoder;
import feign.Logger;
import feign.Retryer;
import feign.codec.ErrorDecoder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

// Feign 설정파일 : 로깅(logging)레벨,  재시도를 위한 Retryer을 빈으로 등록
@Configuration
@EnableFeignClients(basePackages = "com.app") // 변경시 todo 패키지명 수정
@Import(FeignClientsConfiguration.class)
public class FeignConfigration {

    // logging level 설정
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    // http 에러발생 처리를 위한 ErrorDecoder 등록
    @Bean
    public ErrorDecoder errorDecoder() {
        return new FeignClientExceptionErrorDecoder();
    }

    // 재시도를 위한 Retryer 등록
    @Bean
    public Retryer retryer() {
        return new Retryer.Default(1000, 2000, 3);
    }


}
