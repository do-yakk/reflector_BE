package com.doyak.reflector.buisiness;

import com.doyak.reflector.buisiness.repository.UserService;
import com.doyak.reflector.domain.User;
import com.doyak.reflector.infrastructure.RedisRepository;
import com.doyak.reflector.infrastructure.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RedisRepository redisRepository;

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
            redisRepository.createEmailCode(email, code);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return sendCodeDto;
    }

    public UserDto.checkCode checkCode(UserDto.checkCode checkCodeDto) throws Exception {
        if (!isVerify(checkCodeDto)) {
            throw new Exception("Failed to verify email.");
        }
        redisRepository.deleteEmailCode(checkCodeDto.getEmail());
        return checkCodeDto;
    }

    private boolean isVerify(UserDto.checkCode checkCodeDto) {
        String email = checkCodeDto.getEmail();
        String code = checkCodeDto.getCode();

        return redisRepository.checkEmailCode(email) && redisRepository.getEmailCode(email).equals(code);
    }

    public UserDto.Email checkEmail(UserDto.Email emailDto) throws Exception {
        String email = emailDto.getEmail();
        User result = userRepository.findByEmail(email);

        if (result == null) {
            return emailDto;
        } else {
            throw new Exception("Email is duplicate.");
        }
    }

    public void register(UserDto userDto) throws Exception {
        if (userDto == null)
            throw new Exception("User cannot be null");
        try {
            User user = new User();
            user.setUserId(UUID.randomUUID().toString());
            user.setEmail(userDto.getEmail());
            user.setPassword(userDto.getPassword());
            user.setCreated_at(LocalDateTime.now());
            user.setUpdated_at(LocalDateTime.now());
            userRepository.save(user);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

    }

    public UserDto.UserId login(UserDto userDto) throws Exception {
        if (userDto == null)
            throw new Exception("User cannot be null");
        try {
            User user = userRepository.findByEmail(userDto.getEmail());
            if (user == null) {
                throw new Exception("User not found.");
            }
            UserDto.UserId loginUser = new UserDto.UserId();
            loginUser.setUser_id(user.getUserId());
            return loginUser;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
