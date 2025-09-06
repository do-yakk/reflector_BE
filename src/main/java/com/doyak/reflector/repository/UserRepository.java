package com.doyak.reflector.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.doyak.reflector.domain.User;

public interface UserRepository extends JpaRepository<User, String>{

}
