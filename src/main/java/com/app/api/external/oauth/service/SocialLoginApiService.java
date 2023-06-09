package com.app.api.external.oauth.service;

import com.app.api.external.oauth.model.OauthAttributes;

// 소셜API 에서 공통적으로 사용되는 메서드 정의
public interface SocialLoginApiService {

    // 사용자 정보 가져오기
    OauthAttributes getUserInfo(String accessToken);

}
