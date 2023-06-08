package com.app.api.external.oauth.kakao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;



// 카카오 사용자 정보 가져오기 응답dto
@Getter @Setter
public class KakaoUserInfoResponseDto {

    // 회원번호
    private String id;

    // 카카오 계정 정보
    @JsonProperty("kakao_account") /* 카멜 케이스로 받을수 있도록 사용 */
    private KakaoAccount kakaoAccount;

    @Getter @Setter
    public static class KakaoAccount {

        private String email;    // 카카오계정 대표 이메일
        private Profile profile; // 프로필 정보

        @Getter @Setter
        public static class Profile{
            private String nickName; // 닉네임
            @JsonProperty("thumbnail_image_url") /* 카멜 케이스로 받을수 있도록 사용 */
            private String thumbnailImageUrl; // 프로필 미리보기 이미지 url
        }

    }



}
