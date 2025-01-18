package com.doyak.reflector.presentation;

import com.doyak.reflector.business.dto.APIResponse;
import com.doyak.reflector.business.dto.PostDto;
import com.doyak.reflector.business.repository.CodeService;
import com.doyak.reflector.business.repository.PostService;
import com.doyak.reflector.domain.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;
    @Autowired
    private CodeService codeService;


    @PostMapping("/create/{user-id}")
    public ResponseEntity<?> create(@PathVariable("user-id") String userId, @RequestBody PostDto.Post post) {
        try {
            Post newPost = postService.create(userId, post.getBaekId(), post.getTitle(), post.getContent());
            return ResponseEntity.ok(APIResponse.successAPI("노트가 성공적으로 생성되었습니다.", newPost));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(APIResponse.errorAPI("노트 생성 실패: " + e.getMessage()));
        }
    }

    @GetMapping("/read/{user-id}/{post-id}")
    public ResponseEntity<?> read(@PathVariable("user-id") String userId, @PathVariable("post-id") Integer postId) {
        try {
            Post post = postService.read(userId, postId);
            return ResponseEntity.ok(APIResponse.successAPI("노트를 성공적으로 조회했습니다.", post));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(APIResponse.errorAPI("노트 조회 실패: " + e.getMessage()));
        }
    }

    @PutMapping("/update/{user-id}/{post-id}")
    public ResponseEntity<?> update(@PathVariable("user-id") String userId, @PathVariable("post-id") Integer postId, @RequestBody PostDto.Modify modify) {
        try {
            Post modifiedPost = postService.update(userId, postId, modify.getTitle(), modify.getContent());
            return ResponseEntity.ok(APIResponse.successAPI("노트 제목이 성공적으로 수정되었습니다.", modifiedPost));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(APIResponse.errorAPI("노트 수정 실패: " + e.getMessage()));
        }
    }


    @DeleteMapping("/delete/{user-id}/{post-id}")
    public ResponseEntity<?> delete(@PathVariable("user-id") String userId, @PathVariable("post-id") Integer postId) {
        try {
            codeService.deleteAll(userId, postId);
            postService.delete(userId, postId);
            return ResponseEntity.ok(APIResponse.successAPI("노트가 성공적으로 삭제되었습니다.", postId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(APIResponse.errorAPI("노트 삭제 실패: " + e.getMessage()));
        }
    }

}
