package com.xtrendence.aut;

public class RestaurantAdapter {
    private String json;
    public RestaurantAdapter(String json) {
        this.json = json;
    }

    public Restaurant[] adapt() {
        System.out.println(json);
        return null;
    }
}
