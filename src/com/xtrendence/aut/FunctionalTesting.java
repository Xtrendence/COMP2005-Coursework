package com.xtrendence.aut;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class FunctionalTesting {
    boolean mockTest;
    String data;
    Restaurant[] restaurants;

    public FunctionalTesting(boolean mockTest) {
        this.mockTest = mockTest;
    }

    public void loadRestuarants() throws Exception {
        int code;
        String json;

        Testing.outputFunctional("Fetching JSON...");

        if(mockTest) {
            MockHttpLib mockHttpLib = new MockHttpLib();
            Response response = mockHttpLib.call("");
            code = response.getCode();
            json = response.getData();
        } else {
            RestaurantSearch restaurantSearch = new RestaurantSearch();
            code = restaurantSearch.loadRestaurants();
            json = restaurantSearch.getData();
        }

        if(code == 200) {
            if (json != null && !json.equals("")) {
                Testing.outputFunctional("Fetched JSON");
                Testing.outputFunctional(json.substring(0, 127) + "...\n(Only showing first 128 characters)");
                Testing.outputFunctional("Parsing JSON...");
                this.restaurants = new RestaurantAdapter(json).adapt();
                this.data = json;
                if(this.restaurants.length > 0) {
                    String[] names = new String[restaurants.length];
                    for(int i = 0; i < this.restaurants.length; i++) {
                        names[i] = "\n" + restaurants[i].getName() + " (Average Rating: " + restaurants[i].getAverageRating() + ")";
                    }
                    Testing.outputFunctional("Restaurants Found (" + this.restaurants.length + "): " + String.join(", ", names));
                } else {
                    Testing.outputFunctional("Couldn't parse JSON.");
                }
            } else {
                Testing.outputFunctional("Couldn't fetch JSON.");
            }
        }
    }

    public void testGetByCuisine() {
        RestaurantSearch restaurantSearch = new RestaurantSearch(this.data, this.restaurants);
        Restaurant[] restaurantArray = restaurantSearch.getByCuisine("Asian");
        String[] names = new String[restaurantArray.length];
        for(int i = 0; i < restaurantArray.length; i++) {
            names[i] = restaurantArray[i].getName();
        }
        Testing.outputFunctional("Testing Method: getByCuisine(\"Asian\")");
        Testing.outputFunctional("Expected: Mission Chinese Food, Kang Ho Dong Baekjeong, Mu Ramen");
        Testing.outputFunctional("Actual: " + String.join(", ", names));
    }
}
