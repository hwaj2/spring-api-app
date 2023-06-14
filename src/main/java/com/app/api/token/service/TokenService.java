package com.app.api.token.service;


import com.app.api.token.dto.AccessTokenResponseDto;
import com.app.domain.member.entity.Member;
import com.app.domain.member.serivce.MemberService;
import com.app.global.jwt.constant.GrantType;
import com.app.global.jwt.service.TokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TokenService {

    private final TokenManager tokenManager;
    private final MemberService memberService;


    // Access Token 재발급
    public AccessTokenResponseDto createAccessTokenByRefreshToken(String refreshToken) {

        // 리프레쉬 토큰으로 회원정보 가져오기
        Member member = memberService.findMemberByRefreshToken(refreshToken);
        Date accessTokenExpireTime = tokenManager.createAccessTokenExpireTime(); //액세스토큰시간 갱신
        String accessToken = tokenManager.createAccessToken(member.getMemberId(), member.getRole(), accessTokenExpireTime); //액세스토큰 발급

        return AccessTokenResponseDto.builder()
                .grantType(GrantType.BEARER.getType())
                .accessToken(accessToken)
                .accessTokenExpireTime(accessTokenExpireTime)
                .build();
    }
}
