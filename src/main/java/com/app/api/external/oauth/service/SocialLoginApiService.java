package com.app.api.external.oauth.service;

import com.app.api.external.oauth.model.OauthAttributes;

public interface SocialLoginApiService {

    // 사용자 정보 가져오기
    OauthAttributes getUserInfo(String accessToken);

}
