package com.doyak.reflector.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.doyak.reflector.domain.Post;
import com.doyak.reflector.domain.User;
import com.doyak.reflector.dto.request.PostRequest;
import com.doyak.reflector.dto.response.PostResponse;
import com.doyak.reflector.payload.code.status.ErrorStatus;
import com.doyak.reflector.payload.exception.GeneralException;
import com.doyak.reflector.repository.PostRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {
	
	private final PostRepository postRepository;
	
	@Transactional
	public PostResponse createPost(User user, PostRequest request) {
		Post post = new Post(user, request.getSite(), request.getLevel(), request.getTitle(), request.getContent());
		Post saved = postRepository.save(post);
		return toResponse(saved);
	}
	
	public PostResponse getPost(Long postId) {
		Post post = findPostById(postId);
		return toResponse(post);
	}
	
	@Transactional
	public PostResponse updatePost(Long postId, PostRequest request) {
		Post post = findPostById(postId);
		post.update(request.getSite(), request.getLevel(), request.getTitle(), request.getContent());
		Post updated = postRepository.save(post);
		return toResponse(updated);
	}
	
	@Transactional
	public void deletePost(Long postId) {
		Post post = findPostById(postId);
		postRepository.delete(post);
	}
	
	// 특정 유저의 포스트 목록
	public List<PostResponse> gettAllPostsByUsers(User user) {
		List<Post> posts = postRepository.findAllByUserOrderByCreatedAtDesc(user);
		return posts.stream().map(this::toResponse).toList();
	}
	
	
	// 포스트가 존재하는지 찾기 
	private Post findPostById(Long postId) {
		return postRepository.findById(postId)
				.orElseThrow(() -> new GeneralException(ErrorStatus.POST_NOT_FOUND));
	}
	
	// 응답 통일 
	private PostResponse toResponse(Post post) {
		return new PostResponse(post.getPostId(), post.getSite(), post.getLevel(), post.getTitle(), post.getContent());
	}

}
