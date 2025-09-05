package com.doyak.reflector.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.doyak.reflector.auth.filter.CustomAuthenticationFilter;
import com.doyak.reflector.auth.handler.CustomAuthFailureHandler;
import com.doyak.reflector.auth.handler.CustomAuthSuccessHandler;
import com.doyak.reflector.auth.handler.CustomAuthenticationProvider;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	
	// 정적 자원에 대해 Security 적용하지 않음으로 설정 
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
	}
	
	
	// HTTP에 대해서 인증과 인가 담당. 필터를 이용하여 인증 방식과 인증 절차에 대해 등록 
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.csrf(auth -> auth.disable()) // 서버에 인증 정보 저장하지 않으므로 csrf 비활성화 
			.formLogin(auth -> auth.disable()) // form 기반 로그인 비활성화 - 커스텀으로 구성한 필터 사용 
			.authorizeHttpRequests(auth -> auth.anyRequest().permitAll()) // 토큰을 사용하는 경우 모든 요청에 대해 '인가' 사용 
			.addFilterBefore(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class); // 스프링 시큐리티 필터 로드 - form 인증에 대해 사
			//        http.addFilterBefore(jwtAuthorizationFilter(), BasicAuthenticationFilter.class);
		return http.build();
	}
	
	
	// authenticate의 인증 메서드 제공 인터페이스 
	@Bean
	public AuthenticationManager authenticationManager() {
		return new ProviderManager(customAuthenticationProvider());
	}
	
	// 인증 제공자로 사용자의 이름과 비밀번호 요구 
	@Bean
	public CustomAuthenticationProvider customAuthenticationProvider() {
		return new CustomAuthenticationProvider(bCryptPasswordEncoder());
	}
	
	// BCrypt 인코딩을 통해 비밀번호에 대한 암호화 수행
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	// 커스텀을 수행한 인증 필터. 접근 URL, 데이터 전달 방식 등 인증 과정 및 인증 후 처리에 대한 설정 구성 
	@Bean
	public CustomAuthenticationFilter customAuthenticationFilter() {
		CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManager());
        customAuthenticationFilter.setFilterProcessesUrl("/api/users/login");     // 접근 URL
        customAuthenticationFilter.setAuthenticationSuccessHandler(customLoginSuccessHandler());    // '인증' 성공 시 해당 핸들러로 처리 전가 
        customAuthenticationFilter.setAuthenticationFailureHandler(customLoginFailureHandler());    // '인증' 실패 시 해당 핸들러로 처리 전가 
        customAuthenticationFilter.afterPropertiesSet();
        return customAuthenticationFilter;
	}
	
	@Bean
    public CustomAuthSuccessHandler customLoginSuccessHandler() {
        return new CustomAuthSuccessHandler();
    }
	
	 @Bean
	 public CustomAuthFailureHandler customLoginFailureHandler() {
	    return new CustomAuthFailureHandler();
	 }
	
	
	
	
}