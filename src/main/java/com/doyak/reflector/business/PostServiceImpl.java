package com.doyak.reflector.business;

import com.doyak.reflector.business.repository.PostService;
import com.doyak.reflector.domain.Code;
import com.doyak.reflector.domain.Post;
import com.doyak.reflector.infrastructure.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;


@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public Post create(String userId, Integer beakId, String title, String content) {
        Post post = new Post();
        post.setBaekId(beakId);
        post.setTitle(title);
        post.setContent(content);
        post.setCreated_at(LocalDateTime.now());
        post.setUserId(userId);
        postRepository.save(post);
        return post;
    }

    @Override
    public Post read(String userId, Integer postId) {
        Post post = postRepository.findByUserIdAndPostId(userId, postId);
        return post;
    }

    @Override
    public Post update(String userId, Integer PostId, String newTitle, String newContent) {
        Post post = read(userId, PostId);
        post.update(newTitle, newContent);
        postRepository.save(post);
        return post;
    }

    @Override
    public void delete(String userId, Integer PostId) {
        Post post = read(userId, PostId);
        if (post != null) {
            postRepository.delete(post);
        }
    }

}
