package com.doyak.reflector.converter;

import java.util.List;

import org.springframework.stereotype.Component;

import com.doyak.reflector.domain.Block;
import com.doyak.reflector.domain.CodeBlock;
import com.doyak.reflector.domain.Post;
import com.doyak.reflector.domain.TextBlock;
import com.doyak.reflector.dto.request.BlockRequest;
import com.doyak.reflector.dto.response.BlockResponse;
import com.doyak.reflector.payload.code.status.ErrorStatus;
import com.doyak.reflector.payload.exception.GeneralException;

@Component
public class BlockConverter {

    // Request → Entity
    public Block toBlock(BlockRequest request, int orderIndex, Post post) {
        if (request instanceof BlockRequest.TextCommand textReq) {
            return toTextEntity(textReq, orderIndex, post);
        } else if (request instanceof BlockRequest.CodeCommand codeReq) {
            return toCodeEntity(codeReq, orderIndex, post);
        } else {
            throw new GeneralException(ErrorStatus.UNSUPPORTED_BLOCK_TYPE);
        }
    }

    private TextBlock toTextEntity(BlockRequest.TextCommand request, int orderIndex, Post post) {
        return TextBlock.builder()
        		.orderIndex(orderIndex)
        		.post(post)
                .content(request.getContent())
                .build();
    }

    private CodeBlock toCodeEntity(BlockRequest.CodeCommand request, int orderIndex, Post post) {
        return CodeBlock.builder()
        		.orderIndex(orderIndex)
        		.post(post)
                .content(request.getContent())
                .language(request.getLanguage())
                .performTime(request.getPerformTime())
                .performMem(request.getPerformMem())
                .build();
    }

    // Entity → Response
    public BlockResponse toResponse(Block block) {
        if (block instanceof TextBlock textBlock) {
            return toTextResponse(textBlock);
        } else if (block instanceof CodeBlock codeBlock) {
            return toCodeResponse(codeBlock);
        } else {
        	throw new GeneralException(ErrorStatus.UNSUPPORTED_BLOCK_TYPE);
        }
    }

    private BlockResponse.Text toTextResponse(TextBlock block) {
        return BlockResponse.Text.builder()
                .content(block.getContent())
                .build();
    }

    private BlockResponse.Code toCodeResponse(CodeBlock block) {
        return BlockResponse.Code.builder()
                .content(block.getContent())
                .language(block.getLanguage())
                .performTime(block.getPerformTime())
                .performMem(block.getPerformMem())
                .build();
    }

    // Entity List → Response List
    public List<BlockResponse> toResponseList(List<Block> blocks) {
        return blocks.stream()
                     .map(this::toResponse)
                     .toList();
    }
}
