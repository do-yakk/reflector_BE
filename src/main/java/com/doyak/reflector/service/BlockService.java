package com.doyak.reflector.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.doyak.reflector.converter.BlockConverter;
import com.doyak.reflector.domain.Block;
import com.doyak.reflector.domain.CodeBlock;
import com.doyak.reflector.domain.Hashtag;
import com.doyak.reflector.domain.Post;
import com.doyak.reflector.domain.TextBlock;
import com.doyak.reflector.dto.request.BlockRequest;
import com.doyak.reflector.dto.response.BlockResponse;
import com.doyak.reflector.payload.code.status.ErrorStatus;
import com.doyak.reflector.payload.exception.GeneralException;
import com.doyak.reflector.repository.BlockRepository;
import com.doyak.reflector.repository.HashtagRepository;
import com.doyak.reflector.repository.PostRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BlockService {

	private final PostRepository postRepository;
    private final BlockRepository blockRepository;
    private final BlockConverter blockConverter; 
    
    private final HashtagRepository hashtagRepository;

    // 블럭 생성  
    @Transactional
    public BlockResponse createBlock(BlockRequest request, Long postId) {
    	Post post = postRepository.findById(postId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.POST_NOT_FOUND));
    	int nextOrderIndex = blockRepository.findMaxOrderIndexByPost(post).orElse(0) + 1;
    	Block block = blockConverter.toBlock(request, nextOrderIndex, post, post.getUser());
    	if (request instanceof BlockRequest.CodeCommand codeRequest && block instanceof CodeBlock codeBlock) {
            Set<Hashtag> hashtags = getHashtags(codeRequest.getHashtags());
            hashtags.forEach(codeBlock::addHashtag);
        }
    	post.getBlocks().add(block);
    	Block saved = blockRepository.save(block);
    	return blockConverter.toResponse(saved);
    }
    
    // 단일 블럭 조회 
    @Transactional(readOnly = true)
    public BlockResponse getBlock(Long blockId) {
        Block block = findBlockById(blockId);
        return blockConverter.toResponse(block);
    }

    // 블럭 수정
    @Transactional
    public BlockResponse updateBlock(Long blockId, BlockRequest request) {
        Block block = findBlockById(blockId);

        if (request instanceof BlockRequest.TextCommand textRequest && block instanceof TextBlock textBlock) {
            textBlock.update(textRequest.getContent());
            return blockConverter.toResponse(textBlock);
        } else if (request instanceof BlockRequest.CodeCommand codeRequest && block instanceof CodeBlock codeBlock) {
        	Set<Hashtag> newHashtags = getHashtags(codeRequest.getHashtags());
            codeBlock.getHashtags().removeIf(tag -> !newHashtags.contains(tag));
            newHashtags.forEach(tag -> codeBlock.getHashtags().add(tag));
        	
        	codeBlock.update(codeRequest.getContent(),codeRequest.getLanguage(), 
        			codeRequest.getPerformTime(), codeRequest.getPerformMem());
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
        
        if (block instanceof CodeBlock codeBlock) {
            codeBlock.getHashtags().forEach(h -> h.getCodeBlocks().remove(codeBlock));
            codeBlock.getHashtags().clear();
        }

        int deletedOrderIndex = block.getOrderIndex();
        Long postId = block.getPost().getPostId();

        blockRepository.delete(block);
        blockRepository.decrementOrderIndexAfter(postId, deletedOrderIndex);
    }
    
    private Set<Hashtag> getHashtags(List<String> tagNames) {
        // 이미 존재하는 해시태그 
        Set<Hashtag> existing = hashtagRepository.findByHashIn(tagNames);
        Set<String> existingNames = existing.stream()
            .map(Hashtag::getHash)
            .collect(Collectors.toSet());
        // 새로 만들 해시태그 
        List<Hashtag> newTags = tagNames.stream().distinct()
        	    .filter(name -> !existingNames.contains(name))
        	    .map(name -> Hashtag.builder().hash(name).build())
        	    .collect(Collectors.toList());
    	hashtagRepository.saveAll(newTags);

        // 기존 + 새 태그 합치기
        Set<Hashtag> allTags = new HashSet<>(existing);
        allTags.addAll(newTags);

        return allTags;
    }
    
    private Block findBlockById(Long blockId) {
    	Block block = blockRepository.findById(blockId)
    		    .orElseThrow(() -> new GeneralException(ErrorStatus.BLOCK_NOT_FOUND));
    	return block;
    }
}