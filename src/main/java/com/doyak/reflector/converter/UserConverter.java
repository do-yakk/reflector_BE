package com.doyak.reflector.converter;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

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
	
	public static UserResponse.UserUpdateDTO toUpdateResponse(User user) {
		return UserResponse.UserUpdateDTO.builder()
				.email(user.getEmail())
				.build();
	}
	
	public static List<UserResponse.UserTrackerDTO> toTrackerResponse(Map<LocalDate, Long> logs) {
		return logs.entrySet().stream()
		        .map(entry -> UserResponse.UserTrackerDTO.builder()
		                .date(entry.getKey())
		                .count(entry.getValue())
		                .build())
		        .sorted(Comparator.comparing(UserResponse.UserTrackerDTO::getDate))
		        .toList();
	}
	
}