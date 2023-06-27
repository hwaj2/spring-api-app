package com.app.global.resolver.memberinfo;


import com.app.domain.member.constant.Role;
import lombok.Builder;
import lombok.Getter;

// 토큰안에 있는 유저 정보를 전달하는 dto
@Getter @Builder
public class MemberInfoDto {

    private Long memberId;
    private Role role;

}
