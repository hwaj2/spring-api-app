package com.app.global.jwt.service;

import com.app.domain.member.constant.Role;
import com.app.global.error.ErrorCode;
import com.app.global.error.exception.AuthenticationException;
import com.app.global.jwt.constant.GrantType;
import com.app.global.jwt.constant.TokenType;
import com.app.global.jwt.dto.JwtTokenDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Date;



@Slf4j
@RequiredArgsConstructor
public class TokenManager {

    private final String accessTokenExpirationTime; //액세스 토큰 만료시간
    private final String refreshTokenExpirationTime; //리프레시 토큰 만료시간
    private final String tokenSecret; // 토큰생성시 사용할 시크릿키


    // [service] 서비스 jwt 토큰Dto 발급
    public JwtTokenDto createJwtTokenDto(Long memberId, Role role) {

        Date accessTokenExpireTime = createAccessTokenExpireTime();     // 엑세스 토큰 만료시간
        Date refreshTokenExpireTime = createRefreshTokenExpireTime();   // 리프레쉬 토큰 만료시간

        String accessToken = createAccessToken(memberId, role, accessTokenExpireTime); // 엑세스 토큰 생성(발급)
        String refreshToken = createRefreshToken(memberId, refreshTokenExpireTime);    // 리프레쉬 토큰 생성(발급)

        return JwtTokenDto.builder()
                .grantType(GrantType.BEARER.getType())
                .accessToken(accessToken)
                .accessTokenExpireTime(accessTokenExpireTime)
                .refreshToken(refreshToken)
                .refreshTokenExpireTime(refreshTokenExpireTime)
                .build();
    }


    public Date createAccessTokenExpireTime() {
        return new Date(System.currentTimeMillis() + Long.parseLong(accessTokenExpirationTime)); // 현재시간 + 15분
    }
    public Date createRefreshTokenExpireTime() {
        return new Date(System.currentTimeMillis() + Long.parseLong(refreshTokenExpirationTime)); // 현재시간 + 2주
    }



    // 액세스 토큰 생성
    public String createAccessToken(Long memberId, Role role, Date expirationTime) {
        String accessToken = Jwts.builder()
                .setSubject(TokenType.ACCESS.name())    // 토큰 제목
                .setIssuedAt(new Date())                // 토큰 발급 시간
                .setExpiration(expirationTime)          // 토큰 만료 시간
                .claim("memberId", memberId)      // 회원 아이디
                .claim("role", role)              // 유저 role
                .signWith(SignatureAlgorithm.HS512, tokenSecret.getBytes(StandardCharsets.UTF_8)) // 토큰 생성시 사용할 알고리즘 지정
                .setHeaderParam("typ", "JWT") //토큰타입
                .compact();
        return accessToken;
    }

    // 리프레쉬 토큰 생성
    public String createRefreshToken(Long memberId, Date expirationTime) {
        String refreshToken = Jwts.builder()
                .setSubject(TokenType.REFRESH.name())   // 토큰 제목
                .setIssuedAt(new Date())                // 토큰 발급 시간
                .setExpiration(expirationTime)          // 토큰 만료 시간
                .claim("memberId", memberId)      // 회원 아이디
                .signWith(SignatureAlgorithm.HS512, tokenSecret.getBytes(StandardCharsets.UTF_8))
                .setHeaderParam("typ", "JWT")
                .compact();
        return refreshToken;
    }


    // [service] 서비스 토큰 검증
    public void validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(tokenSecret.getBytes(StandardCharsets.UTF_8)) // 시크릿키를 이용해 서명검증
                    .parseClaimsJws(token);
        } catch (ExpiredJwtException e) {
            log.info("token 만료", e);
            throw new AuthenticationException(ErrorCode.TOKEN_EXPIRED);
        } catch (Exception e) {
            log.info("유효하지 않은 token", e);
            throw new AuthenticationException(ErrorCode.NOT_VALID_TOKEN);
        }
    }


    // [service] 토큰정보를 서버에서 사용하기 위해 payload안에 있는 claim 정보 가져오기
    public Claims getTokenClaims(String token) {

        Claims claims;

        try {
            claims = Jwts.parser().setSigningKey(tokenSecret.getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(token).getBody();
        } catch (Exception e) {
            log.info("유효하지 않은 token", e);
            throw new AuthenticationException(ErrorCode.NOT_VALID_TOKEN);
        }
        return claims;
    }

}
