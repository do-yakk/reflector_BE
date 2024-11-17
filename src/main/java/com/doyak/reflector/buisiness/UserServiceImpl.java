package com.doyak.reflector.buisiness;

import com.doyak.reflector.buisiness.repository.UserService;
import com.doyak.reflector.infrastructure.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailServiceImpl mailServiceImpl;

    private String createRandomNumber() {
        Random random = new Random();
        StringBuilder randomNumber = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            String number = String.valueOf(random.nextInt(10));
            randomNumber.append(number);
        }
        return randomNumber.toString();
    }

    public UserDto.sendCode sendCode(UserDto.sendCode sendCodeDto) throws Exception {
        String email = sendCodeDto.getEmail();
        String code = createRandomNumber();

        try {
            mailServiceImpl.sendMail(email, code);
            userRepository.createEmailCode(email, code);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return sendCodeDto;
    }
}
