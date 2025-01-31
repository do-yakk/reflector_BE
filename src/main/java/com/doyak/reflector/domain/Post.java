package com.doyak.reflector.domain;


import java.sql.Timestamp;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "POST")
public class Post {

    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postId;

    @Column(name = "user_id")
    private String userId;
    @Column(name = "beak_id")
    private Integer baekId;
    private String title;
    private String content;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    public void updateTime(){
        this.updated_at = LocalDateTime.now();
    }

    public void update(String newTitle, String newContent) {
        this.title = newTitle;
        this.content = newContent;
        updateTime();
    }

}
