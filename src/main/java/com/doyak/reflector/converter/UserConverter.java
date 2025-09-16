package com.doyak.reflector.converter;

import com.doyak.reflector.domain.User;
import com.doyak.reflector.dto.response.UserResponse;
import com.doyak.reflector.dto.request.UserRequest;

public class UserConverter {
	
	public static UserResponse.UserLoginResponseDTO toLoginResponse(User user, String token) {
		return UserResponse.UserLoginResponseDTO.builder()
				.email(user.getEmail())
				.token(token)
				.build();
	}
	
	public static User toUser(UserRequest.UserSignUpDTO userDto, String encodedPassword) {
		return User.builder()
				.email(userDto.getEmail())
				.password(encodedPassword)
				.build();
	}
	
	public static UserResponse.EmailVerifyDTO toEmailVerifyResponse(Boolean status) {
		return UserResponse.EmailVerifyDTO.builder()
				.status(status)
				.build();
	}
}