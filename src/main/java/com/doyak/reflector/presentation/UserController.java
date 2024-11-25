package com.doyak.reflector.presentation;

import com.doyak.reflector.buisiness.APIResponse;
import com.doyak.reflector.buisiness.UserDto;
import com.doyak.reflector.buisiness.repository.UserService;
import com.doyak.reflector.domain.User;
import com.doyak.reflector.infrastructure.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
    @Autowired
    private UserRepository userRepository;

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

    @GetMapping("login")
    public ResponseEntity<APIResponse<?>> login(@RequestBody UserDto userDto, HttpServletRequest request) {
        try {
            UserDto.UserId loginUser = userService.login(userDto);
            request.getSession().setAttribute("LOGIN_USER", loginUser.getUser_id()); // 세션 생성 및 세션 값 저장. servlet에서 제공하는 세션의 경우 servlet이 자동으로 쿠키에 정보를 넘겨준다.
            return ResponseEntity.ok().body(APIResponse.successAPI("Login successful.", loginUser));
            //return "redirect:/";
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(APIResponse.errorAPI(e.getMessage()));
            // return "redirect:/";
        }
    }

    @GetMapping("/")
    public String home(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // 세션이 없어도 문제가 없으므로 false. 세션이 존재하지 않을시 null 반환
        if (session == null) {
            return "login";
        }
        String userId = (String) session.getAttribute("LOGIN_USER");
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            return "login";
        }
        return "home";
    }

    @PostMapping("logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        session.invalidate();

        return "redirect:/";
    }

}
