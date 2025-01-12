package com.doyak.reflector.presentation;

import com.doyak.reflector.business.dto.APIResponse;
import com.doyak.reflector.business.dto.CodeDto;
import com.doyak.reflector.business.repository.CodeService;
import com.doyak.reflector.business.repository.PostService;
import com.doyak.reflector.domain.Code;
import com.doyak.reflector.domain.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/codes")
public class CodeController {

    @Autowired
    private CodeService codeService;
    @Autowired
    private PostService postService;


    @PostMapping("/create/{user-id}/{post-id}")
    public ResponseEntity<?> create(@PathVariable("user-id") String userId, @PathVariable("user-id") Integer postId, @RequestBody CodeDto codeDto) {
        try {
            Code code = codeService.create(userId, postId, codeDto.getCode(), codeDto.getReview(),
                    codeDto.getPerformanceTime(), codeDto.getPerformanceMem());
            Post post = postService.read(userId, postId);
            return ResponseEntity.ok(APIResponse.successAPI("성공", code));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(APIResponse.errorAPI("실패: " + e.getMessage()));
        }
    }




}
