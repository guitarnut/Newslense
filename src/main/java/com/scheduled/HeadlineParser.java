package com.scheduled;

import com.db.HeadlineRepository;
import com.google.common.base.Strings;
import com.html.Html;
import com.model.Headline;
import com.model.Site;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

/**
 * Created by guitarnut on 12/9/17.
 */
@Component
public class HeadlineParser {
    private static final Logger LOG = LoggerFactory.getLogger(HeadlineParser.class);
    private final HeadlineRepository repository;
    private final SiteRefresher siteRefresher;

    @Autowired
    public HeadlineParser(HeadlineRepository repository, SiteRefresher siteRefresher) {
        this.repository = repository;
        this.siteRefresher = siteRefresher;
    }

    @Scheduled(fixedDelay = 1000 * 60 * 10)
    public void scrapeHeadlines() {
        Map<String, Site> sites = siteRefresher.getSites();

        sites.forEach((k, v) -> {
            try {
                Document document = Jsoup.connect(v.getUrl())
                    .userAgent(v.getUseragent())
                    .timeout(v.getTimeout())
                    .cookie(v.getCookiename(), v.getCookievalue())
                    .referrer(v.getReferrer())
                    .get();

                Optional<Headline> headline = generateHeadline(v, document.select(v.getSelector()));

                if(headline.isPresent()) {
                    if(repository.findOneBySourceAndHeadline(headline.get().getSource(), headline.get().getHeadline()).isEmpty()) {
                        repository.save(headline.get());
                        LOG.info("Parsed and saved headline for {}", v.getName());
                    }
                } else {
                    LOG.info("No headline found for {}", v.getName());
                }
            } catch (IOException ex) {
                LOG.error(ex.toString());
            }
        });
    }

    private Optional<Headline> generateHeadline(Site site, Elements element) {
        final Headline headline = new Headline();
        if (element.size() > 0 && !Strings.isNullOrEmpty(element.get(0).text())) {
            headline.setHeadline(element.get(0).text().trim());
            headline.setSource(site.getName());
            headline.setDate(new Date().getTime());
            if (element.get(0).hasAttr(Html.HREF_ATTRIBUTE.getValue())) {
                headline.setLink(element.get(0).attr(Html.HREF_ATTRIBUTE.getValue()));
                if(headline.getLink().startsWith("/")) {
                    headline.setLink(site.getUrl() + headline.getLink());
                }
            } else {
                headline.setLink("");
            }
            return Optional.of(headline);
        } else {
            return Optional.empty();
        }
    }
}
