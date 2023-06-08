package com.app.api.external.oauth.service;


import com.app.domain.member.constant.MemberType;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SocialLoginApiServiceFactory {

    private static Map<String, SocialLoginApiService> socialLoginApiServices;

    public SocialLoginApiServiceFactory(Map<String, SocialLoginApiService> socialLoginApiServices) {
        this.socialLoginApiServices = socialLoginApiServices;
    }

    //  MemberType(KAKAO,NAVER,GOOGLE...)에 따라서 구현체를 주입
    public static SocialLoginApiService getSocialLoginApiService(MemberType memberType){

        String socialLoginApiServiceBeanName = "";

        if(MemberType.KAKAO.equals(memberType)){
            socialLoginApiServiceBeanName = "KakaoLoginApiServiceImpl";
        }else if(MemberType.NAVER.equals(memberType)){
            socialLoginApiServiceBeanName = "NaverLoginApiServiceImpl";
        }else if(MemberType.GOOGLE.equals(memberType)){
            socialLoginApiServiceBeanName = "GoogleLoginApiServiceImpl";
        }
        return socialLoginApiServices.get(socialLoginApiServiceBeanName);
    }
}
