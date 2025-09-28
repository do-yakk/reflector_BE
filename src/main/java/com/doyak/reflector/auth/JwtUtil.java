package com.doyak.reflector.auth;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

import com.doyak.reflector.service.UserDetailsServiceImpl;
import com.doyak.reflector.auth.handler.JwtExceptionHandler;
import com.doyak.reflector.domain.User;
import com.doyak.reflector.payload.code.status.ErrorStatus;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.http.HttpServletRequest;

@Slf4j
@Component
public class JwtUtil {
	
	private SecretKey secretKey;
    private final UserDetailsServiceImpl userDetailService;

    public JwtUtil(@Value("${spring.jwt.secret}") String secretKey, UserDetailsServiceImpl userDetailService) {
        this.secretKey = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
        this.userDetailService = userDetailService;
    }
    
    // 토큰 생성 - 추후 만료 시간 추가 예정 
    public String createToken(String category, User user, int expirationMillis) {
        return Jwts.builder()
                .claims(createClaims(user, category))
                .subject(String.valueOf(user.getId()))
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expirationMillis))
                .signWith(secretKey)
               .compact();
    }
    
    // request 헤더에서 토큰 추출 
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
    
    // 유효성 검사
    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
            return true;
        } catch (ExpiredJwtException e) { // 만료된 토큰일 경우 
            throw new JwtExceptionHandler(ErrorStatus.TOKEN_EXPIRED.getMessage(), e);
        } catch (SecurityException | MalformedJwtException e) { // 서명이 잘못되었거나 변조된 토큰일 경우(토큰 서명/형식 오류)
            throw new JwtExceptionHandler(ErrorStatus.WRONG_TYPE_SIGNATURE.getMessage(), e);
        } catch (UnsupportedJwtException e) { // 지원하지 않는 형식의 JWT일 경우 
            throw new JwtExceptionHandler(ErrorStatus.WRONG_TYPE_TOKEN.getMessage(), e);
        } catch (IllegalArgumentException e) { // 토큰이 비어있거나 잘못된 값인 경우 
            throw new JwtExceptionHandler(ErrorStatus.NOT_VALID_TOKEN.getMessage(), e);
        }
    }
    
    private static Map<String, Object> createClaims(User user, String category) {
    	Map<String, Object> claims = new HashMap<>();
    	
        claims.put("category", category);
        claims.put("userId", user.getId());
        claims.put("userEmail", user.getEmail());
        return claims;
    }
    
    public Authentication getAuthentication(String token) throws UsernameNotFoundException {
        UserDetails userDetails = userDetailService.loadUserByUsername(this.getUserEmail(token));
        return new UsernamePasswordAuthenticationToken(userDetails, null, null);
    }
    
    public String getUserEmail(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("userEmail", String.class);
    }
   
	
}