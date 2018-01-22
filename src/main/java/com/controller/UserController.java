package com.controller;

import com.model.User;
import com.service.AuthService;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by guitarnut on 12/9/17.
 */
@CrossOrigin
@RestController
@RequestMapping("/api")
public class UserController {

    private UserService userService;
    private final AuthService authService;

    @Autowired
    public UserController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public void signup(User user, HttpServletRequest request, HttpServletResponse response) {
        if (userService.registerNewUser(user, request, response) && authService.loginNewUser(user, request, response)) {
            response.setStatus(200);
        } else {
            response.setStatus(400);
        }
    }

}
