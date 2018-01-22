package com.scheduled;

import com.db.SiteRepository;
import com.model.Site;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by guitarnut on 12/9/17.
 */

@Component
public class SiteRefresher {

    private Logger LOG = LoggerFactory.getLogger(SiteRefresher.class);
    private Map<String, Site> sites = new ConcurrentHashMap<>();
    private final SiteRepository repository;

    @Autowired
    public SiteRefresher(SiteRepository repository) {
        this.repository = repository;
    }

    @Scheduled(fixedDelay = 1000 * 60 * 5, initialDelay = 0)
    private void refresh() {
        List<Site> results = repository.findAll();
        results.forEach(site -> {
            if(site.getEnabled() == 1) {
                sites.put(site.getName(), site);
            } else {
                if(sites.containsValue(site) || sites.containsKey(site.getName())) {
                    sites.remove(site);
                    LOG.info("Disabling {}", site.getName());
                }
            }
        });

        LOG.info("Updated {} sites", results.size());
    }

    public Map<String, Site> getSites() {
        return sites;
    }
}
