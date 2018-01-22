package com.db;

import com.model.Headline;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by guitarnut on 12/9/17.
 */
@Repository
public interface HeadlineRepository extends MongoRepository<Headline, String> {
    Headline findOne(String id);
    List<Headline> findOneBySourceAndHeadline(String source, String headline);
    Page<Headline> findBySource(String source, Pageable pageable);
}
