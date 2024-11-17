package com.doyak.reflector.domain;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.Entity;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;


@Getter
@Setter
@Entity
@Table("user")
public class User {

    @Id
    private UUID userId;

    private String email;

    private String password;
}
