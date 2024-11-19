package com.doyak.reflector.infrastructure;

import com.doyak.reflector.buisiness.UserDto;
import com.doyak.reflector.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    void save(UserDto userDto);
    User findByEmail(String email);
}
