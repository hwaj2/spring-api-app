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


    // 회원 타입에 따른 SocialLoginApiService 구현체 반환
    public static SocialLoginApiService getSocialLoginApiService(MemberType memberType){

        String socialLoginApiServiceBeanName = "";

        if(MemberType.KAKAO.equals(memberType)){
            socialLoginApiServiceBeanName = "kakaoLoginApiServiceImpl";
        }else if(MemberType.NAVER.equals(memberType)){
            socialLoginApiServiceBeanName = "naverLoginApiServiceImpl";
        }else if(MemberType.GOOGLE.equals(memberType)){
            socialLoginApiServiceBeanName = "googleLoginApiServiceImpl";
        }
        return socialLoginApiServices.get(socialLoginApiServiceBeanName);
    }
}
