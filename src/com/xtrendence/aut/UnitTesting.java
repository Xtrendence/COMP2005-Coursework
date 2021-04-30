package com.xtrendence.aut;

import org.junit.Test;

import java.awt.*;
import java.lang.reflect.Method;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashMap;

import static com.xtrendence.aut.Utils.distanceBetween;
import static org.junit.Assert.assertEquals;

public class UnitTesting {
    public int overallMethods;
    public int testedMethods;
    public int passedMethods;
    public int failedMethods;

    String data;
    Restaurant[] restaurants;

    public UnitTesting() throws Exception {
        Class thisClass = UnitTesting.class;
        Method[] methods = thisClass.getDeclaredMethods();

        this.overallMethods = methods.length;
        this.testedMethods = 0;
        this.passedMethods = 0;
        this.failedMethods = 0;

        loadRestuarants();
    }

    @Test
    public void loadRestuarants() throws Exception {
        testedMethods++;

        MockHttpLib mockHttpLib = new MockHttpLib();
        Testing.outputText("Fetching JSON from Mock Object...", Color.BLUE);
        Response response = mockHttpLib.call("");
        int code = response.getCode();
        String json = response.getData();

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
                        passedMethods++;
                        assertEquals(expected, actual);
                        Testing.outputText("Test Passed", new Color(0, 150, 0));
                    } catch(AssertionError e) {
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

    @Test
    public void testGetHotelCoordinatesByNeighborhood() {
        testedMethods++;

        RestaurantSearch restaurantSearch = new RestaurantSearch(this.data, this.restaurants);

        double[] coordinates = restaurantSearch.getHotelCoordinatesByNeighborhood("Queens");

        String expected = "[40.75399, -73.94924]";
        String actual = Arrays.toString(coordinates);

        Testing.outputText("------------", Color.BLACK);
        Testing.outputText("Testing Method: getHotelCoordinatesByNeighborhood(\"Queens\")", Color.BLUE);
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

    @Test
    public void testGetByCuisine() {
        testedMethods++;

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

    @Test
    public void testGetByCuisineAndNeighborhood() {
        testedMethods++;

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

    @Test
    public void testGetByDayAndHour() {
        testedMethods++;

        RestaurantSearch restaurantSearch = new RestaurantSearch(this.data, this.restaurants);
        Restaurant[] restaurantArray = restaurantSearch.getByDayAndHour("Saturday", "5:30 pm");
        String[] names = new String[restaurantArray.length];
        for(int i = 0; i < restaurantArray.length; i++) {
            HashMap<String, LocalTime[]> hours = restaurantArray[i].getHours();
            LocalTime[] operatingHours = hours.get("Saturday");
            names[i] = restaurantArray[i].getName() + " (" + Arrays.toString(operatingHours) + ")";
        }

        String expected = "Emily ([17:00, 23:30]), Katz's Delicatessen ([00:00, 23:59]), Hometown BBQ ([12:00, 23:00]), Superiority Burger ([11:30, 22:00]), Mu Ramen ([17:00, 23:00])";
        String actual = String.join(", ", names);

        Testing.outputText("------------", Color.BLACK);
        Testing.outputText("Testing Method: getByDayAndHour(\"Saturday\", \"5:30 pm\")", Color.BLUE);
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

    @Test
    public void testGetByNeighborhood() {
        testedMethods++;

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

    @Test
    public void testGetByRating() {
        testedMethods++;

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

    @Test
    public void testGetByNeighborhoodAndRating() {
        testedMethods++;

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

    @Test
    public void testGetByNeighborhoodAndSortByScore() {
        testedMethods++;

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

    @Test
    public void testGetByVicinity() {
        testedMethods++;

        RestaurantSearch restaurantSearch = new RestaurantSearch(this.data, this.restaurants);
        Restaurant[] restaurantArray = restaurantSearch.getByVicinity("Brooklyn");
        String[] names = new String[restaurantArray.length];
        for(int i = 0; i < restaurantArray.length; i++) {
            double[] hotelCoordinates = restaurantSearch.getHotelCoordinatesByNeighborhood("Brooklyn");
            double[] restaurantCoordinates = restaurantArray[i].getCoordinates();
            names[i] = restaurantArray[i].getName() + " (" + distanceBetween(hotelCoordinates, restaurantCoordinates) + ")";
        }

        String expected = "Emily (0.022509017615175434), Hometown BBQ (0.031625908192483905), Roberta's Pizza (0.05669735854518338)";
        String actual = String.join(", ", names);

        Testing.outputText("------------", Color.BLACK);
        Testing.outputText("Testing Method: getByVicinity(\"Brooklyn\")", Color.BLUE);
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
