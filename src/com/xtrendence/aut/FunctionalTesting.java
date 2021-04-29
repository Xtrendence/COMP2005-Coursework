package com.xtrendence.aut;

import org.junit.Test;

import java.awt.*;
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

        Testing.outputFunctional("Fetching JSON...", Color.BLUE);

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
                Testing.outputFunctional("Fetched JSON", Color.BLUE);
                Testing.outputFunctional(json.substring(0, 127) + "...\n(Only showing first 128 characters)", Color.BLACK);
                Testing.outputFunctional("Parsing JSON...", Color.BLUE);
                this.restaurants = new RestaurantAdapter(json).adapt();
                this.data = json;
                if(this.restaurants.length > 0) {
                    String[] names = new String[restaurants.length];
                    for(int i = 0; i < this.restaurants.length; i++) {
                        names[i] = restaurants[i].getName() + " (Average Rating: " + restaurants[i].getAverageRating() + ")";
                    }

                    String expected = "Mission Chinese Food (Average Rating: 3.7), Emily (Average Rating: 4.3), Kang Ho Dong Baekjeong (Average Rating: 3.7), Katz's Delicatessen (Average Rating: 4.0), Roberta's Pizza (Average Rating: 4.0), Hometown BBQ (Average Rating: 4.0), Superiority Burger (Average Rating: 4.3), The Dutch (Average Rating: 4.0), Mu Ramen (Average Rating: 4.0), Casa Enrique (Average Rating: 4.7)";
                    String actual = String.join(", ", names);

                    Testing.outputFunctional("Expected: " + expected, Color.BLACK);
                    Testing.outputFunctional("Actual: " + actual, Color.BLACK);
                    if(actual.equalsIgnoreCase(expected)) {
                        Testing.outputFunctional("Test Passed", new Color(0, 150, 0));
                    } else {
                        Testing.outputFunctional("Test Failed", Color.RED);
                    }
                } else {
                    Testing.outputFunctional("Couldn't parse JSON.", Color.RED);
                }
            } else {
                Testing.outputFunctional("Couldn't fetch JSON.", Color.RED);
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

        String expected = "Mission Chinese Food, Kang Ho Dong Baekjeong, Mu Ramen";
        String actual = String.join(", ", names);

        Testing.outputFunctional("------------", Color.BLACK);
        Testing.outputFunctional("Testing Method: getByCuisine(\"Asian\")", Color.BLUE);
        Testing.outputFunctional("Expected: " + expected, Color.BLACK);
        Testing.outputFunctional("Actual: " + actual, Color.BLACK);
        if(actual.equalsIgnoreCase(expected)) {
            Testing.outputFunctional("Test Passed", new Color(0, 150, 0));
        } else {
            Testing.outputFunctional("Test Failed", Color.RED);
        }
    }

    public void testGetByNeighborhood() {
        RestaurantSearch restaurantSearch = new RestaurantSearch(this.data, this.restaurants);
        Restaurant[] restaurantArray = restaurantSearch.getByNeighborhood("Manhattan");
        String[] names = new String[restaurantArray.length];
        for(int i = 0; i < restaurantArray.length; i++) {
            names[i] = restaurantArray[i].getName();
        }

        String expected = "Mission Chinese Food, Kang Ho Dong Baekjeong, Katz's Delicatessen, Superiority Burger, The Dutch";
        String actual = String.join(", ", names);

        Testing.outputFunctional("------------", Color.BLACK);
        Testing.outputFunctional("Testing Method: getByNeighborhood(\"Manhattan\")", Color.BLUE);
        Testing.outputFunctional("Expected: " + expected, Color.BLACK);
        Testing.outputFunctional("Actual: " + actual, Color.BLACK);
        if(actual.equalsIgnoreCase(expected)) {
            Testing.outputFunctional("Test Passed", new Color(0, 150, 0));
        } else {
            Testing.outputFunctional("Test Failed", Color.RED);
        }
    }

    public void testGetByRating() {
        RestaurantSearch restaurantSearch = new RestaurantSearch(this.data, this.restaurants);
        Restaurant[] restaurantArray = restaurantSearch.getByRating(4.5);
        String[] names = new String[restaurantArray.length];
        for(int i = 0; i < restaurantArray.length; i++) {
            names[i] = restaurantArray[i].getName();
        }

        String expected = "Casa Enrique";
        String actual = String.join(", ", names);

        Testing.outputFunctional("------------", Color.BLACK);
        Testing.outputFunctional("Testing Method: getByRating(4.5)", Color.BLUE);
        Testing.outputFunctional("Expected: " + expected, Color.BLACK);
        Testing.outputFunctional("Actual: " + actual, Color.BLACK);
        if(actual.equalsIgnoreCase(expected)) {
            Testing.outputFunctional("Test Passed", new Color(0, 150, 0));
        } else {
            Testing.outputFunctional("Test Failed", Color.RED);
        }
    }

    public void testGetByNeighborhoodAndRating() {
        RestaurantSearch restaurantSearch = new RestaurantSearch(this.data, this.restaurants);
        Restaurant[] restaurantArray = restaurantSearch.getByNeighborhoodAndRating("Manhattan", 4);
        String[] names = new String[restaurantArray.length];
        for(int i = 0; i < restaurantArray.length; i++) {
            names[i] = restaurantArray[i].getName();
        }

        String expected = "Katz's Delicatessen, Superiority Burger, The Dutch";
        String actual = String.join(", ", names);

        Testing.outputFunctional("------------", Color.BLACK);
        Testing.outputFunctional("Testing Method: getByNeighborhoodAndRating(\"Manhattan\", 4)", Color.BLUE);
        Testing.outputFunctional("Expected: " + expected, Color.BLACK);
        Testing.outputFunctional("Actual: " + actual, Color.BLACK);
        if(actual.equalsIgnoreCase(expected)) {
            Testing.outputFunctional("Test Passed", new Color(0, 150, 0));
        } else {
            Testing.outputFunctional("Test Failed", Color.RED);
        }
    }

    public void testGetByNeighborhoodAndSortByScore() {
        RestaurantSearch restaurantSearch = new RestaurantSearch(this.data, this.restaurants);
        Restaurant[] restaurantArray = restaurantSearch.getByNeighborhoodAndSortByScore("Manhattan");
        String[] names = new String[restaurantArray.length];
        for(int i = 0; i < restaurantArray.length; i++) {
            names[i] = restaurantArray[i].getName();
        }

        String expected = "Kang Ho Dong Baekjeong, Katz's Delicatessen, Mission Chinese Food, The Dutch, Superiority Burger";
        String actual = String.join(", ", names);

        Testing.outputFunctional("------------", Color.BLACK);
        Testing.outputFunctional("Testing Method: getByNeighborhoodAndSortByScore(\"Manhattan\")", Color.BLUE);
        Testing.outputFunctional("Expected: " + expected, Color.BLACK);
        Testing.outputFunctional("Actual: " + actual, Color.BLACK);
        if(actual.equalsIgnoreCase(expected)) {
            Testing.outputFunctional("Test Passed", new Color(0, 150, 0));
        } else {
            Testing.outputFunctional("Test Failed", Color.RED);
        }
    }
}
