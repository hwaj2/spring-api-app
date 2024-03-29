package com.app.global.util;

import com.app.domain.member.constant.MemberType;
import com.app.global.error.ErrorCode;
import com.app.global.error.exception.AuthenticationException;
import com.app.global.error.exception.BusinessException;
import com.app.global.jwt.constant.GrantType;
import org.springframework.util.StringUtils;

public class AuthorizationHeaderUtils {

    // 헤더값 검증
    public static void validationAuthorization(String authorizationHeader){
        // 1. authorizationHeader 필수체크
        if(!StringUtils.hasText(authorizationHeader)){
            throw new AuthenticationException(ErrorCode.NOT_EXISTS_AUTHORIZATION);
        }
        // 2. authorizationHeader Bearer 체크
        String[] authorizations = authorizationHeader.split(" ");
        if(authorizations.length < 2 || (!GrantType.BEARER.getType().equals(authorizations[0]))){
            throw new AuthenticationException(ErrorCode.NOT_VALID_BEARER_GRANT_TYPE);
        }
    }

}
