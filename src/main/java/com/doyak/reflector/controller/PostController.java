package com.doyak.reflector.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.doyak.reflector.domain.User;
import com.doyak.reflector.domain.enums.SortType;
import com.doyak.reflector.dto.request.PostRequest;
import com.doyak.reflector.dto.response.PostResponse;
import com.doyak.reflector.payload.ApiResponse;
import com.doyak.reflector.service.PostService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/posts")
@RequiredArgsConstructor
public class PostController {
	
	private final PostService postService;
	
	@PostMapping
	@Operation(summary = "포스트 생성", description = "포스트 내용을 입력해주세요. 단, 로그인 상태여야 합니다.")
	public ApiResponse<PostResponse.PostInfo> createPost(@AuthenticationPrincipal User user, 
												@RequestBody PostRequest.PostCommand command) {
		PostResponse.PostInfo info = postService.createPost(user, command);
		return ApiResponse.onSuccess(info);
	}
	
	@GetMapping("/{postId}")
	@Operation(summary = "포스트 읽기", description = "읽어오길 원하는 포스트 번호를 입력해주세요.")
	public ApiResponse<PostResponse.PostInfo> getPost(@AuthenticationPrincipal User user, 
														@PathVariable("postId") Long postId) {
		PostResponse.PostInfo info =  postService.getPost(user, postId);
		return ApiResponse.onSuccess(info);
	}
	
	@PutMapping("/{postId}")
	@Operation(summary = "포스트 수정", description = "수정할 포스트 내용을 입력해주세요.")
	public ApiResponse<PostResponse.PostInfo> updatePost(@AuthenticationPrincipal User user, 
														@PathVariable("postId") Long postId,
														@RequestBody PostRequest.PostCommand command) {
		PostResponse.PostInfo info =  postService.updatePost(user, postId, command);
		return ApiResponse.onSuccess(info);
	}
	
	@DeleteMapping("/{postId}")
	@Operation(summary = "포스트 삭제", description = "삭제할 포스트 번호를 입력해주세요.")
	public ApiResponse<Void> deletePost(@AuthenticationPrincipal User user, 
										@PathVariable("postId") Long postId) {
		postService.deletePost(user, postId);
		return ApiResponse.onSuccess(null);
	}
	
	@GetMapping("/hashtags")
	@Operation(summary = "해시태그 목록 조회")
	public ApiResponse<List<String>> getAllHashtags(@AuthenticationPrincipal User user) {
		List<String> hashtags = postService.getAllHashtags(user);
		return ApiResponse.onSuccess(hashtags);
	}
	
	@GetMapping("/sorted")
	@Operation(summary = "포스트 정렬")
	public ApiResponse<Page<PostResponse.PostOverview>> getSortedPosts(@AuthenticationPrincipal User user,
																 @RequestParam(name = "sort", defaultValue = "CREATED_AT")
															 	 @Parameter(description = "정렬 기준 enum 입력 (CREATED_AT, UPDATED_AT)") SortType sort,
																 @RequestParam(name = "direction", defaultValue = "DESC") 
																 @Parameter(description = "정렬 방향 입력 (ASC, DESC)") Sort.Direction direction,
														         @RequestParam(name = "page", defaultValue = "0") 
																 @Parameter(description = "페이지 번호 입력") int page,
														         @RequestParam(name = "size", defaultValue = "10") 
																 @Parameter(description = "페이지 크기 입력") int size,
																 @RequestParam(name = "hashtag", required = false)
																 @Parameter(description = "해시태그 입력") String hashtag) {
		Page<PostResponse.PostOverview> response = postService.getSortedPosts(user, sort, direction, page, size, hashtag);
		return ApiResponse.onSuccess(response);
		
	}

}
