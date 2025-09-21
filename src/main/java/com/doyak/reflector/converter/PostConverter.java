package com.doyak.reflector.converter;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.doyak.reflector.domain.Post;
import com.doyak.reflector.domain.User;
import com.doyak.reflector.dto.request.PostRequest;
import com.doyak.reflector.dto.response.PostResponse;

@Component
public class PostConverter {

    // 생성
    public Post toEntity(PostRequest.PostCommand command, User user) {
        return Post.builder()
        		.site(command.getSite())
        		.level(command.getLevel())
        		.title(command.getTitle())
        		.content(command.getContent())
        		.user(user)
        		.build();
    }

    // 단일 
    public PostResponse.PostInfo toResponse(Post post) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return PostResponse.PostInfo.builder()
        			.postId(post.getPostId())
        			.site(post.getSite())
        			.level(post.getLevel())
        			.title(post.getTitle())
        			.content(post.getContent())
        			.author(post.getUser().getEmail())
        			.createdAt(post.getCreatedAt().format(formatter))
        			.updatedAt(post.getUpdatedAt().format(formatter))
        			.build();
    }

    // 리스트 
    public List<PostResponse.PostOverview> toResponseList(List<Post> posts) {    	
    	return posts.stream()
    		.map(this::toOverview)
    		.collect(Collectors.toList());
    }
    
    public Page<PostResponse.PostOverview> toResponsePage(Page<Post> posts) {
    	return posts.map(this::toOverview);
    }
    
    private PostResponse.PostOverview toOverview(Post post) {
    	return PostResponse.PostOverview.builder()
    			.postId(post.getPostId())
				.title(post.getTitle())
				.level(post.getLevel())
				.site(post.getSite())
				.build();
    }
}