package com.app.api.health.dto;


import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter @Builder
public class HealthCheckResponseDto {

    private String health; // 상태
    private List<String> activeProfiles; // 현재기동환경

}
