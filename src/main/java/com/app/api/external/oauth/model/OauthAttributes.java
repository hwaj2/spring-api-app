package com.app.api.external.oauth.model;


import com.app.domain.member.constant.MemberType;
import com.app.domain.member.constant.Role;
import com.app.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/* 소셜 서비스마다 반환되는 객체값의 형태가 다른데, 서비스에 맞게 하나의 통일된형태로 전달하기 위해서 사용*/
@Builder @Getter
public class OauthAttributes {

    private String name;  // 이름
    private String email; // 이메일
    private String profile; // 프로필사진
    private MemberType memberType; // 회원타입(카카오,네이버,구글등등)


    // 회원가입시, 사용될 Member엔티티
    public Member toMemberEntity(MemberType memberType, Role role){
        return Member.builder()
                .memberName(name)
                .email(email)
                .memberType(memberType)
                .profile(profile)
                .role(role)
                .build();
    }

}
