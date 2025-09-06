package com.doyak.reflector.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.doyak.reflector.domain.User;
import com.doyak.reflector.domain.enums.Site;
import com.doyak.reflector.dto.request.PostRequest;
import com.doyak.reflector.dto.response.PostResponse;
import com.doyak.reflector.payload.exception.GeneralException;
import com.doyak.reflector.repository.UserRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
@ActiveProfiles("test")  
@Transactional  
class PostServiceTest {
	
    @Autowired
    private PostService postService;
    
    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        userRepository.save(user);
    }

    @Test
    void createAndGetPost() {
        PostRequest request = new PostRequest(Site.BAEKJOON, 1, "Test Title", "Test Content");

        PostResponse created = postService.createPost(user, request);
        PostResponse fetched = postService.getPost(created.postId());

        assertEquals("Test Title", fetched.title());
        assertEquals("Test Content", fetched.content());
    }

    @Test
    void updatePost() {
        PostRequest request = new PostRequest(Site.PROGRAMMERS, 2, "Original Title", "Original Content");
        PostResponse created = postService.createPost(user, request);

        PostRequest updateRequest = new PostRequest(Site.LEETCODE, 3, "Updated Title", "Updated Content");
        PostResponse updated = postService.updatePost(created.postId(), updateRequest);

        assertEquals("Updated Title", updated.title());
        assertEquals("Updated Content", updated.content());
    }

    @Test
    void deletePost() {
        PostRequest request = new PostRequest(Site.PROGRAMMERS, 2, "Title to Delete", "Content to Delete");
        PostResponse created = postService.createPost(user, request);

        assertDoesNotThrow(() -> postService.deletePost(created.postId()));

        assertThrows(GeneralException.class, () -> postService.getPost(created.postId()));
    }

    @Test
    void getAllPostsByUser() {
        PostRequest request1 = new PostRequest(Site.PROGRAMMERS, 2, "Title1", "Content1");
        PostRequest request2 = new PostRequest(Site.LEETCODE, 3, "Title2", "Content2");

        postService.createPost(user, request1);
        postService.createPost(user, request2);

        List<PostResponse> posts = postService.gettAllPostsByUsers(user);

        assertEquals(2, posts.size());
        assertEquals("Title2", posts.get(0).title()); 
        assertEquals("Title1", posts.get(1).title());
    }
    
}

