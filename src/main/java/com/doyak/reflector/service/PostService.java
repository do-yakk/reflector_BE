package com.doyak.reflector.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.doyak.reflector.converter.PostConverter;
import com.doyak.reflector.domain.Post;
import com.doyak.reflector.domain.User;
import com.doyak.reflector.dto.request.PostRequest;
import com.doyak.reflector.dto.response.PostResponse;
import com.doyak.reflector.payload.code.status.ErrorStatus;
import com.doyak.reflector.payload.exception.GeneralException;
import com.doyak.reflector.repository.PostRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {
    
    private final PostRepository postRepository;
    private final PostConverter postConverter;

    @Transactional
    public PostResponse.PostInfo createPost(User user, PostRequest.PostCommand command) {
        Post post = postConverter.toEntity(command, user);
        Post saved = postRepository.save(post);
        return postConverter.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public PostResponse.PostInfo getPost(Long postId) {
        Post post = findPostById(postId);
        return postConverter.toResponse(post);
    }

    @Transactional
    public PostResponse.PostInfo updatePost(Long postId, PostRequest.PostCommand command) {
        Post post = findPostById(postId);
        post.update(command.getSite(), command.getLevel(), command.getTitle(), command.getContent());
        return postConverter.toResponse(post);
    }

    @Transactional
    public void deletePost(Long postId) {
        Post post = findPostById(postId);
        postRepository.delete(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponse.PostInfo> getAllPostsByUser(User user) {
        List<Post> posts = postRepository.findAllByUserOrderByCreatedAtDesc(user);
        return postConverter.toResponseList(posts);
    }

    private Post findPostById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.POST_NOT_FOUND));
    }
}
