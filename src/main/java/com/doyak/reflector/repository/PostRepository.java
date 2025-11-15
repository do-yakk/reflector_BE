package com.doyak.reflector.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.doyak.reflector.domain.Post;
import com.doyak.reflector.domain.User;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findAllByUser(User user, PageRequest pageRequest); 
    
    @Query(value = "SELECT DISTINCT p FROM Post p " +
            "JOIN p.blocks b " +
            "JOIN CodeBlock cb ON cb.blockId = b.blockId " +
            "LEFT JOIN cb.hashtags h " +
            "WHERE p.user = :user AND h.hash IS NULL",
    countQuery = "SELECT COUNT(DISTINCT p) FROM Post p " +
                 "JOIN p.blocks b " +
                 "JOIN CodeBlock cb ON cb.blockId = b.blockId " +
                 "LEFT JOIN cb.hashtags h " +
	                 "WHERE p.user = :user AND h.hash IS NULL")
	Page<Post> findAllByUserWithNoHash(@Param("user") User user, Pageable pageable);
	    
    @Query("SELECT p FROM Post p LEFT JOIN FETCH p.blocks WHERE p.postId = :postId")
    Optional<Post> findByIdWithBlocks(@Param("postId") Long postId);
    
    @Query("SELECT FUNCTION('DATE', p.createdAt) as date, COUNT(p) as count " +
    		"FROM Post p WHERE p.user = :user AND p.createdAt BETWEEN :from AND :to " +
    		"GROUP BY FUNCTION('DATE', p.createdAt) " +
    		"ORDER BY date")
    List<Object[]> countPostGroupedByDate(
    		@Param("user") User user, 
    		@Param("from") LocalDateTime from, 
    		@Param("to") LocalDateTime to);

    @Query(value = "SELECT DISTINCT p FROM Post p " +
				   "JOIN p.blocks b " +
				   "JOIN CodeBlock cb ON cb.blockId = b.blockId " +
				   "JOIN cb.hashtags h " +
				   "WHERE p.user = :user AND h.hash = :hash",
		   countQuery = "SELECT COUNT(DISTINCT p) FROM Post p " +
				   		"JOIN p.blocks b " +
				   		"JOIN CodeBlock cb ON cb.blockId = b.blockId " +
				   		"JOIN cb.hashtags h " +
				   		"WHERE p.user = :user AND h.hash = :hash")
	Page<Post> findByUserAndHashtag(@Param("user") User user, @Param("hash") String hash, Pageable pageable);
     

}
