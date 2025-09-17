package com.doyak.reflector.service;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.doyak.reflector.converter.UserConverter;
import com.doyak.reflector.dto.request.UserRequest;
import com.doyak.reflector.dto.response.UserResponse;
import com.doyak.reflector.payload.code.status.ErrorStatus;
import com.doyak.reflector.payload.exception.handler.UserHandler;
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
	
	@Value("${spring.mail.username}")
	private String senderEmail;
	
	private String createCode() {
		int num = secureRandom.nextInt(1_000_000);
        return String.format("%06d", num);
	}
	
	private SimpleMailMessage createEmailVerifyForm(String email) {
		String code = createCode();
		
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(email);
		message.setSubject("[reflector] 인증번호를 확인해주세요.");
		message.setText("[" + code + "]");
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
	
	public UserResponse.EmailVerifyDTO verifyEmailCode(UserRequest.EmailCodeDTO emailVerifyDTO) {
		String codeFoundByEmail = redisUtil.getData(emailVerifyDTO.getEmail());
		if (codeFoundByEmail == null) {
			throw new UserHandler(ErrorStatus.INVALID_EMAIL);
		} else if (!codeFoundByEmail.equals(emailVerifyDTO.getCode())) {
			throw new UserHandler(ErrorStatus.INVALID_CODE);
		} else {
			return UserConverter.toEmailVerifyResponse(true);
		}
	}
	
}