package com.doyak.reflector.infrastructure;

import com.doyak.reflector.buisiness.UserDto;
import com.doyak.reflector.domain.User;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.DriverManager;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final StringRedisTemplate stringRedisTemplate;
    private final JdbcTemplate jdbcTemplate;

    public UserRepositoryImpl(StringRedisTemplate stringRedisTemplate, JdbcTemplate jdbcTemplate) { this.stringRedisTemplate = stringRedisTemplate;
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createEmailCode(String email, String code) {
        int TTL = 3 * 60;
        stringRedisTemplate.opsForValue().set(email, code, Duration.ofSeconds(TTL));
    }
    public Boolean checkEmailCode(String email) { return stringRedisTemplate.hasKey(email); }
    public String getEmailCode(String email) { return stringRedisTemplate.opsForValue().get(email); }
    public void deleteEmailCode(String email) { stringRedisTemplate.delete(email); }

    public boolean existsByEmail(String email) {
        String sql = "select count(*) from User where email = ?";

        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }

    public void save(UserDto userDto) throws Exception {
        String sql = "insert into user (user_id, email, password, created_at, updated_at) values (?, ?, ?, ?, ?)";

        int rowAffected = jdbcTemplate.update(sql, UUID.randomUUID().toString(), userDto.getEmail(), userDto.getPassword(),
                                              LocalDateTime.now(), LocalDateTime.now());

        if (rowAffected < 1) {
            throw new Exception("Failed to save user");
        }

    }

}
