package com.doyak.reflector.business.dto;

import lombok.Getter;

public class PostDto {

    @Getter
    public static class Post {
        private Integer baekId;
        private String title;
        private String content;
    }

    @Getter
    public static class Modify {
        private String title;
        private String content;
    }

}
