package com.doyak.reflector.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
		private String email;
		
		@NotBlank(message = "비밀번호를 입력해주세요.")
		private String password;
		
	}
	
	@Builder
	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	@AllArgsConstructor
	public static class UserSignUpDTO {
		
		@NotBlank(message = "이메일은 필수 입력 값입니다.")
		private String email;
		
		@NotBlank(message = "비밀번호는 필수 입력 값입니다.")
		private String password;
	}
	
	@Builder
	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	@AllArgsConstructor
	public static class UserUpdateDTO {
		
		@NotBlank(message = "이메일을 입력해주세요. 변경하지 않을 것이라면 기존의 이메일을 입력해주세요.")
		private String email;
		
		@NotBlank(message = "비밀번호를 입력해주세요. 변경하지 않을 것이라면 기존의 비밀번호를 입력해주세요.")
		private String password;
	}
  
	@Builder
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	@AllArgsConstructor
	@Getter
	public static class UserEmailDTO {
		@NotBlank(message = "이메일은 필수 입력 값입니다.")
		private String email;
	}
	
	@Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Getter
	public static class EmailCodeDTO {
		
		@NotBlank(message = "이메일은 필수 입력 값입니다.")
		private String email;
		
		@Pattern(regexp = "^[0-9]{6}$", message = "인증 코드는 숫자 6자리여야 합니다.")
		private String code;
	}
	
	@Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Getter
	public static class TokenRequestDTO {
		
		@NotBlank
		private String accessToken;
		
		@NotBlank
		private String refreshToken;
	}
	
}