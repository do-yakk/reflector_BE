package com.doyak.reflector.infrastructure;

import org.springframework.stereotype.Repository;

@Repository
public interface RedisRepository {
    void createEmailCode(String email, String code);
    Boolean checkEmailCode(String email);
    String getEmailCode(String email);
    void deleteEmailCode(String email);
}
