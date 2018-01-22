package com.db;

import com.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by guitarnut on 12/9/17.
 */
@Repository
public interface UserRepository extends MongoRepository<User, String> {
    User findOneByUsername(String username);
    List<User> findByUsernameAndPassword(String username, String password);
}
