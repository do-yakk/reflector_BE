package com.doyak.reflector.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.doyak.reflector.domain.Block;
import com.doyak.reflector.domain.Post;

@Repository
public interface BlockRepository extends JpaRepository<Block, Long> {

	@Query("SELECT b FROM Block b WHERE b.post.id = :postId ORDER BY b.orderIndex")
	List<Block> findAllByPostIdOrderByOrderIndex(@Param("postId") Long postId);

	@Query("SELECT MAX(b.orderIndex) FROM Block b WHERE b.post = :post")
	Optional<Integer> findMaxOrderIndexByPost(@Param("post") Post post);

	@Modifying
    @Query("UPDATE Block b SET b.orderIndex = b.orderIndex - 1 " +
           "WHERE b.post.id = :postId AND b.orderIndex > :deletedOrderIndex")
    void decrementOrderIndexAfter(@Param("postId") Long postId,
                                  @Param("deletedOrderIndex") Double deletedOrderIndex);
	
	List<Block> findAllByPostOrderByOrderIndexAsc(Post post);
}
