package com.doyak.reflector.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.doyak.reflector.domain.Post;
import com.doyak.reflector.domain.User;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByUserOrderByCreatedAtDesc(User user);

	Page<Post> findAllByUser(User user, PageRequest pageRequest); 
}
