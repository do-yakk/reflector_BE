package com.doyak.reflector.service;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.doyak.reflector.auth.JwtUtil;
import com.doyak.reflector.converter.UserConverter;
import com.doyak.reflector.domain.User;
import com.doyak.reflector.repository.UserRepository;
import com.doyak.reflector.dto.request.UserRequest;
import com.doyak.reflector.dto.response.UserResponse;
import com.doyak.reflector.payload.code.status.ErrorStatus;
import com.doyak.reflector.payload.exception.handler.UserHandler;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	
	private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    
    @Transactional
    public UserResponse.UserLoginResponseDTO signup(UserRequest.UserSignUpDTO request) {
    	if (userRepository.findByEmail(request.getEmail()).isPresent())
    		throw new UserHandler(ErrorStatus.USER_ALREADY_EXIST);
    	
    	String encodedPassword = passwordEncoder.encode(request.getPassword());
    	
    	User newUser = UserConverter.toUser(request, encodedPassword);
        User savedUser = userRepository.save(newUser);
    	
    	String accessToken = jwtUtil.createAccessToken(savedUser);
    	
    	return UserConverter.toLoginResponse(savedUser, accessToken);
    }
    
    @Transactional
	public UserResponse.UserLoginResponseDTO login(UserRequest.UserLoginDTO request) {
		User user = userRepository.findByEmail(request.getEmail())
				.orElseThrow(() -> new UserHandler(ErrorStatus.USER_NOT_FOUND));
		
		if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UserHandler(ErrorStatus.INVALID_PASSWORD);
        }
		
		String accessToken = jwtUtil.createAccessToken(user);
		
		return UserConverter.toLoginResponse(user, accessToken);
	}
    

}