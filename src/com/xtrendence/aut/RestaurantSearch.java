package com.xtrendence.aut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static com.xtrendence.aut.Utils.listToArray;
import static com.xtrendence.aut.Utils.sortArrayDescending;

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
        hotels.put("manhattan", new double[]{40.752831, -73.985748});
        hotels.put("queens", new double[]{40.753990, -73.949240});
        hotels.put("brooklyn", new double[]{40.689510, -73.988100});
        this.hotels = hotels;
    }

    public double[] getHotelCoordinatesByNeighborhood(String neighborhood) {
        return hotels.get(neighborhood.toLowerCase());
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

    public Restaurant[] getByNeighborhood(String neighborhood) {
        List<Restaurant> list = new ArrayList<>();
        for(Restaurant restaurant : this.restaurants) {
            if(restaurant.getNeighborhood().equalsIgnoreCase(neighborhood)) {
                list.add(restaurant);
            }
        }
        return listToArray(list);
    }

    public Restaurant[] getByRating(double rating) {
        List<Restaurant> list = new ArrayList<>();
        for(Restaurant restaurant : this.restaurants) {
            if(restaurant.getAverageRating() >= rating) {
                list.add(restaurant);
            }
        }
        return listToArray(list);
    }

    public Restaurant[] getByNeighborhoodAndRating(String neighborhood, double rating) {
        List<Restaurant> list = new ArrayList<>();
        Restaurant[] neighborhoodRestaurants = getByNeighborhood(neighborhood);
        for(Restaurant restaurant : neighborhoodRestaurants) {
            if(restaurant.getAverageRating() >= rating) {
                list.add(restaurant);
            }
        }
        return listToArray(list);
    }

    public Restaurant[] getByNeighborhoodAndSortByScore(String neighborhood) {
        List<Restaurant> list = new ArrayList<>();
        Restaurant[] neighborhoodRestaurants = getByNeighborhood(neighborhood);
        int[] scores = new int[neighborhoodRestaurants.length];
        for(int i = 0; i < neighborhoodRestaurants.length; i++) {
            scores[i] = neighborhoodRestaurants[i].getScore();
        }
        scores = sortArrayDescending(scores);
        for(int i = 0; i < scores.length; i++) {
            for(Restaurant restaurant : neighborhoodRestaurants) {
                if(restaurant.getScore() >= scores[i] && !list.contains(restaurant)) {
                    list.add(restaurant);
                }
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
