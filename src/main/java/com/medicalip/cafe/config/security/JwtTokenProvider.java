package com.medicalip.cafe.config.security;


import java.util.Base64;
import java.util.Collection;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider { // JWT 토큰을 생성 및 검증 모듈

    @Value("spring.jwt.secret")
    private String secretKey;

    private final long ACCESS_TOKEN_VALID_TIME = 60 * 60; //1시간
    private final long REFRESH_TOKEN_VALID_TIME = 60 * 60 * 24 * 60; // 1달

    private final UserDetailsService userDetailsService;
    private final ExpiredRefreshTokenService expiredRefreshTokenService;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    // Jwt 토큰 생성
    public String createToken(String userPk, Collection<? extends GrantedAuthority> collection) {
        Claims claims = Jwts.claims().setSubject(userPk);
        claims.put("roles", collection);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims) // 데이터
                .setIssuedAt(now) // 토큰 발행일자
                .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_VALID_TIME)) // 토큰 유효시간 설정
                .signWith(SignatureAlgorithm.HS256, secretKey) // 암호화 알고리즘, 암호키
                .compact();
    }
    
    // Refresh 토큰 생성
    public String createRefreshToken(String userPk, Collection<? extends GrantedAuthority> collection) {
    	Claims claims = Jwts.claims().setSubject(userPk);
    	claims.put("roles", collection);
    	Date now = new Date();
    	return Jwts.builder()
    			.setClaims(claims) // 데이터
    			.setIssuedAt(now) // 토큰 발행일자
    			.setExpiration(new Date(now.getTime() + REFRESH_TOKEN_VALID_TIME)) // 토큰 유효시간 설정
    			.signWith(SignatureAlgorithm.HS256, secretKey) // 암호화 알고리즘, 암호키
    			.compact();
    }

    // Jwt 토큰으로 인증 정보 조회
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // Jwt 토큰에서 회원 구별 정보 추출
    public String getUserPk(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveAccessToken(HttpServletRequest request) {
        String token = request.getHeader("access-token");
        return token;
    }

    public String resolveRefreshToken(HttpServletRequest request) {
        String token = null;
        Cookie cookie = WebUtils.getCookie(request, "refresh-token");
        if (cookie != null)
            token = cookie.getValue();
        return token;
    }

    // Jwt 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean validateRefreshToken(String jwtToken) {
        if(expiredRefreshTokenService.isExpiredToken(jwtToken)) {
            return false;
        }

        return validateToken(jwtToken);
    }
}
