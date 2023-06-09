package com.app.api.login.dto;

import com.app.domain.member.constant.MemberType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

@Getter @Setter
public class OauthLoginDto {

    @Getter @Setter
    public static class Request{
        private String memberType;
    }

    @Getter @Setter @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class Response{

        private String grantType;
        private String accessToken;
        private String refreshToken;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private String accessTokenExpireTime;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private String refreshTokenExpireTime;

    }

}
