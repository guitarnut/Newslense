package com.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

/**
 * Created by guitarnut on 12/9/17.
 */

@Document
public class Headline {
    @Id
    private String id;
    private String headline;
    private String source;
    private String link;
    private int likes;
    private int views;
    private ArrayList<String> likedby;
    private long date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public ArrayList getLikedby() {
        return likedby;
    }

    public void setLikedby(ArrayList likedby) {
        this.likedby = likedby;
    }

    @Override
    public String toString() {
        return "Headline{" +
            "id='" + id + '\'' +
            ", headline='" + headline + '\'' +
            ", source='" + source + '\'' +
            ", link='" + link + '\'' +
            ", likes=" + likes +
            ", views=" + views +
            ", likedby=" + likedby +
            ", date=" + date +
            '}';
    }
}