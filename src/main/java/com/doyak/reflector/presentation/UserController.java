package com.doyak.reflector.presentation;

import com.doyak.reflector.buisiness.APIResponse;
import com.doyak.reflector.buisiness.UserDto;
import com.doyak.reflector.buisiness.repository.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("register/email/verify-code")
    public ResponseEntity<APIResponse<?>> emailDemand(@RequestBody UserDto.sendCode sendCodeDto) {
        try {
            UserDto.sendCode result = userService.sendCode(sendCodeDto);
            return ResponseEntity.ok().body(APIResponse.successAPI("Successfully send code.", result));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(APIResponse.errorAPI(e.getMessage()));
        }
    }

    @GetMapping("register/email/verify-code")
    public ResponseEntity<APIResponse<?>> emailVerificaton(@RequestBody UserDto.checkCode checkCodeDto) {
        try {
            UserDto.checkCode result = userService.checkCode(checkCodeDto);
            return ResponseEntity.ok().body(APIResponse.successAPI("Email verification successful.", result));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(APIResponse.errorAPI(e.getMessage()));
        }
    }

    @GetMapping("register/email")
    public ResponseEntity<APIResponse<?>> emailCheck(@RequestBody UserDto.Email emailDto) {
        try {
            UserDto.Email result = userService.checkEmail(emailDto);
            return ResponseEntity.ok().body(APIResponse.successAPI("Email is not duplicate.", result));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(APIResponse.errorAPI(e.getMessage()));
        }
    }

    @PostMapping("register")
    public ResponseEntity<APIResponse<?>> register(@RequestBody UserDto userDto) {
        try {
            userService.register(userDto);
            return ResponseEntity.ok().body(APIResponse.successAPI("Successfully registered.", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(APIResponse.errorAPI(e.getMessage()));
        }
    }


}
