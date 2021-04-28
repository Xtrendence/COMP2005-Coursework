package com.xtrendence.aut;

import java.util.Date;
import java.util.HashMap;

public class Restaurant {
    private int id;
    private String name;
    private int score;
    private String neighborhood;
    private String photograph;
    private String address;
    private float[] coordinates;
    private String cuisine;
    private HashMap<String, Date> hours;
    private Review[] reviews;

    public Restaurant(int id, String name, int score, String neighborhood, String photograph, String address, float[] coordinates, String cuisine, HashMap<String, Date> hours, Review[] reviews) {
        this.id = id;
        this.name = name;
        this.score = score;
        this.neighborhood = neighborhood;
        this.photograph = photograph;
        this.address = address;
        this.coordinates = coordinates;
        this.cuisine = cuisine;
        this.hours = hours;
        this.reviews = reviews;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public String getPhotograph() {
        return photograph;
    }

    public String getAddress() {
        return address;
    }

    public float[] getCoordinates() {
        return coordinates;
    }

    public String getCuisine() {
        return cuisine;
    }

    public HashMap<String, Date> getHours() {
        return hours;
    }

    public Review[] getReviews() {
        return reviews;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public void setPhotograph(String photograph) {
        this.photograph = photograph;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCoordinates(float[] coordinates) {
        this.coordinates = coordinates;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public void setHours(HashMap<String, Date> hours) {
        this.hours = hours;
    }

    public void setReviews(Review[] reviews) {
        this.reviews = reviews;
    }
}
