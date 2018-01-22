package com.service;

import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Component
public class CookieService {
    private static final String COOKIE_NAME = "nls";
    private static final int EXPIRY = 600;

    public Cookie generateNewSessionCookie(String sessionId) {
        final Cookie cookie = new Cookie(COOKIE_NAME, sessionId);
        cookie.setMaxAge(EXPIRY);
        cookie.setPath("/");
        cookie.setDomain("localhost");
        return cookie;
    }

    public Optional<Cookie> getSessionCookie(HttpServletRequest request) {
        if(request.getCookies() != null) {
            for(Cookie cookie : request.getCookies()) {
                if(cookie.getName().equals(COOKIE_NAME)) {
                    return Optional.of(cookie);
                }
            }
        }
        return Optional.empty();
    }

    public void expireCookie(Cookie cookie) {
        cookie.setMaxAge(0);
    }

}
