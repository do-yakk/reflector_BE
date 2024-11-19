package com.doyak.reflector.infrastructure;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
public class RedisRepositoryImpl implements RedisRepository {

    private final StringRedisTemplate stringRedisTemplate;

    public RedisRepositoryImpl(StringRedisTemplate stringRedisTemplate) { this.stringRedisTemplate = stringRedisTemplate; }

    public void createEmailCode(String email, String code) {
        int TTL = 3 * 60;
        stringRedisTemplate.opsForValue().set(email, code, Duration.ofSeconds(TTL));
    }
    public Boolean checkEmailCode(String email) { return stringRedisTemplate.hasKey(email); }
    public String getEmailCode(String email) { return stringRedisTemplate.opsForValue().get(email); }
    public void deleteEmailCode(String email) { stringRedisTemplate.delete(email); }
}
