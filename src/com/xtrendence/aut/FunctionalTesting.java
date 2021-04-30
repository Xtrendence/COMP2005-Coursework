package com.xtrendence.aut;

import org.junit.Test;

import java.awt.*;
import java.lang.reflect.Method;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashMap;

import static com.xtrendence.aut.Utils.distanceBetween;
import static org.junit.Assert.assertEquals;

public class FunctionalTesting {
    public int overallMethods;
    public int testedMethods;
    String data;
    Restaurant[] restaurants;

    public FunctionalTesting() throws Exception {
        Class thisClass = FunctionalTesting.class;
        Method[] methods = thisClass.getDeclaredMethods();

        this.overallMethods = methods.length;
        this.testedMethods = 0;

        loadRestuarants();
    }

    @Test
    public void loadRestuarants() throws Exception {
        testedMethods++;

        RestaurantSearch restaurantSearch = new RestaurantSearch();
        Testing.outputText("Fetching JSON from " + restaurantSearch.api + "...", Color.BLUE);
        int code = restaurantSearch.loadRestaurants();
        String json = restaurantSearch.getData();

        if(code == 200) {
            if (json != null && !json.equals("")) {
                Testing.outputText("Fetched JSON", Color.BLUE);
                Testing.outputText(json.substring(0, 255) + "...\n(Only showing the first 256 characters)", Color.BLACK);
                Testing.outputText("Parsing JSON...", Color.BLUE);
                Testing.outputText("Checking Restaurant Names...", Color.BLUE);
                this.restaurants = new RestaurantAdapter(json).adapt();
                this.data = json;
                if(this.restaurants.length > 0) {
                    String[] names = new String[restaurants.length];
                    for(int i = 0; i < this.restaurants.length; i++) {
                        names[i] = restaurants[i].getName() + " (Average Rating: " + restaurants[i].getAverageRating() + ")";
                    }

                    String expected = "Mission Chinese Food (Average Rating: 3.7), Emily (Average Rating: 4.3), Kang Ho Dong Baekjeong (Average Rating: 3.7), Katz's Delicatessen (Average Rating: 4.0), Roberta's Pizza (Average Rating: 4.0), Hometown BBQ (Average Rating: 4.0), Superiority Burger (Average Rating: 4.3), The Dutch (Average Rating: 4.0), Mu Ramen (Average Rating: 4.0), Casa Enrique (Average Rating: 4.7)";
                    String actual = String.join(", ", names);

                    Testing.outputText("Expected: " + expected, Color.BLACK);
                    Testing.outputText("Actual: " + actual, Color.BLACK);

                    try {
                        assertEquals(expected, actual);
                        Testing.outputText("Test Passed", new Color(0, 150, 0));
                    } catch(AssertionError e) {
                        Testing.outputText("Test Failed", Color.RED);
                        e.printStackTrace();
                    }
                } else {
                    Testing.outputText("Couldn't parse JSON.", Color.RED);
                }
            } else {
                Testing.outputText("Couldn't fetch JSON.", Color.RED);
            }
        }
    }

    public void testGetByCuisine(String cuisine) {
        testedMethods++;

        RestaurantSearch restaurantSearch = new RestaurantSearch(this.data, this.restaurants);
        Restaurant[] restaurantArray = restaurantSearch.getByCuisine(cuisine);
        String[] names = new String[restaurantArray.length];
        for(int i = 0; i < restaurantArray.length; i++) {
            names[i] = restaurantArray[i].getName();
        }

        String response = String.join(", ", names);

        Testing.outputText("------------", Color.BLACK);
        Testing.outputText("Testing Method: getByCuisine(\"" + cuisine + "\")", Color.BLUE);
        Testing.outputText("Response: " + response, Color.BLACK);

        Testing.outputText("Test Finished", Color.DARK_GRAY);
    }

    public void testGetByCuisineAndNeighborhood(String neighborhood, String cuisine) {
        testedMethods++;

        RestaurantSearch restaurantSearch = new RestaurantSearch(this.data, this.restaurants);
        Restaurant[] restaurantArray = restaurantSearch.getByCuisineAndNeighborhood("Manhattan", "Asian");
        String[] names = new String[restaurantArray.length];
        for(int i = 0; i < restaurantArray.length; i++) {
            names[i] = restaurantArray[i].getName();
        }

        String response = String.join(", ", names);

        Testing.outputText("------------", Color.BLACK);
        Testing.outputText("Testing Method: getByCuisineAndNeighborhood(\"" + neighborhood + "\", \"" + cuisine + "\")", Color.BLUE);
        Testing.outputText("Response: " + response, Color.BLACK);

        Testing.outputText("Test Finished", Color.DARK_GRAY);
    }

    public void testGetByDayAndHour(String day, String hour) {
        testedMethods++;

        RestaurantSearch restaurantSearch = new RestaurantSearch(this.data, this.restaurants);
        Restaurant[] restaurantArray = restaurantSearch.getByDayAndHour(day, hour);
        String[] names = new String[restaurantArray.length];
        for(int i = 0; i < restaurantArray.length; i++) {
            HashMap<String, LocalTime[]> hours = restaurantArray[i].getHours();
            LocalTime[] operatingHours = hours.get(day);
            names[i] = restaurantArray[i].getName() + " (" + Arrays.toString(operatingHours) + ")";
        }

        String response = String.join(", ", names);

        Testing.outputText("------------", Color.BLACK);
        Testing.outputText("Testing Method: getByDayAndHour(\"" + day + "\", \"" + hour + "\")", Color.BLUE);
        Testing.outputText("Response: " + response, Color.BLACK);

        Testing.outputText("Test Finished", Color.DARK_GRAY);
    }

    public void testGetByNeighborhood(String neighborhood) {
        testedMethods++;

        RestaurantSearch restaurantSearch = new RestaurantSearch(this.data, this.restaurants);
        Restaurant[] restaurantArray = restaurantSearch.getByNeighborhood(neighborhood);
        String[] names = new String[restaurantArray.length];
        for(int i = 0; i < restaurantArray.length; i++) {
            names[i] = restaurantArray[i].getName();
        }

        String response = String.join(", ", names);

        Testing.outputText("------------", Color.BLACK);
        Testing.outputText("Testing Method: getByNeighborhood(\"" + neighborhood + "\")", Color.BLUE);
        Testing.outputText("Response: " + response, Color.BLACK);

        Testing.outputText("Test Finished", Color.DARK_GRAY);
    }

    public void testGetByRating(double rating) {
        testedMethods++;

        RestaurantSearch restaurantSearch = new RestaurantSearch(this.data, this.restaurants);
        Restaurant[] restaurantArray = restaurantSearch.getByRating(rating);
        String[] names = new String[restaurantArray.length];
        for(int i = 0; i < restaurantArray.length; i++) {
            names[i] = restaurantArray[i].getName() + " (" + restaurantArray[i].getAverageRating() + ")";
        }

        String response = String.join(", ", names);

        Testing.outputText("------------", Color.BLACK);
        Testing.outputText("Testing Method: getByRating(" + rating + ")", Color.BLUE);
        Testing.outputText("Response: " + response, Color.BLACK);

        Testing.outputText("Test Finished", Color.DARK_GRAY);
    }

    public void testGetByNeighborhoodAndRating(String neighborhood, double rating) {
        testedMethods++;

        RestaurantSearch restaurantSearch = new RestaurantSearch(this.data, this.restaurants);
        Restaurant[] restaurantArray = restaurantSearch.getByNeighborhoodAndRating(neighborhood, rating);
        String[] names = new String[restaurantArray.length];
        for(int i = 0; i < restaurantArray.length; i++) {
            names[i] = restaurantArray[i].getName() + " (" + restaurantArray[i].getAverageRating() + ")";
        }

        String response = String.join(", ", names);

        Testing.outputText("------------", Color.BLACK);
        Testing.outputText("Testing Method: getByNeighborhoodAndRating(\"" + neighborhood + "\", " + rating + ")", Color.BLUE);
        Testing.outputText("Response: " + response, Color.BLACK);

        Testing.outputText("Test Finished", Color.DARK_GRAY);
    }

    public void testGetByNeighborhoodAndSortByScore(String neighborhood) {
        testedMethods++;

        RestaurantSearch restaurantSearch = new RestaurantSearch(this.data, this.restaurants);
        Restaurant[] restaurantArray = restaurantSearch.getByNeighborhoodAndSortByScore(neighborhood);
        String[] names = new String[restaurantArray.length];
        for(int i = 0; i < restaurantArray.length; i++) {
            names[i] = restaurantArray[i].getName() + " (" + restaurantArray[i].getScore() + ")";
        }

        String response = String.join(", ", names);

        Testing.outputText("------------", Color.BLACK);
        Testing.outputText("Testing Method: getByNeighborhoodAndSortByScore(\"" + neighborhood + "\")", Color.BLUE);
        Testing.outputText("Response: " + response, Color.BLACK);

        Testing.outputText("Test Finished", Color.DARK_GRAY);
    }

    public void testGetByVicinity(String neighborhood) {
        testedMethods++;

        RestaurantSearch restaurantSearch = new RestaurantSearch(this.data, this.restaurants);
        Restaurant[] restaurantArray = restaurantSearch.getByVicinity(neighborhood);
        String[] names = new String[restaurantArray.length];
        for(int i = 0; i < restaurantArray.length; i++) {
            double[] hotelCoordinates = restaurantSearch.getHotelCoordinatesByNeighborhood(neighborhood);
            double[] restaurantCoordinates = restaurantArray[i].getCoordinates();
            names[i] = restaurantArray[i].getName() + " (" + distanceBetween(hotelCoordinates, restaurantCoordinates) + ")";
        }

        String response = String.join(", ", names);

        Testing.outputText("------------", Color.BLACK);
        Testing.outputText("Testing Method: getByVicinity(\"" + neighborhood + "\")", Color.BLUE);
        Testing.outputText("Response: " + response, Color.BLACK);

        Testing.outputText("Test Finished", Color.DARK_GRAY);
    }
}
