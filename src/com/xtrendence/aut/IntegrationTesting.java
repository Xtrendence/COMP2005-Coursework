package com.xtrendence.aut;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.awt.*;
import java.lang.reflect.Method;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

import static com.xtrendence.aut.Utils.*;
import static org.junit.Assert.assertEquals;

public class IntegrationTesting {
    public int overallMethods;
    public int testedMethods;
    public int passedMethods;
    public int failedMethods;

    String data;
    Restaurant[] restaurants;

    public IntegrationTesting() throws Exception {
        Class thisClass = IntegrationTesting.class;
        Method[] methods = thisClass.getDeclaredMethods();

        this.overallMethods = methods.length;
        this.testedMethods = 0;
        this.passedMethods = 0;
        this.failedMethods = 0;

        loadRestuarants();
    }

    /* Fetches the restaurant data from the mock object to ensure the API doesn't act as a dependency when unit testing.
    *  @return Nothing.
    */
    @Test
    public void loadRestuarants() throws Exception {
        testedMethods++;

        MockHttpLib mockHttpLib = new MockHttpLib();
        Testing.outputText("Fetching JSON from Mock Object...", Color.BLUE);
        Response response = mockHttpLib.call("");
        int code = response.getCode();
        String json = response.getData();

        if (code == 200) {
            if (json != null && !json.equals("")) {
                Testing.outputText("Fetched JSON", Color.BLUE);
                Testing.outputText(json.substring(0, 255) + "...\n(Only showing the first 256 characters)", Color.BLACK);
                Testing.outputText("Parsing JSON...", Color.BLUE);
                Testing.outputText("Checking Restaurant Names...", Color.BLUE);
                this.restaurants = new RestaurantAdapter(json).adapt();
                this.data = json;
                if (this.restaurants.length > 0) {
                    String[] names = new String[restaurants.length];
                    for (int i = 0; i < this.restaurants.length; i++) {
                        names[i] = restaurants[i].getName() + " (Average Rating: " + restaurants[i].getAverageRating() + ")";
                    }

                    String expected = "Mission Chinese Food (Average Rating: 3.7), Emily (Average Rating: 4.3), Kang Ho Dong Baekjeong (Average Rating: 3.7), Katz's Delicatessen (Average Rating: 4.0), Roberta's Pizza (Average Rating: 4.0), Hometown BBQ (Average Rating: 4.0), Superiority Burger (Average Rating: 4.3), The Dutch (Average Rating: 4.0), Mu Ramen (Average Rating: 4.0), Casa Enrique (Average Rating: 4.7)";
                    String actual = String.join(", ", names);

                    Testing.outputText("Expected: " + expected, Color.BLACK);
                    Testing.outputText("Actual: " + actual, Color.BLACK);

                    try {
                        passedMethods++;
                        assertEquals(expected, actual);
                        Testing.outputText("Test Passed", new Color(0, 150, 0));
                    } catch (AssertionError e) {
                        failedMethods++;
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

    /* Fetches the list of hotels, and calculates their distance from a restaurant in the same neighborhood.
    *  @return Nothing.
    */
    @Test
    public void testHotelDistanceFromRestaurant() {
        testedMethods++;

        RestaurantSearch restaurantSearch = new RestaurantSearch(this.data, this.restaurants);
        HashMap<String, double[]> hotels = restaurantSearch.getHotels();
        double[] coordinates = hotels.get("queens");
        Restaurant restaurant = restaurantSearch.getByNeighborhood("Queens")[0];
        double distance = distanceBetween(coordinates, restaurant.getCoordinates());

        String expected = "0.010290334931382266";
        String actual = String.valueOf(distance);

        Testing.outputText("------------", Color.BLACK);
        Testing.outputText("Test: testHotelDistanceFromRestaurant()", Color.BLUE);
        Testing.outputText("Expected: " + expected, Color.BLACK);
        Testing.outputText("Actual: " + actual, Color.BLACK);

        try {
            assertEquals(expected, actual);
            passedMethods++;
            Testing.outputText("Test Passed", new Color(0, 150, 0));
        } catch(AssertionError e) {
            failedMethods++;
            Testing.outputText("Test Failed", Color.RED);
            e.printStackTrace();
        }
    }

    /* Fetches a list of restaurants in a neighborhood, and filters them by ones that are open on a given day at a given time.
    *  @return Nothing.
    */
    @Test
    public void testGetByNeighborhoodAndDayAndHour() {
        testedMethods++;

        RestaurantSearch restaurantSearch = new RestaurantSearch(this.data, this.restaurants);
        Restaurant[] restaurantArray = restaurantSearch.getByNeighborhood("Manhattan");
        restaurantSearch.setRestaurants(restaurantArray);
        restaurantArray = restaurantSearch.getByDayAndHour("Sunday", "2:30 PM");
        String[] names = new String[restaurantArray.length];
        for(int i = 0; i < restaurantArray.length; i++) {
            names[i] = restaurantArray[i].getName();
        }

        String expected = "[Mission Chinese Food, Katz's Delicatessen, Superiority Burger, The Dutch]";
        String actual = Arrays.toString(names);

        Testing.outputText("------------", Color.BLACK);
        Testing.outputText("Test: testGetByNeighborhoodAndDayAndHour()", Color.BLUE);
        Testing.outputText("Expected: " + expected, Color.BLACK);
        Testing.outputText("Actual: " + actual, Color.BLACK);

        try {
            assertEquals(expected, actual);
            passedMethods++;
            Testing.outputText("Test Passed", new Color(0, 150, 0));
        } catch(AssertionError e) {
            failedMethods++;
            Testing.outputText("Test Failed", Color.RED);
            e.printStackTrace();
        }
    }

    /* Fetches a list of restaurants in a neighborhood, and filters them by ones that are open on a given day at a given time and have a rating above a certain number.
    *  @return Nothing.
    */
    @Test
    public void testGetByNeighborhoodAndDayAndHourAndRating() {
        testedMethods++;

        RestaurantSearch restaurantSearch = new RestaurantSearch(this.data, this.restaurants);
        Restaurant[] restaurantArray = restaurantSearch.getByNeighborhood("Manhattan");
        restaurantSearch.setRestaurants(restaurantArray);
        restaurantArray = restaurantSearch.getByDayAndHour("Sunday", "2:30 PM");
        restaurantSearch.setRestaurants(restaurantArray);
        restaurantArray = restaurantSearch.getByRating(4.2);
        String[] names = new String[restaurantArray.length];
        for(int i = 0; i < restaurantArray.length; i++) {
            names[i] = restaurantArray[i].getName();
        }

        String expected = "[Superiority Burger]";
        String actual = Arrays.toString(names);

        Testing.outputText("------------", Color.BLACK);
        Testing.outputText("Test: testGetByNeighborhoodAndDayAndHourAndRating()", Color.BLUE);
        Testing.outputText("Expected: " + expected, Color.BLACK);
        Testing.outputText("Actual: " + actual, Color.BLACK);

        try {
            assertEquals(expected, actual);
            passedMethods++;
            Testing.outputText("Test Passed", new Color(0, 150, 0));
        } catch(AssertionError e) {
            failedMethods++;
            Testing.outputText("Test Failed", Color.RED);
            e.printStackTrace();
        }
    }

    /* Fetches a list of restaurants in a neighborhood based on their vicinity to the hotel, and filters them by cuisine.
    *  @return Nothing.
    */
    @Test
    public void testGetByVicinityAndCuisine() {
        testedMethods++;

        RestaurantSearch restaurantSearch = new RestaurantSearch(this.data, this.restaurants);
        Restaurant[] restaurantArray = restaurantSearch.getByVicinity("Brooklyn");
        restaurantSearch.setRestaurants(restaurantArray);
        restaurantArray = restaurantSearch.getByCuisine("Pizza");
        String[] names = new String[restaurantArray.length];
        for(int i = 0; i < restaurantArray.length; i++) {
            double[] hotelCoordinates = restaurantSearch.getHotelCoordinatesByNeighborhood("Brooklyn");
            double[] restaurantCoordinates = restaurantArray[i].getCoordinates();
            names[i] = restaurantArray[i].getName() + " (" + distanceBetween(hotelCoordinates, restaurantCoordinates) + ")";
        }

        String expected = "[Emily (0.022509017615175434), Roberta's Pizza (0.05669735854518338)]";
        String actual = Arrays.toString(names);

        Testing.outputText("------------", Color.BLACK);
        Testing.outputText("Test: testGetByVicinityAndCuisine()", Color.BLUE);
        Testing.outputText("Expected: " + expected, Color.BLACK);
        Testing.outputText("Actual: " + actual, Color.BLACK);

        try {
            assertEquals(expected, actual);
            passedMethods++;
            Testing.outputText("Test Passed", new Color(0, 150, 0));
        } catch(AssertionError e) {
            failedMethods++;
            Testing.outputText("Test Failed", Color.RED);
            e.printStackTrace();
        }
    }

    /* Fetches a list of restaurants in a neighborhood with a given cuisine and rating.
    *  @return Nothing.
    */
    @Test
    public void testGetByNeighborhoodAndCuisineAndRating() {
        testedMethods++;

        RestaurantSearch restaurantSearch = new RestaurantSearch(this.data, this.restaurants);
        Restaurant[] restaurantArray = restaurantSearch.getByCuisineAndNeighborhood("Manhattan", "Asian");
        restaurantSearch.setRestaurants(restaurantArray);
        restaurantArray = restaurantSearch.getByRating(3.5);
        String[] names = new String[restaurantArray.length];
        for(int i = 0; i < restaurantArray.length; i++) {
            names[i] = restaurantArray[i].getName();
        }

        String expected = "[Mission Chinese Food, Kang Ho Dong Baekjeong]";
        String actual = Arrays.toString(names);

        Testing.outputText("------------", Color.BLACK);
        Testing.outputText("Test: testGetByNeighborhoodAndCuisineAndRating()", Color.BLUE);
        Testing.outputText("Expected: " + expected, Color.BLACK);
        Testing.outputText("Actual: " + actual, Color.BLACK);

        try {
            assertEquals(expected, actual);
            passedMethods++;
            Testing.outputText("Test Passed", new Color(0, 150, 0));
        } catch(AssertionError e) {
            failedMethods++;
            Testing.outputText("Test Failed", Color.RED);
            e.printStackTrace();
        }
    }

    /* Fetches a list of restaurants in a neighborhood by cuisine, and sorts them according to their score.
    *  @return Nothing.
    */
    @Test
    public void testGetByCuisineAndSortByScore() {
        testedMethods++;

        RestaurantSearch restaurantSearch = new RestaurantSearch(this.data, this.restaurants);
        Restaurant[] restaurantArray = restaurantSearch.getByCuisine("Asian");
        restaurantSearch.setRestaurants(restaurantArray);
        restaurantArray = restaurantSearch.getByNeighborhoodAndSortByScore("Manhattan");
        String[] names = new String[restaurantArray.length];
        for(int i = 0; i < restaurantArray.length; i++) {
            names[i] = restaurantArray[i].getName() + " (" + restaurantArray[i].getScore() + ")";
        }

        String expected = "[Kang Ho Dong Baekjeong (28), Mission Chinese Food (13)]";
        String actual = Arrays.toString(names);

        Testing.outputText("------------", Color.BLACK);
        Testing.outputText("Test: testGetByCuisineAndSortByScore()", Color.BLUE);
        Testing.outputText("Expected: " + expected, Color.BLACK);
        Testing.outputText("Actual: " + actual, Color.BLACK);

        try {
            assertEquals(expected, actual);
            passedMethods++;
            Testing.outputText("Test Passed", new Color(0, 150, 0));
        } catch(AssertionError e) {
            failedMethods++;
            Testing.outputText("Test Failed", Color.RED);
            e.printStackTrace();
        }
    }

    /* Fetches a list of restaurants in a neighborhood by rating, and sorts them by their score.
    *  @return Nothing.
    */
    @Test
    public void testGetByRatingAndSortByScore() {
        testedMethods++;

        RestaurantSearch restaurantSearch = new RestaurantSearch(this.data, this.restaurants);
        Restaurant[] restaurantArray = restaurantSearch.getByRating(3.6);
        restaurantSearch.setRestaurants(restaurantArray);
        restaurantArray = restaurantSearch.getByNeighborhoodAndSortByScore("Manhattan");
        String[] names = new String[restaurantArray.length];
        for(int i = 0; i < restaurantArray.length; i++) {
            names[i] = restaurantArray[i].getName() + " (Score: " + restaurantArray[i].getScore() + ", Rating: " + restaurantArray[i].getAverageRating() + ")";
        }

        String expected = "[Kang Ho Dong Baekjeong (Score: 28, Rating: 3.7), Katz's Delicatessen (Score: 18, Rating: 4.0), Mission Chinese Food (Score: 13, Rating: 3.7), The Dutch (Score: 13, Rating: 4.0), Superiority Burger (Score: 8, Rating: 4.3)]";
        String actual = Arrays.toString(names);

        Testing.outputText("------------", Color.BLACK);
        Testing.outputText("Test: testGetByRatingAndSortByScore()", Color.BLUE);
        Testing.outputText("Expected: " + expected, Color.BLACK);
        Testing.outputText("Actual: " + actual, Color.BLACK);

        try {
            assertEquals(expected, actual);
            passedMethods++;
            Testing.outputText("Test Passed", new Color(0, 150, 0));
        } catch(AssertionError e) {
            failedMethods++;
            Testing.outputText("Test Failed", Color.RED);
            e.printStackTrace();
        }
    }
}