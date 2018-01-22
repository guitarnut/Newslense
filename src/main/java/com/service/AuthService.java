package com.service;

import com.constants.UserRole;
import com.google.common.base.Strings;
import com.google.common.hash.Hashing;
import com.model.Session;
import com.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Optional;

@Component
public class AuthService {
    private final SessionService sessionService;
    private final UserService userService;
    private final CookieService cookieService;

    @Autowired
    public AuthService(SessionService sessionService, UserService userService, CookieService cookieService) {
        this.sessionService = sessionService;
        this.userService = userService;
        this.cookieService = cookieService;
    }

    public Optional<User> getAuthorizedUser(HttpServletRequest request) {
        Optional<Session> session = sessionService.getUserSession(request);
        if (session.isPresent()) {
            Optional<User> user = userService.findOneByUsername(session.get().getUsername());
            if (user.isPresent()) {
                return user;
            }
        }
        return Optional.empty();
    }

    public boolean isAuthorized(HttpServletRequest request, UserRole userRole) {
        Optional<Session> session = sessionService.getUserSession(request);
        if (session.isPresent()) {
            Optional<User> user = userService.findOneByUsername(session.get().getUsername());
            if (user.isPresent() && user.get().getRoles().contains(userRole)) {
                // refresh session TTL
                session.get().setTimestamp(new Date());
                sessionService.saveUserSession(session.get());
                return true;
            }
        }
        return false;
    }

    public boolean loginNewUser(User user, HttpServletRequest request, HttpServletResponse response) {
        return beginSession(user, request, response);
    }

    public boolean loginUser(HttpServletRequest request, HttpServletResponse response) {
        if (Strings.isNullOrEmpty(request.getParameterMap().get("username")[0]) || Strings.isNullOrEmpty(request.getParameterMap().get("password")[0])) {
            return false;
        }

        final Optional<User> user = userService.findOne(
            request.getParameterMap().get("username")[0],
            request.getParameterMap().get("password")[0]
        );

        if(user.isPresent()) {
            return beginSession(user.get(), request, response);
        } else {
            return false;
        }
    }

    private boolean beginSession(User user, HttpServletRequest request, HttpServletResponse response) {
        try {
            final String sessionId = generateHash(user.getUsername() + String.valueOf(Math.random() * 1e10));
            final Session session = new Session();
            session.setSessionId(sessionId);
            session.setTimestamp(new Date());
            session.setUsername(user.getUsername());

            if (!Strings.isNullOrEmpty(request.getHeader("X-Forwarded-For"))) {
                session.setIp(request.getHeader("X-Forwarded-For"));
            } else {
                session.setIp(request.getRemoteAddr());
            }

            Cookie sessionCookie = cookieService.generateNewSessionCookie(sessionId);
            response.addCookie(sessionCookie);
            sessionService.saveUserSession(session);
            return true;
        } catch (NoSuchAlgorithmException ex) {
            // noop
        }
        return false;
    }

    public boolean logoutUser(HttpServletRequest request, HttpServletResponse response) {
        return sessionService.deleteSession(request, response);
    }

    private String generateHash(String seed) throws NoSuchAlgorithmException {
        final String sha256hex = Hashing.sha256()
            .hashString(seed, StandardCharsets.UTF_8)
            .toString();
        return sha256hex;
    }
}
