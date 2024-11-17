package com.doyak.reflector.presentation;

import com.doyak.reflector.buisiness.APIResponse;
import com.doyak.reflector.buisiness.UserDto;
import com.doyak.reflector.buisiness.repository.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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


}
