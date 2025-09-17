package com.doyak.reflector.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserRequest {
	
	@Builder
	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	@AllArgsConstructor
	public static class UserLoginDTO {
		
		@NotBlank(message = "이메일을 입력해주세요.")
		String email;
		
		@NotBlank(message = "비밀번호를 입력해주세요.")
		String password;
		
	}
	
	@Builder
	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	@AllArgsConstructor
	public static class UserSignUpDTO {
		
		@NotBlank(message = "이메일은 필수 입력 값입니다.")
		String email;
		
		@NotBlank(message = "비밀번호는 필수 입력 값입니다.")
		String password;
	}
	
	@Builder
	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	@AllArgsConstructor
	public static class UserUpdateDTO {
		
		String email;
		
		String password;
	}
	
}