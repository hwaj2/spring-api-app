package com.app.domain.member.serivce;


import com.app.domain.member.entity.Member;
import com.app.domain.member.repository.MemberRepository;
import com.app.global.error.ErrorCode;
import com.app.global.error.exception.AuthenticationException;
import com.app.global.error.exception.BusinessException;
import com.app.global.jwt.service.TokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final  MemberRepository memberRepository;

    // 회원가입
    @Transactional
    public Member registerMember(Member member){
        validateDuplicateMember(member);
        return memberRepository.save(member);
    }

    // 이메일 중복 검증
    private void validateDuplicateMember(Member member){
        Optional<Member> optionalMember = memberRepository.findByEmail(member.getEmail());
        if(optionalMember.isPresent()){
            throw new BusinessException(ErrorCode.ALREADY_REGISTERED_MEMBER);
        }
    }



    public Optional<Member> findMemberByEmail(String email){
        return memberRepository.findByEmail(email);
    }


    public Member findMemberByRefreshToken(String refreshToken){
        Member member =  memberRepository.findByRefreshToken(refreshToken)
                .orElseThrow(()-> new AuthenticationException(ErrorCode.REFRESH_TOKEN_NOT_FOUND));

        LocalDateTime tokenExpirationTime = member.getTokenExpirationTime();
        if(tokenExpirationTime.isBefore(LocalDateTime.now())){
            throw new AuthenticationException(ErrorCode.REFRESH_TOKEN_EXPIRED);
        }
        return member;
    }
}
