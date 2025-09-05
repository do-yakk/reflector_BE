package com.doyak.reflector.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.doyak.reflector.domain.User;
import com.doyak.reflector.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	
	private final UserRepository userRepository;
	
	public Optional<User> login(String userEmail) {
		return userRepository.findByEmail(userEmail);
    }
}