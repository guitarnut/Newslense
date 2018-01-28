package com.service;

import com.db.HeadlineRepository;
import com.model.Headline;
import org.apache.commons.lang3.text.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guitarnut on 12/9/17.
 */

@Component
public class HeadlineService {
    private final HeadlineRepository repository;

    private static Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "date"));

    @Autowired
    public HeadlineService(HeadlineRepository repository) {
        this.repository = repository;
    }

    public Page<Headline> getAll(int page) {
        return repository.findAll(new PageRequest(page, 25, sort));
    }

    public Page<Headline> getAllBySource(String source, int page) {
        String sourceName = WordUtils.capitalize(source.replaceAll("-", " "));
        return repository.findBySource(sourceName, new PageRequest(page, 25, sort));
    }

    public List<String> getAllHeadlineTitlesById(List<String> ids) {
        final List<String> titles = new ArrayList<>();
        ids.forEach(id->{
            final Headline headline = repository.findOne(id);
            if(headline != null) {
                titles.add(headline.getHeadline());
            }
        });
        return titles;
    }

    public void incrementLike(String id) {
        Headline headline = repository.findOne(id);
        if (headline != null) {
            int likes = headline.getLikes() + 1;
            headline.setLikes(likes);
            repository.save(headline);
        }
    }

    public void incrementView(String id) {
        Headline headline = repository.findOne(id);
        if (headline != null) {
            int views = headline.getViews() + 1;
            headline.setViews(views);
            repository.save(headline);
        }
    }
}