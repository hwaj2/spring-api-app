package com.app.api.member.controller;

import com.app.api.member.dto.MemberInfoResponseDto;
import com.app.api.member.service.MemberInfoService;
import com.app.global.jwt.service.TokenManager;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberInfoController {

    private final TokenManager tokenManager;
    private final MemberInfoService memberInfoService;

    @GetMapping("/info")
    public ResponseEntity<MemberInfoResponseDto> getMemberInfo(@RequestHeader("Authorization") String authorizationHeader) {

        String accessToken = authorizationHeader.split(" ")[1];
        Claims tokenClaims = tokenManager.getTokenClaims(accessToken);
        Long memberId = Long.valueOf((Integer) tokenClaims.get("memberId"));
        MemberInfoResponseDto memberInfoResponseDto = memberInfoService.getMemberInfo(memberId);
        return ResponseEntity.ok(memberInfoResponseDto);
    }

}