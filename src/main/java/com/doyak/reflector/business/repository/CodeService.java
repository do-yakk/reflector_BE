package com.doyak.reflector.business.repository;

import com.doyak.reflector.domain.Code;

public interface CodeService {

    Code create(String userId, Integer postId, String code, String review, Float performTime, Float performMem);
    Code get(String userId, Integer postId, Integer codeId);
    Code modify(String userId, Integer postId, Integer codeId, String code, String review, Float performTime, Float performMem);
    void delete(String userId, Integer postId, Integer codeId);
    void deleteAll(String userId, Integer postId);
}
