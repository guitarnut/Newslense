package com.service;

import com.db.SessionRepository;
import com.model.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Component
public class SessionService {
    private final SessionRepository sessionRepository;
    private final CookieService cookieService;

    @Autowired
    public SessionService(SessionRepository sessionRepository, CookieService cookieService) {
        this.sessionRepository = sessionRepository;
        this.cookieService = cookieService;
    }

    public Optional<Session> getUserSession(HttpServletRequest request) {
        return findSessionWithCookieValue(request);
    }

    public void saveUserSession(Session session) {
        sessionRepository.save(session);
    }

    public boolean deleteSession(HttpServletRequest request, HttpServletResponse response) {
        final Optional<Cookie> sessionCookie = cookieService.getSessionCookie(request);
        if (sessionCookie.isPresent()) {
            cookieService.expireCookie(sessionCookie.get());
            response.addCookie(sessionCookie.get());
            final Optional<Session> session = findSessionWithCookieValue(request);
            if (session.isPresent()) {
                sessionRepository.delete(session.get());
                return true;
            }
        }
        return false;
    }

    private Optional<Session> findSessionWithCookieValue(HttpServletRequest request) {
        final Optional<Cookie> sessionCookie = cookieService.getSessionCookie(request);
        if (sessionCookie.isPresent()) {
            final Session session = sessionRepository.findBySessionId(sessionCookie.get().getValue());
            if (session != null) {
                return Optional.of(session);
            }
        }
        return Optional.empty();
    }
}
