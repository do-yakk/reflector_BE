package com.doyak.reflector.service;

import java.util.Collections;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.doyak.reflector.domain.User;
import com.doyak.reflector.dto.UserDetailsDto;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
	
	private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String userEmail) {
        Optional<User> userOpt = userService.login(userEmail);
        
        return userOpt
        		.map(u -> new UserDetailsDto(u, Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"))))
        		.orElseThrow(() -> {
        			if (userEmail == null || userEmail.equals("")) {
        				return new AuthenticationServiceException("User Not found.");
        			} else {
        				return new BadCredentialsException("Invalid password.");
        			}
        		});
   
    }
}