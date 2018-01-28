package com.model;

import com.constants.UserRole;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guitarnut on 12/9/17.
 */

@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String email;
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private List<UserRole> roles = new ArrayList<>();
    private List<String> ip = new ArrayList<>();
    private List<String> savedHeadlines = new ArrayList<>();
    private List<String> likedHeadlines = new ArrayList<>();
    private List<String> viewedHeadlines = new ArrayList<>();
    private int zip;
    private int enabled;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public List<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(List<UserRole> roles) {
        this.roles = roles;
    }

    public List<String> getIp() {
        return ip;
    }

    public void setIp(List<String> ip) {
        this.ip = ip;
    }

    public List<String> getSavedHeadlines() {
        return savedHeadlines;
    }

    public void setSavedHeadlines(List<String> savedHeadlines) {
        this.savedHeadlines = savedHeadlines;
    }

    public List<String> getLikedHeadlines() {
        return likedHeadlines;
    }

    public void setLikedHeadlines(List<String> likedHeadlines) {
        this.likedHeadlines = likedHeadlines;
    }

    public List<String> getViewedHeadlines() {
        return viewedHeadlines;
    }

    public void setViewedHeadlines(List<String> viewedHeadlines) {
        this.viewedHeadlines = viewedHeadlines;
    }

    @Override
    public String toString() {
        return "User{" +
            "id='" + id + '\'' +
            ", email='" + email + '\'' +
            ", firstname='" + firstname + '\'' +
            ", lastname='" + lastname + '\'' +
            ", username='" + username + '\'' +
            ", password='" + password + '\'' +
            ", roles=" + roles +
            ", ip=" + ip +
            ", savedHeadlines=" + savedHeadlines +
            ", likedHeadlines=" + likedHeadlines +
            ", viewedHeadlines=" + viewedHeadlines +
            ", zip=" + zip +
            ", enabled=" + enabled +
            '}';
    }
}
