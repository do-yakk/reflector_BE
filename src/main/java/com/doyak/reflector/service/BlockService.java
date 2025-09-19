package com.doyak.reflector.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.doyak.reflector.converter.BlockConverter;
import com.doyak.reflector.domain.Block;
import com.doyak.reflector.domain.CodeBlock;
import com.doyak.reflector.domain.Post;
import com.doyak.reflector.domain.TextBlock;
import com.doyak.reflector.dto.request.BlockRequest;
import com.doyak.reflector.dto.response.BlockResponse;
import com.doyak.reflector.payload.code.status.ErrorStatus;
import com.doyak.reflector.payload.exception.GeneralException;
import com.doyak.reflector.repository.BlockRepository;
import com.doyak.reflector.repository.PostRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BlockService {

	private final PostRepository postRepository;
    private final BlockRepository blockRepository;
    private final BlockConverter blockConverter; 

    // 코드 블럭 생성  
    @Transactional
    public BlockResponse createBlock(BlockRequest request, Long postId) {
    	Post post = postRepository.findById(postId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.POST_NOT_FOUND));
    	int nextOrderIndex = blockRepository.findMaxOrderIndexByPost(post).orElse(0) + 1;
    	Block block = blockConverter.toBlock(request, nextOrderIndex, post, post.getUser());
    	Block saved = blockRepository.save(block);
    	return blockConverter.toResponse(saved);
    }
    
    // 단일 블럭 조회 
    @Transactional(readOnly = true)
    public BlockResponse getBlock(Long blockId) {
        Block block = findBlockById(blockId);
        return blockConverter.toResponse(block);
    }

    // 게시글에 속한 블럭 목록 조회 
    @Transactional(readOnly = true)
    public List<BlockResponse> getBlocksByPostId(Long postId) {
        List<Block> blocks = blockRepository.findAllByPostIdOrderByOrderIndex(postId);
        return blockConverter.toResponseList(blocks);
    }
    
    // 블럭 수정
    @Transactional
    public BlockResponse updateBlock(Long blockId, BlockRequest request) {
        Block block = findBlockById(blockId);

        if (request instanceof BlockRequest.TextCommand textRequest && block instanceof TextBlock textBlock) {
            textBlock.update(textRequest.getContent());
            return blockConverter.toResponse(textBlock);
        } else if (request instanceof BlockRequest.CodeCommand codeRequest && block instanceof CodeBlock codeBlock) {
        	codeBlock.update(codeRequest.getContent(), codeRequest.getLanguage(), codeRequest.getPerformTime(), codeRequest.getPerformMem());
            return blockConverter.toResponse(codeBlock);
        } else {
        	throw new GeneralException(ErrorStatus.UNSUPPORTED_BLOCK_TYPE);
        }
    }


    // 블럭 삭제 
    @Transactional
    public void deleteBlock(Long blockId) {
        Block block = blockRepository.findById(blockId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.UNSUPPORTED_BLOCK_TYPE));

        int deletedOrderIndex = block.getOrderIndex();
        Long postId = block.getPost().getPostId();

        blockRepository.delete(block);
        blockRepository.decrementOrderIndexAfter(postId, deletedOrderIndex);
    }
    
    private Block findBlockById(Long blockId) {
    	Block block = blockRepository.findById(blockId)
    		    .orElseThrow(() -> new GeneralException(ErrorStatus.BLOCK_NOT_FOUND));
    	return block;
    }
}