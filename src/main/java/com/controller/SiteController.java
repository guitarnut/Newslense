package com.controller;

import com.model.Source;
import com.service.SourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/source")
public class SiteController {
    private final SourceService service;

    @Autowired
    public SiteController(SourceService service) {
        this.service = service;
    }

    @RequestMapping("/all")
    public List<Source> getAll() {
        return service.getAll();
    }
}
