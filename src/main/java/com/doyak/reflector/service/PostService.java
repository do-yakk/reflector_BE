package com.doyak.reflector.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.doyak.reflector.converter.PostConverter;
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
    private final PostConverter postConverter;

    @Transactional
    public PostResponse createPost(User user, PostRequest request) {
        Post post = postConverter.toEntity(request, user);
        Post saved = postRepository.save(post);
        return postConverter.toResponse(saved);
    }

    public PostResponse getPost(Long postId) {
        Post post = findPostById(postId);
        return postConverter.toResponse(post);
    }

    @Transactional
    public PostResponse updatePost(Long postId, PostRequest request) {
        Post post = findPostById(postId);
        postConverter.updateEntity(post, request);
        Post updated = postRepository.save(post);
        return postConverter.toResponse(updated);
    }

    @Transactional
    public Long deletePost(Long postId) {
        Post post = findPostById(postId);
        postRepository.delete(post);
        return postId;
    }

    public List<PostResponse> getAllPostsByUser(User user) {
        List<Post> posts = postRepository.findAllByUserOrderByCreatedAtDesc(user);
        return postConverter.toResponseList(posts);
    }

    private Post findPostById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.POST_NOT_FOUND));
    }
}
