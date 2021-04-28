package com.xtrendence.aut;

public class RestaurantSearch {
    private Restaurant[] restaurants;

    public RestaurantSearch(Restaurant[] restaurants) {
        this.restaurants = restaurants;
    }

    public Restaurant[] getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(Restaurant[] restaurants) {
        this.restaurants = restaurants;
    }
}
