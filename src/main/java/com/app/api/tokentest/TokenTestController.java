package com.app.api.tokentest;

import com.app.domain.member.constant.Role;
import com.app.global.jwt.dto.JwtTokenDto;
import com.app.global.jwt.service.TokenManager;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/token-test")
@RequiredArgsConstructor
public class TokenTestController {

    private final TokenManager tokenManager;

    // 서비스 토큰 발급 test
    @GetMapping("/create")
    public JwtTokenDto createJwtTokenDto() {
        return tokenManager.createJwtTokenDto(1L, Role.ADMIN);
    }


    // 서비스 토큰 검증 test
    @GetMapping("/valid")
    public String validateJwtToken(@RequestParam String token) {

        // 시크릿키를 통해 서명검증
        tokenManager.validateToken(token);

        // 토큰안에 payload에 있는 claim 정보 가져오기
        Claims tokenClaims = tokenManager.getTokenClaims(token);
        Long memberId = Long.valueOf((Integer) tokenClaims.get("memberId"));
        String role = (String) tokenClaims.get("role");
        log.info("memberId : {}", memberId);
        log.info("role : {}", role);
        return "success";
    }

}
