package com.xtrendence.aut;

import java.util.Date;

public class Review {
    private String name;
    private Date date;
    private int rating;
    private String comments;

    public Review(String name, Date date, int rating, String comments) {
        this.name = name;
        this.date = date;
        this.rating = rating;
        this.comments = comments;
    }

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    public int getRating() {
        return rating;
    }

    public String getComments() {
        return comments;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
