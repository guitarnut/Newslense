package com.db;

import com.model.Site;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by guitarnut on 12/9/17.
 */
@Repository
public interface SiteRepository extends MongoRepository<Site, String> {
}
