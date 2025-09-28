package com.doyak.reflector.util;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.doyak.reflector.payload.code.status.ErrorStatus;
import com.doyak.reflector.payload.exception.handler.UserHandler;

@Component
public class CookieUtil {
	private final int defaultRefreshTokenExpire = 1000 * 60 * 60 * 24;
	
	public String getRefreshToken(HttpServletRequest request) {
        return Arrays.stream(Optional.ofNullable(request.getCookies()).orElse(new Cookie[0]))
                     .filter(c -> "refreshToken".equals(c.getName()))
                     .findFirst()
                     .map(Cookie::getValue)
                     .orElseThrow(() -> new UserHandler(ErrorStatus.TOKEN_EXPIRED));
    }

    public void addRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
        addRefreshTokenCookie(response, refreshToken, defaultRefreshTokenExpire);
    }

    public void addRefreshTokenCookie(HttpServletResponse response, String refreshToken, long expireMs) {
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge((int)(expireMs / 1000));
        response.addCookie(cookie);
    }

}