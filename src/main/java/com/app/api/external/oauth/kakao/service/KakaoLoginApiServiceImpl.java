package com.app.api.external.oauth.kakao.service;

import com.app.api.external.oauth.kakao.client.KakaoUserInfoClient;
import com.app.api.external.oauth.kakao.dto.KakaoUserInfoResponseDto;
import com.app.api.external.oauth.model.OauthAttributes;
import com.app.api.external.oauth.service.SocialLoginApiService;
import com.app.domain.member.constant.MemberType;
import com.app.global.jwt.constant.GrantType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class KakaoLoginApiServiceImpl implements SocialLoginApiService {


    private final KakaoUserInfoClient kakaoUserInfoClient;
    private final String CONTENT_TYPE = "application/x-www-form-urlencoded;charset=utf-8";

    @Override
    public OauthAttributes getUserInfo(String accessToken) {

        /*  카카오 API 문서에 정의된 필수 헤더와 파라미터를 세팅 후에 HTTP 요청
            Authorization: Bearer ${ACCESS_TOKEN},  Content-type: application/x-www-form-urlencoded;charset=utf-8  */
        KakaoUserInfoResponseDto kakaoUserInfoResponseDto
                = kakaoUserInfoClient.getKakaoUserInfo(CONTENT_TYPE, GrantType.BEARER.getType() + " " + accessToken);

        KakaoUserInfoResponseDto.KakaoAccount kakaoAccount = kakaoUserInfoResponseDto.getKakaoAccount(); //OauthAttributes(이름, 이메일, 프로필, 회원타입)
        String email = kakaoAccount.getEmail();

        return OauthAttributes.builder()
                .name(kakaoAccount.getProfile().getNickname())
                .email(!StringUtils.hasText(email) ? kakaoUserInfoResponseDto.getId() : email)
                .profile(kakaoAccount.getProfile().getThumbnailImageUrl())
                .memberType(MemberType.KAKAO)
                .build();
    }
}
