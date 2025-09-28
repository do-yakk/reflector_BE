package com.doyak.reflector.converter;

import java.util.List;
import java.util.stream.Collectors;

import com.doyak.reflector.domain.User;
import com.doyak.reflector.dto.response.UserResponse;
import com.doyak.reflector.dto.response.UserResponse.UserTrackerDTO;
import com.doyak.reflector.dto.request.UserRequest;

public class UserConverter {
	
	public static UserResponse.UserSignupResponseDTO toSignupResponse(User user) {
		return UserResponse.UserSignupResponseDTO.builder()
				.email(user.getEmail())
				.build();
	}
	
	public static UserResponse.UserLoginResponseDTO toLoginResponse(User user, String accessToken, String refreshToken) {
		return UserResponse.UserLoginResponseDTO.builder()
				.email(user.getEmail())
				.accessToken(accessToken)
				.refreshToken(refreshToken)
				.build();
	}
	
	public static User toUser(UserRequest.UserSignUpDTO userDto, String encodedPassword) {
		return User.builder()
				.email(userDto.getEmail())
				.password(encodedPassword)
				.build();
	}
	
	public static UserResponse.UserUpdateDTO toUpdateResponse(User user) {
		return UserResponse.UserUpdateDTO.builder()
				.email(user.getEmail())
				.build();
	}
	
	public static List<UserResponse.UserTrackerDTO> toTrackerResponse(List<Object[]> queryResult) {
		return queryResult.stream()
    			.map(o -> UserTrackerDTO.builder()
    	                .date(((java.sql.Date) o[0]).toLocalDate())
    	                .count(((Long) o[1]))                       
    	                .build()                       
    	        ).collect(Collectors.toList());
	}
	
}