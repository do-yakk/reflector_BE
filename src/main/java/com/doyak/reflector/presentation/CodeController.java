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
    public ResponseEntity<?> create(@PathVariable("user-id") String userId, @PathVariable("user-id") Integer postId, @RequestBody CodeDto.Code codeDto) {
        try {
            Code code = codeService.create(userId, postId, codeDto.getCode(), codeDto.getReview(),
                    codeDto.getPerformanceTime(), codeDto.getPerformanceMem());
            Post post = postService.read(userId, postId);
            return ResponseEntity.ok(APIResponse.successAPI("성공", code));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(APIResponse.errorAPI("실패: " + e.getMessage()));
        }
    }

    @PutMapping("/update/{user-id}/{post-id}/{code-id}")
    public ResponseEntity<?> update(@PathVariable("user-id") String userId, @PathVariable("post-id") Integer postId, @PathVariable("code-id") Integer codeId, @RequestBody CodeDto.Code codeDto) {
        try {
            Post post = postService.read(userId, postId);
            post.updateTime();
            Code updated = codeService.modify(userId,postId,codeId, codeDto.getCode(), codeDto.getReview(), codeDto.getPerformanceTime(), codeDto.getPerformanceMem());
            return ResponseEntity.ok(APIResponse.successAPI("성공", updated));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(APIResponse.errorAPI("실패: " + e.getMessage()));
        }
    }

    @DeleteMapping("/delete/{user-id}/{post-id}/{code-id}")
    public ResponseEntity<?> delete(@PathVariable("user-id") String userId, @PathVariable("post-id") Integer postId, @PathVariable("code-id") Integer codeId) {
        try {
            codeService.delete(userId, postId, codeId);
            return ResponseEntity.ok(APIResponse.successAPI("성공", codeId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(APIResponse.errorAPI("실패: " + e.getMessage()));
        }
    }




}
