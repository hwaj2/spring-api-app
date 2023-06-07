package com.app.web.kakaotoken.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

// 카카오 토큰 발급을 위한 dto 클래스
public class KakaoTokenDto {

    // 요청 dto
    @Builder
    @Getter
    public static class Request {
        private String grant_type;
        private String client_id;
        private String redirect_uri;
        private String code;
        private String client_secret;
    }

    // 응답 dto
    @ToString
    @Builder @Getter
    public static class Response {
        private String token_type;
        private String access_token;
        private Integer expires_in;
        private String refresh_token;
        private Integer refresh_token_expires_in;
        private String scope;
    }

}