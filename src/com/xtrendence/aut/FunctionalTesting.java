package com.xtrendence.aut;

import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertEquals;

public class FunctionalTesting {
    boolean mockTest;
    String data;
    Restaurant[] restaurants;

    public FunctionalTesting(boolean mockTest) {
        this.mockTest = mockTest;
    }

    @Test
    public void loadRestuarants() throws Exception {
        int code;
        String json;

        if(mockTest) {
            MockHttpLib mockHttpLib = new MockHttpLib();
            Testing.outputText("Fetching JSON from Mock Object...", Color.BLUE);
            Response response = mockHttpLib.call("");
            code = response.getCode();
            json = response.getData();
        } else {
            RestaurantSearch restaurantSearch = new RestaurantSearch();
            Testing.outputText("Fetching JSON from " + restaurantSearch.api + "...", Color.BLUE);
            code = restaurantSearch.loadRestaurants();
            json = restaurantSearch.getData();
        }

        if(code == 200) {
            if (json != null && !json.equals("")) {
                Testing.outputText("Fetched JSON", Color.BLUE);
                Testing.outputText(json.substring(0, 127) + "...\n(Only showing first 128 characters)", Color.BLACK);
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
                    if(actual.equalsIgnoreCase(expected)) {
                        Testing.outputText("Test Passed", new Color(0, 150, 0));
                    } else {
                        Testing.outputText("Test Failed", Color.RED);
                    }

                    assertEquals(expected, actual);
                } else {
                    Testing.outputText("Couldn't parse JSON.", Color.RED);
                }
            } else {
                Testing.outputText("Couldn't fetch JSON.", Color.RED);
            }
        }
    }

    @Test
    public void testGetByCuisine() {
        RestaurantSearch restaurantSearch = new RestaurantSearch(this.data, this.restaurants);
        Restaurant[] restaurantArray = restaurantSearch.getByCuisine("Asian");
        String[] names = new String[restaurantArray.length];
        for(int i = 0; i < restaurantArray.length; i++) {
            names[i] = restaurantArray[i].getName();
        }

        String expected = "Mission Chinese Food, Kang Ho Dong Baekjeong, Mu Ramen";
        String actual = String.join(", ", names);

        Testing.outputText("------------", Color.BLACK);
        Testing.outputText("Testing Method: getByCuisine(\"Asian\")", Color.BLUE);
        Testing.outputText("Expected: " + expected, Color.BLACK);
        Testing.outputText("Actual: " + actual, Color.BLACK);
        if(actual.equalsIgnoreCase(expected)) {
            Testing.outputText("Test Passed", new Color(0, 150, 0));
        } else {
            Testing.outputText("Test Failed", Color.RED);
        }

        assertEquals(expected, actual);
    }

    @Test
    public void testGetByCuisineAndNeighborhood() {
        RestaurantSearch restaurantSearch = new RestaurantSearch(this.data, this.restaurants);
        Restaurant[] restaurantArray = restaurantSearch.getByCuisineAndNeighborhood("Manhattan", "Asian");
        String[] names = new String[restaurantArray.length];
        for(int i = 0; i < restaurantArray.length; i++) {
            names[i] = restaurantArray[i].getName();
        }

        String expected = "Mission Chinese Food, Kang Ho Dong Baekjeong";
        String actual = String.join(", ", names);

        Testing.outputText("------------", Color.BLACK);
        Testing.outputText("Testing Method: getByCuisineAndNeighborhood(\"Manhattan\", \"Asian\")", Color.BLUE);
        Testing.outputText("Expected: " + expected, Color.BLACK);
        Testing.outputText("Actual: " + actual, Color.BLACK);
        if(actual.equalsIgnoreCase(expected)) {
            Testing.outputText("Test Passed", new Color(0, 150, 0));
        } else {
            Testing.outputText("Test Failed", Color.RED);
        }

        assertEquals(expected, actual);
    }

    @Test
    public void testGetByNeighborhood() {
        RestaurantSearch restaurantSearch = new RestaurantSearch(this.data, this.restaurants);
        Restaurant[] restaurantArray = restaurantSearch.getByNeighborhood("Manhattan");
        String[] names = new String[restaurantArray.length];
        for(int i = 0; i < restaurantArray.length; i++) {
            names[i] = restaurantArray[i].getName();
        }

        String expected = "Mission Chinese Food, Kang Ho Dong Baekjeong, Katz's Delicatessen, Superiority Burger, The Dutch";
        String actual = String.join(", ", names);

        Testing.outputText("------------", Color.BLACK);
        Testing.outputText("Testing Method: getByNeighborhood(\"Manhattan\")", Color.BLUE);
        Testing.outputText("Expected: " + expected, Color.BLACK);
        Testing.outputText("Actual: " + actual, Color.BLACK);
        if(actual.equalsIgnoreCase(expected)) {
            Testing.outputText("Test Passed", new Color(0, 150, 0));
        } else {
            Testing.outputText("Test Failed", Color.RED);
        }

        assertEquals(expected, actual);
    }

    @Test
    public void testGetByRating() {
        RestaurantSearch restaurantSearch = new RestaurantSearch(this.data, this.restaurants);
        Restaurant[] restaurantArray = restaurantSearch.getByRating(4.5);
        String[] names = new String[restaurantArray.length];
        for(int i = 0; i < restaurantArray.length; i++) {
            names[i] = restaurantArray[i].getName() + " (" + restaurantArray[i].getAverageRating() + ")";
        }

        String expected = "Casa Enrique (4.7)";
        String actual = String.join(", ", names);

        Testing.outputText("------------", Color.BLACK);
        Testing.outputText("Testing Method: getByRating(4.5)", Color.BLUE);
        Testing.outputText("Expected: " + expected, Color.BLACK);
        Testing.outputText("Actual: " + actual, Color.BLACK);
        if(actual.equalsIgnoreCase(expected)) {
            Testing.outputText("Test Passed", new Color(0, 150, 0));
        } else {
            Testing.outputText("Test Failed", Color.RED);
        }

        assertEquals(expected, actual);
    }

    @Test
    public void testGetByNeighborhoodAndRating() {
        RestaurantSearch restaurantSearch = new RestaurantSearch(this.data, this.restaurants);
        Restaurant[] restaurantArray = restaurantSearch.getByNeighborhoodAndRating("Manhattan", 3.85);
        String[] names = new String[restaurantArray.length];
        for(int i = 0; i < restaurantArray.length; i++) {
            names[i] = restaurantArray[i].getName() + " (" + restaurantArray[i].getAverageRating() + ")";
        }

        String expected = "Katz's Delicatessen (4.0), Superiority Burger (4.3), The Dutch (4.0)";
        String actual = String.join(", ", names);

        Testing.outputText("------------", Color.BLACK);
        Testing.outputText("Testing Method: getByNeighborhoodAndRating(\"Manhattan\", 3.85)", Color.BLUE);
        Testing.outputText("Expected: " + expected, Color.BLACK);
        Testing.outputText("Actual: " + actual, Color.BLACK);
        if(actual.equalsIgnoreCase(expected)) {
            Testing.outputText("Test Passed", new Color(0, 150, 0));
        } else {
            Testing.outputText("Test Failed", Color.RED);
        }

        assertEquals(expected, actual);
    }

    @Test
    public void testGetByNeighborhoodAndSortByScore() {
        RestaurantSearch restaurantSearch = new RestaurantSearch(this.data, this.restaurants);
        Restaurant[] restaurantArray = restaurantSearch.getByNeighborhoodAndSortByScore("Manhattan");
        String[] names = new String[restaurantArray.length];
        for(int i = 0; i < restaurantArray.length; i++) {
            names[i] = restaurantArray[i].getName() + " (" + restaurantArray[i].getScore() + ")";
        }

        String expected = "Kang Ho Dong Baekjeong (28), Katz's Delicatessen (18), Mission Chinese Food (13), The Dutch (13), Superiority Burger (8)";
        String actual = String.join(", ", names);

        Testing.outputText("------------", Color.BLACK);
        Testing.outputText("Testing Method: getByNeighborhoodAndSortByScore(\"Manhattan\")", Color.BLUE);
        Testing.outputText("Expected: " + expected, Color.BLACK);
        Testing.outputText("Actual: " + actual, Color.BLACK);
        if(actual.equalsIgnoreCase(expected)) {
            Testing.outputText("Test Passed", new Color(0, 150, 0));
        } else {
            Testing.outputText("Test Failed", Color.RED);
        }

        assertEquals(expected, actual);
    }

    @Test
    public void testGetByVicinity() {
        RestaurantSearch restaurantSearch = new RestaurantSearch(this.data, this.restaurants);
        Restaurant[] restaurantArray = restaurantSearch.getByVicinity("Brooklyn");
        String[] names = new String[restaurantArray.length];
        for(int i = 0; i < restaurantArray.length; i++) {
            names[i] = restaurantArray[i].getName();
        }

        String expected = "Emily, Roberta's Pizza, Hometown BBQ";
        String actual = String.join(", ", names);

        Testing.outputText("------------", Color.BLACK);
        Testing.outputText("Testing Method: getByVicinity(\"Brooklyn\")", Color.BLUE);
        Testing.outputText("Expected: " + expected, Color.BLACK);
        Testing.outputText("Actual: " + actual, Color.BLACK);
        if(actual.equalsIgnoreCase(expected)) {
            Testing.outputText("Test Passed", new Color(0, 150, 0));
        } else {
            Testing.outputText("Test Failed", Color.RED);
        }

        assertEquals(expected, actual);
    }
}
