package com.doyak.reflector.infrastructure;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

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

}
