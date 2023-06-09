package com.app.api.login.service;

import com.app.api.external.oauth.model.OauthAttributes;
import com.app.api.external.oauth.service.SocialLoginApiService;
import com.app.api.external.oauth.service.SocialLoginApiServiceFactory;
import com.app.api.login.dto.OauthLoginDto;
import com.app.domain.member.constant.MemberType;
import com.app.domain.member.constant.Role;
import com.app.domain.member.entity.Member;
import com.app.domain.member.serivce.MemberService;
import com.app.global.jwt.dto.JwtTokenDto;
import com.app.global.jwt.service.TokenManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OauthLoginService {

    private final MemberService memberService;
    private final TokenManager tokenManager;


    public OauthLoginDto.Response oauthLogin(String accessToken, MemberType memberType) {

        SocialLoginApiService socialLoginApiService = SocialLoginApiServiceFactory.getSocialLoginApiService(memberType);
        OauthAttributes userInfo = socialLoginApiService.getUserInfo(accessToken);
        log.info("userInfo : {}", userInfo);

        JwtTokenDto jwtTokenDto;

        // 신규회원 및 기존회원 처리
        Optional<Member> optionalMember = memberService.findMemberByEmail(userInfo.getEmail());

        if (optionalMember.isEmpty()) {  // 신규회원
            Member oauthMember = userInfo.toMemberEntity(memberType, Role.ADMIN);
            memberService.registerMember(oauthMember);
            /* 서비스 토큰 발급 */
            jwtTokenDto = tokenManager.createJwtTokenDto(oauthMember.getMemberId(), oauthMember.getRole());
            oauthMember.updateRefreshToken(jwtTokenDto);
            log.info("소셜로그인 신규회원 JWT 토큰발급 완료!!");

        }else{  // 기존회원
            Member oauthMember = optionalMember.get();
            /* 서비스 토큰 발급 */
            jwtTokenDto = tokenManager.createJwtTokenDto(oauthMember.getMemberId(), oauthMember.getRole());
            oauthMember.updateRefreshToken(jwtTokenDto);
            log.info("소셜로그인 기존회원 JWT 토큰발급 완료!!");
        }

        return OauthLoginDto.Response.of(jwtTokenDto);

    }

}
