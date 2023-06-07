package com.app.global.jwt.constant;


import lombok.Getter;

@Getter
public enum GrantType { // 토큰반환시 GrantType 제공

    BEARER("Bearer");

    GrantType(String type){
        this.type = type;
    }

    private String type;
}
