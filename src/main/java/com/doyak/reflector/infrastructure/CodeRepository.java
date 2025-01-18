package com.doyak.reflector.infrastructure;

import com.doyak.reflector.domain.Code;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CodeRepository extends JpaRepository<Code, Integer> {
    Code findByUserIdAndPostIdAndCodeId(String userId, Integer postId, Integer codeId);
    List<Code> findByUserIdAndPostId(String userId, Integer postId);
}