package com.doyak.reflector.infrastructure;

import com.doyak.reflector.buisiness.UserDto;
import com.doyak.reflector.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository {
    void createEmailCode(String email, String code);
    Boolean checkEmailCode(String email);
    String getEmailCode(String email);
    void deleteEmailCode(String email);
    boolean existsByEmail(String email);
    void save(UserDto userDto) throws Exception;
}
