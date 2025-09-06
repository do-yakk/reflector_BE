package com.doyak.reflector.dto.response;

import com.doyak.reflector.domain.enums.Site;

public record PostResponse(Long postId, Site site, Integer level, String title, String content) {}
