package com.doyak.reflector.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.doyak.reflector.converter.PostConverter;
import com.doyak.reflector.domain.Block;
import com.doyak.reflector.domain.Post;
import com.doyak.reflector.domain.User;
import com.doyak.reflector.domain.enums.SortType;
import com.doyak.reflector.dto.request.PostRequest;
import com.doyak.reflector.dto.response.PostResponse;
import com.doyak.reflector.payload.code.status.ErrorStatus;
import com.doyak.reflector.payload.exception.handler.PostHandler;
import com.doyak.reflector.repository.HashtagRepository;
import com.doyak.reflector.repository.PostRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {
    
    private final PostRepository postRepository;
    private final PostConverter postConverter;
	private final HashtagRepository hashtagRepository;
	private final BlockService blockService;

    @Transactional
    public PostResponse.PostInfo createPost(User user, PostRequest.PostCommand command) {
        Post post = postConverter.toEntity(command, user);
        Post saved = postRepository.save(post);
        return postConverter.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public PostResponse.PostInfo getPost(User user, Long postId) {
        Post post = findPostWithBlocks(user, postId);
        return postConverter.toResponse(post);
    }

    @Transactional
    public PostResponse.PostInfo updatePost(User user, Long postId, PostRequest.PostCommand command) {
        Post post = findPostById(user, postId);
        post.update(command.getSite(), command.getLevel(), command.getTitle(), command.getContent(), 
        		command.getInput(), command.getOutput(), command.getLimitTime(), command.getLimitMem());
        return postConverter.toResponse(post);
    }

    @Transactional
    public void deletePost(User user, Long postId) {
    	Post post = findPostById(user, postId);
    	for (Block block : post.getBlocks()) {
    		blockService.deleteBlock(post.getPostId(), block.getBlockId(), user);
    	}
        postRepository.deleteById(postId);
    }

    private Post findPostById(User user, Long postId) {
    	Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostHandler(ErrorStatus.POST_NOT_FOUND));
    	if (!post.getUser().getId().equals(user.getId())) {
    		throw new PostHandler(ErrorStatus.POST_FORBIDDEN);
    	}
        return post;
    }
    
    @Transactional(readOnly = true)
    public Page<PostResponse.PostOverview> getSortedPosts(User user, SortType sort, Sort.Direction direction, int page, int size, String hash) {
    	PageRequest pageRequest = PageRequest.of(page, size, Sort.by(direction, sort.getField()));
    	Page<Post> posts;
    	if (hash == null) {
    		posts = postRepository.findAllByUser(user, pageRequest);
    	} else {
    		posts = postRepository.findByUserAndHashtag(user, hash, pageRequest);
    	}
    	
    	if (posts.isEmpty()) {
    		throw new PostHandler(ErrorStatus.POST_NOT_FOUND);
    	}
    	
    	if (page >= posts.getTotalPages() && posts.getTotalPages() > 0) {
            throw new PostHandler(ErrorStatus.PAGE_OUT_OF_RANGE);
        }
    	
    	return postConverter.toResponsePage(posts);
    }
    
    public List<String> getAllHashtags(User user) {
    	return hashtagRepository.findAllHashStringsByUserId(user);
    }
   
    private Post findPostWithBlocks(User user, Long postId) {
    	Post post = postRepository.findByIdWithBlocks(postId)
    			.orElseThrow(() -> new PostHandler(ErrorStatus.POST_NOT_FOUND));
    	if (!post.getUser().getId().equals(user.getId())) {
    		throw new PostHandler(ErrorStatus.POST_FORBIDDEN);
    	}
      return post;
    }
}
