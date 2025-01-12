package com.doyak.reflector.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "CODE")
public class Code {

    @Id
    @Column(name = "code_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codeId;

    @Column(name = "post_id")
    private Integer postId;
    @Column(name = "user_id")
    private String userId;

    private String code;
    private String review;
    private Float perform_time;
    private Float perform_mem;



}
