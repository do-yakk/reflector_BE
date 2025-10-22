package com.doyak.reflector.converter;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.doyak.reflector.domain.Post;
import com.doyak.reflector.domain.User;
import com.doyak.reflector.dto.request.PostRequest;
import com.doyak.reflector.dto.response.BlockResponse;
import com.doyak.reflector.dto.response.PostResponse;

@Component
public class PostConverter {
	
	@Autowired
	private BlockConverter blockConverter;
	
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // 생성
    public Post toEntity(PostRequest.PostCommand command, User user) {
        return Post.builder()
        		.site(command.getSite())
        		.level(command.getLevel())
        		.title(command.getTitle())
        		.content(command.getContent())
        		.input(command.getInput())
        		.output(command.getOutput())
        		.limitTime(command.getLimitTime())
        		.limitMem(command.getLimitMem())
        		.user(user)
        		.build();
    }
    
    // 단일 
    public PostResponse.PostInfo toResponse(Post post) {
    	List<BlockResponse> blockResponses = post.getBlocks() == null 
    	        ? List.of() 
    	        : blockConverter.toResponseList(post.getBlocks());
        return PostResponse.PostInfo.builder()
        			.postId(post.getPostId())
        			.site(post.getSite())
        			.level(post.getLevel().getDisplayName())
        			.title(post.getTitle())
        			.content(post.getContent())
        			.input(post.getInput())
        			.output(post.getOutput())
        			.limitTime(post.getLimitTime())
        			.limitMem(post.getLimitMem())
        			.author(post.getUser().getEmail())
        			.createdAt(post.getCreatedAt().format(FORMATTER))
        			.updatedAt(post.getUpdatedAt().format(FORMATTER))
        			.blocks(blockResponses)
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
				.level(post.getLevel().getDisplayName())
				.site(post.getSite())
				.build();
    }
}