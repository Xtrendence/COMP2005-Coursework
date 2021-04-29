package com.xtrendence.aut;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.xtrendence.aut.Utils.listToArray;

public class RestaurantSearch {
    private HashMap<String, double[]> hotels;
    private String data;
    private Restaurant[] restaurants;

    public RestaurantSearch() {
        setHotels();
    }

    public RestaurantSearch(String data, Restaurant[] restaurants) {
        setHotels();
        this.restaurants = restaurants;
    }

    public void setHotels() {
        HashMap<String, double[]> hotels = new HashMap<>();
        hotels.put("Manhattan", new double[]{40.752831, -73.985748});
        hotels.put("Queens", new double[]{40.753990, -73.949240});
        hotels.put("Brooklyn", new double[]{40.689510, -73.988100});
        this.hotels = hotels;
    }

    public int loadRestaurants() throws Exception {
        HttpLib httpLib = new HttpLib();
        Response response = httpLib.call("http://intelligent-social-robots-ws.com/restaurant-data.json");
        int code = response.getCode();
        String json = response.getData();
        if(code == 200 && json != null && !json.equals("")) {
            this.data = json;
            this.restaurants = new RestaurantAdapter(json).adapt();
        }
        return code;
    }

    public Restaurant[] getByCuisine(String cuisine) {
        List<Restaurant> list = new ArrayList<>();
        for(Restaurant restaurant : this.restaurants) {
            if(restaurant.getCuisine().equalsIgnoreCase(cuisine)) {
                list.add(restaurant);
            }
        }
        return listToArray(list);
    }

    public String getData() {
        return data;
    }

    public Restaurant[] getRestaurants() {
        return restaurants;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setRestaurants(Restaurant[] restaurants) {
        this.restaurants = restaurants;
    }
}
