package com.doyak.reflector.domain;

import java.util.UUID;

import com.doyak.reflector.domain.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User extends BaseEntity{
	
	@Id
	@GeneratedValue
	@Column(name = "user_id")
	private UUID userId;

}
