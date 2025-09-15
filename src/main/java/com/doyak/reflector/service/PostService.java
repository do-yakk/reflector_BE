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
    public PostResponse.PostInfo getPost(User user, Long postId) {
        Post post = findPostById(user, postId);
        return postConverter.toResponse(post);
    }

    @Transactional
    public PostResponse.PostInfo updatePost(User user, Long postId, PostRequest.PostCommand command) {
        Post post = findPostById(user, postId);
        post.update(command.getSite(), command.getLevel(), command.getTitle(), command.getContent());
        return postConverter.toResponse(post);
    }

    @Transactional
    public void deletePost(User user, Long postId) {
        Post post = findPostById(user, postId);
        postRepository.delete(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponse.PostOverview> getAllPostsByUser(User user) {
        List<Post> posts = postRepository.findAllByUserOrderByCreatedAtDesc(user);
        return postConverter.toResponseList(posts);
    }

    private Post findPostById(User user, Long postId) {
    	Post post = postRepository.findById(postId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.POST_NOT_FOUND));
    	if (!post.getUser().getId().equals(user.getId())) {
    		throw new GeneralException(ErrorStatus.POST_FORBIDDEN);
    	}
        return post;
    }
}
