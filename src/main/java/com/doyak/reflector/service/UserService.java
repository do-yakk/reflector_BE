package com.doyak.reflector.service;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.doyak.reflector.auth.JwtUtil;
import com.doyak.reflector.converter.UserConverter;
import com.doyak.reflector.domain.User;
import com.doyak.reflector.repository.PostRepository;
import com.doyak.reflector.repository.UserRepository;
import com.doyak.reflector.dto.request.UserRequest;
import com.doyak.reflector.dto.response.UserResponse;
import com.doyak.reflector.payload.code.status.ErrorStatus;
import com.doyak.reflector.payload.exception.handler.PostHandler;
import com.doyak.reflector.payload.exception.handler.UserHandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
	
	private final UserRepository userRepository;
	private final PostRepository postRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    
    public void checkEmail(UserRequest.UserEmailDTO request) {
    	if (userRepository.findByEmail(request.getEmail()).isPresent())
    		throw new UserHandler(ErrorStatus.USER_ALREADY_EXIST);
    }
    
    @Transactional
    public UserResponse.UserLoginResponseDTO signup(UserRequest.UserSignUpDTO request) {
    	if (userRepository.findByEmail(request.getEmail()).isPresent())
    		throw new UserHandler(ErrorStatus.USER_ALREADY_EXIST);
    	
    	String encodedPassword = passwordEncoder.encode(request.getPassword());
    	
    	User newUser = UserConverter.toUser(request, encodedPassword);
        User savedUser = userRepository.save(newUser);
    	
    	String accessToken = jwtUtil.createAccessToken(savedUser);
    	
    	return UserConverter.toLoginResponse(savedUser, accessToken);
    }
    
    @Transactional
	public UserResponse.UserLoginResponseDTO login(UserRequest.UserLoginDTO request) {
		User user = userRepository.findByEmail(request.getEmail())
				.orElseThrow(() -> new UserHandler(ErrorStatus.USER_NOT_FOUND));
		
		if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UserHandler(ErrorStatus.INVALID_PASSWORD);
        }
		
		String accessToken = jwtUtil.createAccessToken(user);
		
		return UserConverter.toLoginResponse(user, accessToken);
	}
    
    @Transactional
    public UserResponse.UserUpdateDTO update(User user, UserRequest.UserUpdateDTO request) {
    	User findUser = userRepository.findByEmail(user.getEmail())
				.orElseThrow(() -> new UserHandler(ErrorStatus.USER_NOT_FOUND));
    	
    	if (!request.getEmail().equals(user.getEmail()) && userRepository.findByEmail(request.getEmail()).isPresent())
    		throw new UserHandler(ErrorStatus.USER_ALREADY_EXIST);
    	
    	findUser.updateUser(request, passwordEncoder);
    	
    	return UserConverter.toUpdateResponse(findUser);
    }
    
    @Transactional
    public void delete(User user) {
    	User findUser = userRepository.findByEmail(user.getEmail())
				.orElseThrow(() -> new UserHandler(ErrorStatus.USER_NOT_FOUND));
    	
    	userRepository.delete(findUser);
    	log.info("Successfully delete user");
    }
    
    @Transactional(readOnly = true)
    public List<UserResponse.UserTrackerDTO> getCalendarDate(User user, Integer year) {
    	User findUser = userRepository.findByEmail(user.getEmail())
				.orElseThrow(() -> new UserHandler(ErrorStatus.USER_NOT_FOUND));
    	
    	
    	LocalDate startDate = LocalDate.of(year,  1,  1);
    	LocalDate endDate = LocalDate.of(year, 12, 31);

    	LocalDateTime start = startDate.atStartOfDay();
    	LocalDateTime end = endDate.atTime(LocalTime.MAX);  
    	
    	List<Object[]> queryResult = postRepository.countPostGroupedByDate(findUser, start, end);
    	
    	if (queryResult.isEmpty()) {
    	    throw new PostHandler(ErrorStatus.POST_NOT_FOUND);
    	}
    	
    	
    	/* 전체 날짜 불러오기 (학습 기록이 없는 날은 0)
        Map<LocalDate, Long> calendarMap = logs.stream()
   			.collect(Collectors.toMap(UserTrackerDTO::getDate, UserTrackerDTO::getCount));

		Map<LocalDate, Long> result = new LinkedHashMap<>();
		LocalDate cursor = startDate;
		while (!cursor.isAfter(endDate)) {
		    result.put(cursor, calendarMap.getOrDefault(cursor, 0L));
		    cursor = cursor.plusDays(1);
		}
        */

        return UserConverter.toTrackerResponse(queryResult);
    }
}