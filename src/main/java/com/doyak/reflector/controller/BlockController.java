package com.doyak.reflector.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.doyak.reflector.dto.request.BlockRequest;
import com.doyak.reflector.dto.response.BlockResponse;
import com.doyak.reflector.payload.ApiResponse;
import com.doyak.reflector.service.BlockService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/blocks/{postId}")
@RequiredArgsConstructor
public class BlockController {

	private final BlockService blockService;
	
	@PostMapping("/text")
	@Operation(summary = "텍스트 블럭 생성", description = "텍스트 내용을 입력해주세요.")
	public ApiResponse<BlockResponse> createTextBlock( @PathVariable Long postId,
	        											@RequestBody BlockRequest.TextCommand request) {
	    return ApiResponse.onSuccess(blockService.createBlock(request, postId));
	}

	@PostMapping("/code")
	@Operation(summary = "코드 블럭 생성", description = "코드 내용을 입력해주세요.")
	public ApiResponse<BlockResponse> createCodeBlock( @PathVariable Long postId,
	        											@RequestBody BlockRequest.CodeCommand request) {
	    return ApiResponse.onSuccess(blockService.createBlock(request, postId));
	}
	
	@GetMapping("/{blockId}")
	@Operation(summary = "해당 블럭 읽기", description = "읽어오길 원하는 블럭 아이디를 입력해주세요.")
    public ApiResponse<BlockResponse> getBlock(@PathVariable Long blockId) {
        return ApiResponse.onSuccess(blockService.getBlock(blockId));
    }

    @GetMapping
	@Operation(summary = "전체 블럭 읽기")
    public ApiResponse<List<BlockResponse>> getBlocksByPost(@PathVariable Long postId) {
        return ApiResponse.onSuccess(blockService.getBlocksByPostId(postId));
    }
    
    @PutMapping("/text/{blockId}")
    @Operation(summary = "텍스트 블럭 수정", description = "수정할 텍스트 내용을 입력해주세요.")
    public ApiResponse<BlockResponse> updateTextBlock(@PathVariable Long blockId,
            											@RequestBody BlockRequest.TextCommand request) {
        return ApiResponse.onSuccess(blockService.updateBlock(blockId, request));
    }
    
    @PutMapping("/code/{blockId}")
    @Operation(summary = "코드 블럭 수정", description = "수정할 코드 내용을 입력해주세요.")
    public ApiResponse<BlockResponse> updateCodeBlock(@PathVariable Long blockId,
    													@RequestBody BlockRequest.CodeCommand request) {
        return ApiResponse.onSuccess(blockService.updateBlock(blockId, request));
    }


    @DeleteMapping("{blockId}")
	@Operation(summary = "해당 블럭 삭제", description = "삭제하길 원하는 블럭 아이디를 입력해주세요.")
    public void deleteBlock(@PathVariable Long blockId) {
        blockService.deleteBlock(blockId);
    }

}
