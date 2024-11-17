package com.doyak.reflector.infrastructure;

public interface UserRepository {
    void createEmailCode(String email, String code);

}
