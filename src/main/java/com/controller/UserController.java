package com.controller;

import com.constants.UserRole;
import com.model.User;
import com.service.AuthService;
import com.service.HeadlineService;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * Created by guitarnut on 12/9/17.
 */
@CrossOrigin
@RestController
@RequestMapping("/api")
public class UserController {

    private UserService userService;
    private final AuthService authService;
    private final HeadlineService headlineService;

    @Autowired
    public UserController(UserService userService, AuthService authService, HeadlineService headlineService) {
        this.userService = userService;
        this.authService = authService;
        this.headlineService = headlineService;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public void signup(@RequestBody User user, HttpServletRequest request, HttpServletResponse response) {
        if (userService.registerNewUser(user, request, response) && authService.loginNewUser(user, request, response)) {
            response.setStatus(200);
        } else {
            response.setStatus(400);
        }
    }

    @RequestMapping("/profile")
    public User getProfile(HttpServletRequest request, HttpServletResponse response) {
        if (authService.isAuthorized(request, UserRole.USER)) {
            Optional<User> user = authService.getAuthorizedUser(request);
            if (user.isPresent()) {
                final User thisUser = user.get();
                thisUser.setLikedHeadlines(headlineService.getAllHeadlineTitlesById(thisUser.getLikedHeadlines()));
                thisUser.setViewedHeadlines(headlineService.getAllHeadlineTitlesById(thisUser.getViewedHeadlines()));
                return user.get();
            }
        }
        response.setStatus(400);
        return null;
    }
}
