package com.doyak.reflector.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.doyak.reflector.domain.Post;
import com.doyak.reflector.domain.User;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByUserOrderByCreatedAtDesc(User user); 
    
    @Query("SELECT p FROM Post p LEFT JOIN FETCH p.blocks WHERE p.postId = :postId")
    Optional<Post> findByIdWithBlocks(@Param("postId") Long postId);
}
