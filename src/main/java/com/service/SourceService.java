package com.service;

import com.db.SiteRepository;
import com.model.Site;
import com.model.Source;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SourceService {
    private final SiteRepository repository;

    @Autowired
    public SourceService(SiteRepository repository) {
        this.repository = repository;
    }

    public List<Source> getAll() {
        final List<Site> sites = repository.findAll();
        final List<Source> sources = new ArrayList<>();
        sites.forEach(site->{
            if(site.getEnabled() == 1) {
                Source source = new Source();
                source.setName(site.getName());
                sources.add(source);
            }
        });
        return sources;
    }
}
