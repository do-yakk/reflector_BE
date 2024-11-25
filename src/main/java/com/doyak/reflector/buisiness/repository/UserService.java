package com.doyak.reflector.buisiness.repository;

import com.doyak.reflector.buisiness.UserDto;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    UserDto.sendCode sendCode(UserDto.sendCode sendCodeDto) throws Exception;
    UserDto.checkCode checkCode(UserDto.checkCode checkCodeDto) throws Exception;
    UserDto.Email checkEmail(UserDto.Email emailDto) throws Exception;
    void register(UserDto userDto) throws Exception;
    UserDto.UserId login(UserDto userDto) throws Exception;
    UserDto.updateEmail updateEmail(UserDto.updateEmail userEmailDto) throws Exception;
    UserDto.updatePassword updatePassword(UserDto.updatePassword userPasswordDto) throws Exception;
    void deleteUser(String userId) throws Exception;
}
