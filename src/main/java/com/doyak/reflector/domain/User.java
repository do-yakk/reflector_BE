package com.doyak.reflector.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.Entity;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
@Entity
@Table("user")
public class User {

    @Id
    @Column(name = "user_id")
    private String userId;

    private String email;
    private String password;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}