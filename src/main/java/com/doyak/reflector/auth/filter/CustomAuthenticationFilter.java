package com.doyak.reflector.auth.filter;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.doyak.reflector.dto.request.UserRequest;

@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
		super.setAuthenticationManager(authenticationManager);
	}
	
	// 지정된 url로 폼 전송 시 파라미터 정보를 가져온다. 파라미터 정보에 따른 토큰 리턴 
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		UsernamePasswordAuthenticationToken authRequest;
		try {
			authRequest = getAuthRequest(request);
			setDetails(request, authRequest);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return this.getAuthenticationManager().authenticate(authRequest);
	}
	
	private UsernamePasswordAuthenticationToken getAuthRequest(HttpServletRequest request) throws Exception {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
            UserRequest.UserLoginDTO user = objectMapper.readValue(request.getInputStream(), UserRequest.UserLoginDTO.class);
            log.debug("1.CustomAuthenticationFilter :: userId:" + user.getEmail() + " userPw:" + user.getPassword());

            return new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
		} catch (UsernameNotFoundException ae) {
            throw new UsernameNotFoundException(ae.getMessage());
        } catch (Exception e) {
            throw new Exception(e.getMessage(), e.getCause());
        }
	}
}