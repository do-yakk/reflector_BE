package com.doyak.reflector.buisiness;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserDto {

    private String email;
    private String password;

    @Getter
    @Setter
    public static class sendCode {
        private String email;
    }

    @Getter
    @Setter
    public static class checkCode {
        private String email;
        private String code;
    }

    @Getter
    @Setter
    public static class Email {
        private String email;
    }


}
