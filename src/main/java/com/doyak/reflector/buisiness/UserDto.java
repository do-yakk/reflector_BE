package com.doyak.reflector.buisiness;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserDto {

    private String user_id;
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
    public static class UserId {
        private String user_id;
    }

    @Getter
    @Setter
    public static class Email {
        private String email;
    }

    @Getter
    @Setter
    public static class Password {
        private String password;
    }


    @Getter
    @Setter
    public static class updateEmail {
        private String user_id;
        private String email;
    }
    @Getter
    @Setter
    public static class updatePassword {
        private String user_id;
        private String password;
    }

}
