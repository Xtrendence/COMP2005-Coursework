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

    /* Tests the iteratorSize method.
    *  @return Nothing.
    */
    @Test
    public void testIteratorSize() throws JsonProcessingException {
        testedMethods++;

        String json = "{\"key\":{\"0\":\"value\", \"1\":\"value\"}}";
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(json);
        JsonNode keyNode = jsonNode.get("key");
        Iterator<JsonNode> iterator = keyNode.elements();
        int size = iteratorSize(iterator);

        String expected = "2";
        String actual = String.valueOf(size);

        Testing.outputText("------------", Color.BLACK);
        Testing.outputText("Testing Method: iteratorSize(iterator)", Color.BLUE);
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

    /* Tests the sumIntegerArray method.
    *  @return Nothing.
    */
    @Test
    public void testSumIntegerArray() {
        testedMethods++;

        int[] integers = new int[]{5, 10, 2, 8, 20};
        int sum = sumIntegerArray(integers);

        String expected = "45";
        String actual = String.valueOf(sum);

        Testing.outputText("------------", Color.BLACK);
        Testing.outputText("Testing Method: sumIntegerArray({5, 10, 2, 8, 20})", Color.BLUE);
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

    /* Tests the sortArrayAscendingDoubles method.
    *  @return Nothing.
    */
    @Test
    public void testSortArrayAscendingDoubles() {
        testedMethods++;

        double[] doubles = new double[]{5.5, 0.8, 2.8, 20.1, 50.25, 10.555};
        double[] sorted = sortArrayAscendingDoubles(doubles);

        String expected = "[0.8, 2.8, 5.5, 10.555, 20.1, 50.25]";
        String actual = Arrays.toString(sorted);

        Testing.outputText("------------", Color.BLACK);
        Testing.outputText("Testing Method: sortArrayAscendingDoubles({5.5, 0.8, 2.8, 20.1, 50.25, 10.555})", Color.BLUE);
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

    /* Tests the sortArrayDescending method.
    *  @return Nothing.
    */
    @Test
    public void testSortArrayDescending() {
        testedMethods++;

        int[] integers = new int[]{5, 8, 2, 20, 50, 10};
        int[] sorted = sortArrayDescending(integers);

        String expected = "[50, 20, 10, 8, 5, 2]";
        String actual = Arrays.toString(sorted);

        Testing.outputText("------------", Color.BLACK);
        Testing.outputText("Testing Method: sortArrayDescending({5, 8, 2, 20, 50, 10})", Color.BLUE);
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

    /* Tests the reverse method.
    *  @return Nothing.
    */
    @Test
    public void testReverse() {
        testedMethods++;

        int[] integers = new int[]{5, 8, 2, 20, 50, 10};
        int[] reversed = reverse(integers);

        String expected = "[10, 50, 20, 2, 8, 5]";
        String actual = Arrays.toString(reversed);

        Testing.outputText("------------", Color.BLACK);
        Testing.outputText("Testing Method: reverse({5, 8, 2, 20, 50, 10})", Color.BLUE);
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

    /* Tests the distanceBetween method.
    *  @return Nothing.
    */
    @Test
    public void testDistanceBetween() {
        testedMethods++;

        double[] from = new double[]{5, 10};
        double[] to = new double[]{20, 30};
        double distance = distanceBetween(from, to);

        String expected = "25.0";
        String actual = String.valueOf(distance);

        Testing.outputText("------------", Color.BLACK);
        Testing.outputText("Testing Method: distanceBetween({5, 10}, {20, 30})", Color.BLUE);
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

    /* Tests the getHotels method.
    *  @return Nothing.
    */
    @Test
    public void testGetHotels() {
        testedMethods++;

        RestaurantSearch restaurantSearch = new RestaurantSearch(this.data, this.restaurants);

        HashMap<String, double[]> hotels = restaurantSearch.getHotels();
        String[] names = hotels.keySet().toArray(new String[0]);

        String expected = "[brooklyn, manhattan, queens]";
        String actual = Arrays.toString(names);

        Testing.outputText("------------", Color.BLACK);
        Testing.outputText("Testing Method: getHotels()", Color.BLUE);
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

    /* Tests the getHotelCoordinatesByNeighborhood method.
    *  @return Nothing.
    */
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

    /* Tests the getByCuisine method.
    *  @return Nothing.
    */
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

    /* Tests the getByCuisineAndNeighborhood method.
    *  @return Nothing.
    */
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

    /* Tests the getByDayAndHour method.
    *  @return Nothing.
    */
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

    /* Tests the getByNeighborhood method.
    *  @return Nothing.
    */
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

    /* Tests the getByRating method.
    *  @return Nothing.
    */
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

    /* Tests the getByNeighborhoodAndRating method.
    *  @return Nothing.
    */
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

    /* Tests the getByNeighborhoodAndSortByScore method.
    *  @return Nothing.
    */
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

    /* Tests the getByVicinity method.
    *  @return Nothing.
    */
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

    /* Tests the getByNeighborhood method with a neighborhood that doesn't exist.
    *  @return Nothing.
    */
    @Test
    public void testGetByNeighborhoodNonExistent() {
        testedMethods++;

        RestaurantSearch restaurantSearch = new RestaurantSearch(this.data, this.restaurants);
        Restaurant[] restaurantArray = restaurantSearch.getByNeighborhood("Narnia");
        String[] names = new String[restaurantArray.length];
        for(int i = 0; i < restaurantArray.length; i++) {
            names[i] = restaurantArray[i].getName();
        }

        String expected = "";
        String actual = String.join(", ", names);

        Testing.outputText("------------", Color.BLACK);
        Testing.outputText("Testing Method: getByNeighborhood(\"Narnia\")", Color.BLUE);
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

    /* Tests the getByNeighborhood method without a neighborhood as an argument.
    *  @return Nothing.
    */
    @Test
    public void testGetByNeighborhoodEmpty() {
        testedMethods++;

        RestaurantSearch restaurantSearch = new RestaurantSearch(this.data, this.restaurants);
        Restaurant[] restaurantArray = restaurantSearch.getByNeighborhood("");
        String[] names = new String[restaurantArray.length];
        for(int i = 0; i < restaurantArray.length; i++) {
            names[i] = restaurantArray[i].getName();
        }

        String expected = "";
        String actual = String.join(", ", names);

        Testing.outputText("------------", Color.BLACK);
        Testing.outputText("Testing Method: getByNeighborhood(\"\")", Color.BLUE);
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

    /* Tests the getByNeighborhood method with a number as the neighborhood name.
    *  @return Nothing.
    */
    @Test
    public void testGetByNeighborhoodNumber() {
        testedMethods++;

        RestaurantSearch restaurantSearch = new RestaurantSearch(this.data, this.restaurants);
        Restaurant[] restaurantArray = restaurantSearch.getByNeighborhood("5");
        String[] names = new String[restaurantArray.length];
        for(int i = 0; i < restaurantArray.length; i++) {
            names[i] = restaurantArray[i].getName();
        }

        String expected = "";
        String actual = String.join(", ", names);

        Testing.outputText("------------", Color.BLACK);
        Testing.outputText("Testing Method: getByNeighborhood(\"5\")", Color.BLUE);
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

    /* Tests the getByCuisineAndNeighborhood method with a neighborhood name and restaurant cuisine that don't exist as part of the list.
    *  @return Nothing.
    */
    @Test
    public void testGetByCuisineAndNeighborhoodNonExistent() {
        testedMethods++;

        RestaurantSearch restaurantSearch = new RestaurantSearch(this.data, this.restaurants);
        Restaurant[] restaurantArray = restaurantSearch.getByCuisineAndNeighborhood("Narnia", "Persian");
        String[] names = new String[restaurantArray.length];
        for(int i = 0; i < restaurantArray.length; i++) {
            names[i] = restaurantArray[i].getName();
        }

        String expected = "";
        String actual = String.join(", ", names);

        Testing.outputText("------------", Color.BLACK);
        Testing.outputText("Testing Method: getByCuisineAndNeighborhood(\"Narnia\", \"Persian\")", Color.BLUE);
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

    /* Tests the getByNeighborhoodAndRating method with a negative rating.
    *  @return Nothing.
    */
    @Test
    public void testGetByNeighborhoodAndRatingNegative() {
        testedMethods++;

        RestaurantSearch restaurantSearch = new RestaurantSearch(this.data, this.restaurants);
        Restaurant[] restaurantArray = restaurantSearch.getByNeighborhoodAndRating("Manhattan", -5);
        String[] names = new String[restaurantArray.length];
        for(int i = 0; i < restaurantArray.length; i++) {
            names[i] = restaurantArray[i].getName() + " (" + restaurantArray[i].getAverageRating() + ")";
        }

        String expected = "Mission Chinese Food (3.7), Kang Ho Dong Baekjeong (3.7), Katz's Delicatessen (4.0), Superiority Burger (4.3), The Dutch (4.0)";
        String actual = String.join(", ", names);

        Testing.outputText("------------", Color.BLACK);
        Testing.outputText("Testing Method: getByNeighborhoodAndRating(\"Manhattan\", -5)", Color.BLUE);
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

    /* Tests the getByNeighborhoodAndRating method with a neighborhood that doesn't exist and a negative rating.
    *  @return Nothing.
    */
    @Test
    public void testGetByNeighborhoodAndRatingNonExistentNegative() {
        testedMethods++;

        RestaurantSearch restaurantSearch = new RestaurantSearch(this.data, this.restaurants);
        Restaurant[] restaurantArray = restaurantSearch.getByNeighborhoodAndRating("Narnia", -5);
        String[] names = new String[restaurantArray.length];
        for(int i = 0; i < restaurantArray.length; i++) {
            names[i] = restaurantArray[i].getName() + " (" + restaurantArray[i].getAverageRating() + ")";
        }

        String expected = "";
        String actual = String.join(", ", names);

        Testing.outputText("------------", Color.BLACK);
        Testing.outputText("Testing Method: getByNeighborhoodAndRating(\"Narnia\", -5)", Color.BLUE);
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

    /* Tests the getByDayAndHour method with an invalid day.
    *  @return Nothing.
    */
    @Test(expected = NullPointerException.class)
    public void testGetByDayAndHourInvalidDay() {
        testedMethods++;

        String expected = null;
        String actual;

        Testing.outputText("------------", Color.BLACK);
        Testing.outputText("Testing Method: getByDayAndHour(\"Someday\", \"5:30 pm\")", Color.BLUE);

        try {
            RestaurantSearch restaurantSearch = new RestaurantSearch(this.data, this.restaurants);
            Restaurant[] restaurantArray = restaurantSearch.getByDayAndHour("Someday", "5:30 pm");
            String[] names = new String[restaurantArray.length];
            for(int i = 0; i < restaurantArray.length; i++) {
                HashMap<String, LocalTime[]> hours = restaurantArray[i].getHours();
                LocalTime[] operatingHours = hours.get("Someday");
                names[i] = restaurantArray[i].getName() + " (" + Arrays.toString(operatingHours) + ")";
            }

            actual = String.join(", ", names);
        } catch(NullPointerException e) {
            actual = null;
        }

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

    /* Tests the getByDayAndHour method with an invalid time.
    *  @return Nothing.
    */
    @Test(expected = DateTimeParseException.class)
    public void testGetByDayAndHourInvalidHour() {
        testedMethods++;

        String expected = "Text '005:30 pm' could not be parsed at index 2";
        String actual;

        Testing.outputText("------------", Color.BLACK);
        Testing.outputText("Testing Method: getByDayAndHour(\"Saturday\", \"05:30 pm\")", Color.BLUE);

        try {
            RestaurantSearch restaurantSearch = new RestaurantSearch(this.data, this.restaurants);
            Restaurant[] restaurantArray = restaurantSearch.getByDayAndHour("Saturday", "05:30 pm");
            String[] names = new String[restaurantArray.length];
            for(int i = 0; i < restaurantArray.length; i++) {
                HashMap<String, LocalTime[]> hours = restaurantArray[i].getHours();
                LocalTime[] operatingHours = hours.get("Saturday");
                names[i] = restaurantArray[i].getName() + " (" + Arrays.toString(operatingHours) + ")";
            }

            actual = String.join(", ", names);
        } catch(DateTimeParseException e) {
            actual = e.getMessage();
        }

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
