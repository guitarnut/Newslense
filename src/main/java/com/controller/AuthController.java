package com.controller;

import com.model.User;
import com.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by guitarnut on 12/10/17.
 */
@Controller
@CrossOrigin
@RequestMapping("/api")
public class AuthController {
    private final AuthService service;

    @Autowired
    public AuthController(AuthService service) {
        this.service = service;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public void login(@RequestBody User user, HttpServletRequest request, HttpServletResponse response) {
        if (service.loginUser(user, request, response)) {
            response.setStatus(200);
        } else {
            response.setStatus(400);
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        if (service.logoutUser(request, response)) {
            response.setStatus(200);
        } else {
            response.setStatus(401);
        }
    }

}
