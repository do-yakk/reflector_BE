package com.doyak.reflector.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.doyak.reflector.domain.User;
import com.doyak.reflector.dto.request.UserRequest;
import com.doyak.reflector.dto.response.UserResponse;
import com.doyak.reflector.payload.ApiResponse;
import com.doyak.reflector.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {
	
	private final UserService userService;
	
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
	
	@PutMapping
	@Operation(summary = "회원 정보 수정", description = "변경할 이메일/비밀번호를 입력해주세요.")
	public ApiResponse<UserResponse.UserUpdateDTO> update(@AuthenticationPrincipal User user, 
														  @RequestBody UserRequest.UserUpdateDTO request) {
		UserResponse.UserUpdateDTO response = userService.update(user, request);
		return ApiResponse.onSuccess(response);
	}

}