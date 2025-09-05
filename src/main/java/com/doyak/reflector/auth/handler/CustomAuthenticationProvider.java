package com.doyak.reflector.auth.handler;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.doyak.reflector.dto.UserDetailsDto;

import jakarta.annotation.Resource;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {
	
	@Resource
    private UserDetailsService userDetailsService;

    @NonNull
    private BCryptPasswordEncoder passwordEncoder;
    
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    	log.debug("2.CustomAuthenticationProvider");
    	
    	UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
    	
    	// 토큰으로부터 아이디와 비밀번호 조회 
    	String userEmail = token.getName();
        String userPw = (String) token.getCredentials();
        
        // DB로부터 사용자 조회 
        UserDetailsDto userDetailsDto = (UserDetailsDto) userDetailsService.loadUserByUsername(userEmail);
        
        if (!userDetailsDto.getPassword().equalsIgnoreCase(userPw)) {
            throw new BadCredentialsException(userDetailsDto.getUsername() + " Invalid password");
        }
        
        return new UsernamePasswordAuthenticationToken(userDetailsDto, userPw, userDetailsDto.getAuthorities());
    	
    }
    
    // 해당 provider가 어떤 타입의 인증 객체를 처리할 수 있는지 명시하는 역할. 스프링 시큐리티가 인증 시도 시 이 인증 요청을 처리할 수 있는 provider rhffksoa 
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}