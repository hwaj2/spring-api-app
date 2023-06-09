package com.app.api.login.service;

import com.app.api.external.oauth.model.OauthAttributes;
import com.app.api.external.oauth.service.SocialLoginApiService;
import com.app.api.external.oauth.service.SocialLoginApiServiceFactory;
import com.app.api.login.dto.OauthLoginDto;
import com.app.domain.member.constant.MemberType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class OauthLoginService {

    public OauthLoginDto.Response oauthLogin(String accessToken, MemberType memberType){

      SocialLoginApiService socialLoginApiService = SocialLoginApiServiceFactory.getSocialLoginApiService(memberType);
      OauthAttributes userInfo =  socialLoginApiService.getUserInfo(accessToken);
      log.info("userInfo : {}", userInfo);

      return OauthLoginDto.Response.builder().build();
    }

}
