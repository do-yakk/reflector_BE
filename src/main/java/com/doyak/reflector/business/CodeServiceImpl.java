package com.doyak.reflector.business;

import com.doyak.reflector.business.repository.CodeService;
import com.doyak.reflector.domain.Code;
import com.doyak.reflector.infrastructure.CodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class CodeServiceImpl implements CodeService {

    @Autowired
    private CodeRepository codeRepository;

    @Override
    public Code create(String userId, Integer postId, String code, String review, Float performTime, Float performMem) {
        Code code1 = new Code();
        code1.setCode(code);
        code1.setReview(review);
        code1.setPerform_time(performTime);
        code1.setPerform_mem(performMem);
        code1.setUserId(userId);
        code1.setPostId(postId);
        saveCode(code1);
        return code1;
    }

    private void saveCode(Code code) {
        codeRepository.save(code);
    }

    @Override
    public Code get(String userId, Integer postId, Integer codeId) {
        return codeRepository.findByUserIdAndPostIdAndCodeId(userId, postId, codeId);
    }
}
