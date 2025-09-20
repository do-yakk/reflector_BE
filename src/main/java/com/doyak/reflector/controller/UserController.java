package com.doyak.reflector.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.doyak.reflector.dto.request.UserRequest;
import com.doyak.reflector.dto.response.UserResponse;
import com.doyak.reflector.payload.ApiResponse;
import com.doyak.reflector.service.EmailService;
import com.doyak.reflector.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {
	
	private final UserService userService;
	
	private final EmailService emailService;
	
	@PostMapping("/sign-up")
	@Operation(summary = "회원가입", description = "회원가입에 사용할 이메일과 비밀번호를 입력해주세요.")
	public ApiResponse<UserResponse.UserLoginResponseDTO> signup(@Valid @RequestBody UserRequest.UserSignUpDTO request) {
		UserResponse.UserLoginResponseDTO response = userService.signup(request);
		return ApiResponse.onSuccess(response);
	}
	
	@PostMapping("/login")
	@Operation(summary = "로그인", description = "로그인할 유저의 이메일과 비밀번호를 입력해주세요.")
	public ApiResponse<UserResponse.UserLoginResponseDTO> login(@Valid @RequestBody UserRequest.UserLoginDTO request) {
		UserResponse.UserLoginResponseDTO response = userService.login(request);
		return ApiResponse.onSuccess(response);
	}
	
	@PostMapping("/emails")
	@Operation(summary = "이메일 중복 확인", description = "회원가입 할 이메일을 입력해주세요.")
	public ApiResponse<?> checkEmail(@Valid @RequestBody UserRequest.UserEmailDTO request) {
		userService.checkEmail(request);
		return ApiResponse.onSuccess(null);
	}
	
	@PostMapping("/verification/emails")
	@Operation(summary = "이메일 인증번호 발송", description = "인증번호를 발송할 이메일을 입력해주세요.")
	public ApiResponse<?> sendCode(@Valid @RequestBody UserRequest.UserEmailDTO request) {
		emailService.sendEmail(request);
		return ApiResponse.onSuccess(null);
	}
	
	@PostMapping("/verification/emails/verify")
	@Operation(summary = "이메일 인증번호 코드 인증", description = "인증번호를 입력해주세요.")
	public ApiResponse<?> verifyCode(@Valid @RequestBody UserRequest.EmailCodeDTO request) {
		emailService.verifyEmailCode(request);
		return ApiResponse.onSuccess(null);
	}
	
}