package com.controller;

import com.constants.UserRole;
import com.model.Headline;
import com.service.AuthService;
import com.service.HeadlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by guitarnut on 12/9/17.
 */
@RestController
@CrossOrigin
@RequestMapping("/api/headline")
public class HeadlineController {

    private final HeadlineService headlineService;
    private final AuthService authService;

    @Autowired
    public HeadlineController(HeadlineService headlineService, AuthService authService) {
        this.headlineService = headlineService;
        this.authService = authService;
    }

    @RequestMapping("/page/{page}")
    public Page<Headline> handleFindBySource(@PathVariable("page") int page,
                                             HttpServletRequest request, HttpServletResponse response) {
        if (isAuthorized(request)) {
            return headlineService.getAll(page);
        } else {
            response.setStatus(401);
            return null;
        }
    }

    @RequestMapping("/{source}/page/{page}")
    public Page<Headline> handleFindBySource(@PathVariable("source") String source, @PathVariable("page") int page,
                                             HttpServletRequest request, HttpServletResponse response) {
        if (isAuthorized(request)) {
            return headlineService.getAllBySource(source, page);
        } else {
            response.setStatus(401);
            return null;
        }
    }

    @RequestMapping(value = "/like/{id}", method = RequestMethod.GET)
    public void handleLike(@PathVariable("id") String id,
                           HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (isAuthorized(request)) {
            headlineService.incrementLike(id);
        } else {
            response.setStatus(401);
        }
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public void handleView(@PathVariable("id") String id,
                           HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (isAuthorized(request)) {
            headlineService.incrementView(id);
        } else {
            response.setStatus(401);
        }
    }

    private boolean isAuthorized(HttpServletRequest request) {
        return authService.isAuthorized(request, UserRole.USER);
    }

}
