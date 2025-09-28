package com.doyak.reflector.dto.response;

import java.time.LocalDate;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserResponse {
	
	@Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Getter
    public static class UserSignupResponseDTO {
		private String email;
	}
	
	@Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Getter
    public static class UserLoginResponseDTO {
		private String email;
		private String accessToken;
	}
	
	@Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Getter
    public static class UserLoginWrapperDTO {
		private UserLoginResponseDTO loginResponse;
		private String refreshToken;
	}
	
	@Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Getter
    public static class UserUpdateDTO {
		private String email;
	}
	
	@Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Getter
    public static class UserTrackerDTO {
		private LocalDate date;
		private Long count;
	}
	
}