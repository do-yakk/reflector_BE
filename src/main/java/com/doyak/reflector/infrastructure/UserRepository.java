package com.doyak.reflector.infrastructure;

public interface UserRepository {
    void createEmailCode(String email, String code);
    Boolean checkEmailCode(String email);
    String getEmailCode(String email);
    void deleteEmailCode(String email);
}
