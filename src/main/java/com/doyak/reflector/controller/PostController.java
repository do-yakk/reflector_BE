package com.doyak.reflector.controller;

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
import com.doyak.reflector.dto.request.PostRequest;
import com.doyak.reflector.dto.response.PostResponse;
import com.doyak.reflector.payload.ApiResponse;
import com.doyak.reflector.service.PostService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/posts")
@RequiredArgsConstructor
public class PostController {
	
	private final PostService postService;
	
	@PostMapping()
	@Operation(summary = "포스트 생성", description = "포스트 내용을 입력해주세요. 단, 로그인 상태여야 합니다.")
	public ApiResponse<PostResponse> createPost(@AuthenticationPrincipal User user, 
												@RequestBody PostRequest request) {
		PostResponse response = postService.createPost(user, request);
		return ApiResponse.onSuccess(response);
	}
	
	@GetMapping("/{postId}")
	@Operation(summary = "포스트 읽기", description = "읽어오길 원하는 포스트 번호를 입력해주세요.")
	public ApiResponse<PostResponse> getPost(@PathVariable("postId") Long postId) {
		PostResponse response =  postService.getPost(postId);
		return ApiResponse.onSuccess(response);
	}
	
	@PutMapping("/{postId}")
	@Operation(summary = "포스트 수정", description = "수정할 포스트 내용을 입력해주세요.")
	public ApiResponse<PostResponse> updatePost(@PathVariable("postId") Long postId,
											@RequestBody PostRequest request) {
		PostResponse response =  postService.updatePost(postId, request);
		return ApiResponse.onSuccess(response);
	}
	
	@DeleteMapping("/{postId}")
	@Operation(summary = "포스트 삭제", description = "삭제할 포스트 번호를 입력해주세요.")
	public ApiResponse<Long> deletePost(@PathVariable("postId") Long postId) {
		Long deletedId = postService.deletePost(postId);
		return ApiResponse.onSuccess(deletedId);
	}


}
