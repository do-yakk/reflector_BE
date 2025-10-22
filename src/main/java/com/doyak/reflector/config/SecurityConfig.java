package com.doyak.reflector.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.doyak.reflector.auth.filter.JwtAuthenticationFilter;
import com.doyak.reflector.auth.filter.JwtExceptionHandlerFilter;
import com.doyak.reflector.auth.handler.JwtAccessDeniedHandler;
import com.doyak.reflector.auth.handler.JwtAuthenticationEntryPoint;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	
	
	private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtExceptionHandlerFilter jwtExceptionHandlerFilter;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    
    @Value("${spring.cors.allowed-origins}")
    private String allowedOrigins;
    
    private static final String[] AUTH_WHITELIST = {
            "/v3/api-docs/**",
            "/swagger/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/swagger-resources/**",
            "/css/**",
            "/js/**",
            "/file/**",
            "/images/**",
            "/webjars/**",
            "/configuration/ui",
            "/configuration/security",
            "/h2/**"
    };
	
	
	// HTTP에 대해서 인증과 인가 담당. 필터를 이용하여 인증 방식과 인증 절차에 대해 등록 
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.csrf(auth -> auth.disable())
			.formLogin(auth -> auth.disable())
			.httpBasic((auth) -> auth.disable())
			.sessionManagement((session) -> session
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            .exceptionHandling((exception) -> exception
                    .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                    .accessDeniedHandler(jwtAccessDeniedHandler))
            
			.authorizeHttpRequests(auth -> auth
					.requestMatchers("/api/users/*").permitAll()
					.requestMatchers("/api/users/verification/**").permitAll()
                    .requestMatchers(AUTH_WHITELIST).permitAll()
					.anyRequest().authenticated())
			
			.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(jwtExceptionHandlerFilter, JwtAuthenticationFilter.class);
		return http.build();
	}
	
	@Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173", allowedOrigins)); 
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type")); 
        configuration.setAllowCredentials(true); 

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", configuration); 

        return source;
    }
}