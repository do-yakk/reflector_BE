package com.doyak.reflector.infrastructure;

 import com.doyak.reflector.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    Post findByUserIdAndPostId(String userId, Integer postId);
}