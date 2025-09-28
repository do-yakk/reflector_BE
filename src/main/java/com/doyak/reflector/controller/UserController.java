package com.doyak.reflector.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.doyak.reflector.domain.User;
import com.doyak.reflector.dto.request.UserRequest;
import com.doyak.reflector.dto.response.UserResponse;
import com.doyak.reflector.dto.response.UserResponse.UserSignupResponseDTO;
import com.doyak.reflector.payload.ApiResponse;
import com.doyak.reflector.service.EmailService;
import com.doyak.reflector.service.UserService;
import com.doyak.reflector.util.CookieUtil;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {
	
	private final UserService userService;
	
	private final EmailService emailService;
	
	private final CookieUtil cookieUtil;
	
	@PostMapping("/sign-up")
	@Operation(summary = "회원가입", description = "회원가입에 사용할 이메일과 비밀번호를 입력해주세요.")
	public ApiResponse<UserResponse.UserSignupResponseDTO> signup(@Valid @RequestBody UserRequest.UserSignUpDTO request) {
		UserSignupResponseDTO response = userService.signup(request);
		return ApiResponse.onSuccess(response);
	}
	
	@PostMapping("/login")
	@Operation(summary = "로그인", description = "로그인할 유저의 이메일과 비밀번호를 입력해주세요.")
	public ApiResponse<UserResponse.UserLoginResponseDTO> login(@Valid @RequestBody UserRequest.UserLoginDTO request, 
																HttpServletResponse response) {
		UserResponse.UserLoginWrapperDTO loginWrapper = userService.login(request);
		cookieUtil.addRefreshTokenCookie(response, loginWrapper.getRefreshToken());
		return ApiResponse.onSuccess(loginWrapper.getLoginResponse());
	}
	
	@PutMapping
	@Operation(summary = "회원 정보 수정", description = "변경할 이메일/비밀번호를 입력해주세요. 이메일 변경 시 재로그인이 필요합니다.")
	public ApiResponse<UserResponse.UserUpdateDTO> update(@AuthenticationPrincipal User user, 
														  @Valid @RequestBody UserRequest.UserUpdateDTO request) {
		UserResponse.UserUpdateDTO response = userService.update(user, request);
		return ApiResponse.onSuccess(response);
	}
	
	@DeleteMapping
	@Operation(summary = "회원 탈퇴")
	public ApiResponse<Void> delete(@AuthenticationPrincipal User user) {
		userService.delete(user);
		return ApiResponse.onSuccess(null);
	}

	@PostMapping("/emails")
	@Operation(summary = "이메일 중복 확인", description = "회원가입 할 이메일을 입력해주세요.")
	public ApiResponse<Void> checkEmail(@Valid @RequestBody UserRequest.UserEmailDTO request) {
		userService.checkEmail(request);
		return ApiResponse.onSuccess(null);
	}
	
	@PostMapping("/verification/emails")
	@Operation(summary = "이메일 인증번호 발송", description = "인증번호를 발송할 이메일을 입력해주세요.")
	public ApiResponse<Void> sendCode(@Valid @RequestBody UserRequest.UserEmailDTO request) {
		emailService.sendEmail(request);
		return ApiResponse.onSuccess(null);
	}
	
	@PostMapping("/verification/emails/verify")
	@Operation(summary = "이메일 인증번호 코드 인증", description = "인증번호를 입력해주세요.")
	public ApiResponse<Void> verifyCode(@Valid @RequestBody UserRequest.EmailCodeDTO request) {
		emailService.verifyEmailCode(request);
		return ApiResponse.onSuccess(null);
	}
	
	@GetMapping("/study-tracker/{year}")
	@Operation(summary = "학습 트래커 로그 불러오기", description = "원하는 연도를 입력해주세요.")
	public ApiResponse<List<UserResponse.UserTrackerDTO>> getTrackers(@AuthenticationPrincipal User user, 
																	  @PathVariable("year") Integer year) {
		List<UserResponse.UserTrackerDTO> response = userService.getCalendarDate(user, year);
		return ApiResponse.onSuccess(response);
	}
	
	@PostMapping("/reissue")
	@Operation(summary = "토큰 재발급", description = "로그인할 유저의 이메일과 비밀번호를 입력해주세요.")
	public ApiResponse<UserResponse.UserLoginResponseDTO> reissue(HttpServletRequest request, 
																HttpServletResponse response) {
		String refreshToken = cookieUtil.getRefreshToken(request);
		UserResponse.UserLoginWrapperDTO loginWrapper = userService.reissue(refreshToken);
		cookieUtil.addRefreshTokenCookie(response, loginWrapper.getRefreshToken());
		return ApiResponse.onSuccess(loginWrapper.getLoginResponse());
	}
	
	@DeleteMapping("/logout")
	@Operation(summary = "로그아웃", description = "현재 로그인한 사용자의 쿠키를 삭제합니다.")
	public ApiResponse<Void> logout(@AuthenticationPrincipal User user, HttpServletResponse response) {
		userService.logout(user);
		cookieUtil.deleteRefreshTokenCookie(response);
		return ApiResponse.onSuccess(null);
	}
}