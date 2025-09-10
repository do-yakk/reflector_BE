package com.doyak.reflector.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.doyak.reflector.domain.Post;
import com.doyak.reflector.domain.User;
import com.doyak.reflector.dto.request.PostRequest;
import com.doyak.reflector.dto.response.PostResponse;

@Component
public class PostConverter {

    // 생성
    public Post toEntity(PostRequest request, User user) {
        return new Post(user,
                        request.getSite(),
                        request.getLevel(),
                        request.getTitle(),
                        request.getContent());
    }

    // 업데이트
    public void updateEntity(Post post, PostRequest request) {
        post.update(request.getSite(),
                    request.getLevel(),
                    request.getTitle(),
                    request.getContent());
    }

    // 단일 
    public PostResponse toResponse(Post post) {
        return new PostResponse(post.getPostId(),
                                post.getSite(),
                                post.getLevel(),
                                post.getTitle(),
                                post.getContent());
    }

    // 리스트 
    public List<PostResponse> toResponseList(List<Post> posts) {
        return posts.stream()
                    .map(this::toResponse)
                    .collect(Collectors.toList());
    }
}