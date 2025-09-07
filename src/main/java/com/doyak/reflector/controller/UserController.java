package com.doyak.reflector.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.doyak.reflector.dto.request.UserRequest;
import com.doyak.reflector.dto.response.UserResponse;
import com.doyak.reflector.payload.ApiResponse;
import com.doyak.reflector.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {
	
	private final UserService userService;
	
	@PostMapping("/sign-up")
	public ApiResponse<UserResponse.UserLoginResponseDTO> signup(@Valid @RequestBody UserRequest.UserSignUpDTO request) {
		UserResponse.UserLoginResponseDTO response = userService.signup(request);
		return ApiResponse.onSuccess(response);
	}
	
	@PostMapping("/login")
	public ApiResponse<UserResponse.UserLoginResponseDTO> login(@Valid @RequestBody UserRequest.UserLoginDTO request) {
		UserResponse.UserLoginResponseDTO response = userService.login(request);
		return ApiResponse.onSuccess(response);
	}
}