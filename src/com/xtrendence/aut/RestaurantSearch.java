package com.xtrendence.aut;

import java.util.HashMap;

public class RestaurantSearch {
    private HashMap<String, double[]> hotels;
    private Restaurant[] restaurants;

    public RestaurantSearch(Restaurant[] restaurants) {
        HashMap<String, double[]> hotels = new HashMap<>();
        hotels.put("Manhattan", new double[]{40.752831, -73.985748});
        hotels.put("Queens", new double[]{40.753990, -73.949240});
        hotels.put("Brooklyn", new double[]{40.689510, -73.988100});

        this.hotels = hotels;
        this.restaurants = restaurants;
    }

    public Restaurant[] getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(Restaurant[] restaurants) {
        this.restaurants = restaurants;
    }
}
