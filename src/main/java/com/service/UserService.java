package com.service;

import com.constants.UserRole;
import com.db.UserRepository;
import com.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Created by guitarnut on 12/9/17.
 */

@Component
public class UserService {
    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public boolean registerNewUser(User user, HttpServletRequest request, HttpServletResponse response) {
        user.setRoles(Arrays.asList(UserRole.USER));
        user.setEnabled(1);
        repository.save(user);
        return true;
    }

    public Optional<User> findOneByUsername(String username) {
        User user = repository.findOneByUsername(username);
        if (user != null) {
            return Optional.of(user);
        } else {
            return Optional.empty();
        }
    }

    public Optional<User> findOne(String username, String password) {
        List<User> results = repository.findByUsernameAndPassword(username, password);
        if (results.size() == 1) {
            return Optional.of(results.get(0));
        } else {
            return Optional.empty();
        }
    }

    public void saveUser(User user) {
        repository.save(user);
    }

    public boolean updateLikedHeadlines(Optional<User> user, String id) {
        if (user.isPresent()) {
            if(!user.get().getLikedHeadlines().contains(id)) {
                user.get().getLikedHeadlines().add(id);
                saveUser(user.get());
                return true;
            }
        }
        return false;
    }

    public boolean updateViewedHeadlines(Optional<User> user, String id) {
        if (user.isPresent()) {
            if(!user.get().getViewedHeadlines().contains(id)) {
                user.get().getViewedHeadlines().add(id);
                saveUser(user.get());
                return true;
            }
        }
        return false;
    }

    public boolean updateSavedHeadlines(Optional<User> user, String id) {
        if (user.isPresent()) {
            if(!user.get().getSavedHeadlines().contains(id)) {
                user.get().getSavedHeadlines().add(id);
                saveUser(user.get());
                return true;
            }
        }
        return false;
    }

}
