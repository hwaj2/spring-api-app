package com.app.api.health.controller;

import com.app.api.health.dto.HealthCheckResponseDto;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.asm.Advice;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor //생성자주입
public class HealthCheckController {

    // 현재기동환경
    private final Environment environment;

    // 서버 상태체크 api 구현
    @GetMapping("/health")
    public ResponseEntity<HealthCheckResponseDto> healthCheck(){
            HealthCheckResponseDto healthCheckResponseDto = HealthCheckResponseDto.builder()
                    .health("ok")
                    .activeProfiles(Arrays.asList(environment.getActiveProfiles())) // Edit Configuration에서 변경
                    .build();

        return ResponseEntity.ok(healthCheckResponseDto); // body에 담아서 반환
    }




}
