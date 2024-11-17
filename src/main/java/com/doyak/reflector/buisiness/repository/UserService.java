package com.doyak.reflector.buisiness.repository;

import com.doyak.reflector.buisiness.UserDto;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    UserDto.sendCode sendCode(UserDto.sendCode sendCodeDto) throws Exception;
    UserDto.checkCode checkCode(UserDto.checkCode checkCodeDto) throws Exception;
}
