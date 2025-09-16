package com.doyak.reflector.service;

import java.security.SecureRandom;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.doyak.reflector.converter.UserConverter;
import com.doyak.reflector.dto.request.UserRequest;
import com.doyak.reflector.dto.response.UserResponse;
import com.doyak.reflector.util.RedisUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {
	
	private final JavaMailSender javaMailSender;
	private final RedisUtil redisUtil;
	private static final SecureRandom secureRandom = new SecureRandom();
	private static final String senderEmail = "email";
	
	private String createCode() {
		int num = secureRandom.nextInt(1_000_000);
        return String.format("%06d", num);
	}
	
	private SimpleMailMessage createEmailVerifyForm(String email) {
		String code = createCode();
		
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(email);  // 수신자
		message.setSubject("[reflector] 인증번호를 확인해주세요.");  // 제
		message.setText("[" + code + "]");  // 내용
		message.setFrom(senderEmail);
		
		redisUtil.setDataExpire(email, code, 60 * 3L);
		return message;
	}
	
	public UserResponse.EmailVerifyDTO sendEmail(UserRequest.UserEmailDTO emailDTO) {
		String email = emailDTO.getEmail();
		if (redisUtil.existData(email)) {
			redisUtil.deleteData(email);
		}
		
		SimpleMailMessage message = createEmailVerifyForm(email);
		javaMailSender.send(message);
		
		return UserConverter.toEmailVerifyResponse(true);
	}
	
	public UserResponse.EmailVerifyDTO verifyEmailCode(UserRequest.EmailVerifyDTO emailVerifyDTO) {
		String codeFoundByEmail = redisUtil.getData(emailVerifyDTO.getEmail());
		if (codeFoundByEmail == null) {
			return UserConverter.toEmailVerifyResponse(false);
		}
		return UserConverter.toEmailVerifyResponse(codeFoundByEmail.equals(emailVerifyDTO.getCode()));
	}
	
}