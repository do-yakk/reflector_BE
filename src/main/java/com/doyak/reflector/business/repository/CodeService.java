package com.doyak.reflector.business.repository;

import com.doyak.reflector.domain.Code;

public interface CodeService {

    Code create(String userId, Integer postId, String code, String review, Float performTime, Float performMem);
    Code get(String userId, Integer postId, Integer codeId);

}
