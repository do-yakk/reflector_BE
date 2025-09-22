package com.doyak.reflector.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.doyak.reflector.domain.Hashtag;
import com.doyak.reflector.domain.User;

@Repository
public interface HashtagRepository extends JpaRepository<Hashtag, Long> {

	Optional<Hashtag> findByHash(String tag);

	@Query("SELECT DISTINCT h.hash FROM Hashtag h " +
			"JOIN h.codeBlocks cb " +
			"JOIN cb.post p " +
			"WHERE p.user = :user")
	List<String> findAllHashStringsByUserId(@Param("user") User user);

	Set<Hashtag> findByHashIn(Set<String> tagNames);
}
