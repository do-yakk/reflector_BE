package com.doyak.reflector.business.repository;

import com.doyak.reflector.domain.Post;

public interface PostService {

    Post create(String userId, Integer beakId, String title, String content);
    Post read(String userId, Integer postId);

}