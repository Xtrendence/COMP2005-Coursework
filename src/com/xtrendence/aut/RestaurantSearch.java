package com.xtrendence.aut;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.*;

import static com.xtrendence.aut.Utils.*;

public class RestaurantSearch {
    public String api = "http://intelligent-social-robots-ws.com/restaurant-data.json";
    private HashMap<String, double[]> hotels;
    private String data;
    private Restaurant[] restaurants;

    public RestaurantSearch() {
        setHotels();
    }

    public RestaurantSearch(String data, Restaurant[] restaurants) {
        setHotels();
        this.data = data;
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
        Response response = httpLib.call(api);
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

    public Restaurant[] getByCuisineAndNeighborhood(String neighborhood, String cuisine) {
        List<Restaurant> list = new ArrayList<>();
        Restaurant[] neighborhoodRestaurants = getByNeighborhood(neighborhood);
        for(Restaurant restaurant : neighborhoodRestaurants) {
            if(restaurant.getCuisine().equalsIgnoreCase(cuisine)) {
                list.add(restaurant);
            }
        }
        return listToArray(list);
    }

    public Restaurant[] getByDayAndHour(String day, String hour) {
        int timeHour = Integer.parseInt(hour.split(":")[0]);

        if(timeHour < 10) {
            hour = "0" + hour;
        }

        List<Restaurant> list = new ArrayList<>();
        for(Restaurant restaurant : this.restaurants) {
            HashMap<String, LocalTime[]> hours = restaurant.getHours();
            Map<Long, String> timeOfDay = Map.of(0L, " am", 1L, " pm");
            DateTimeFormatter timeFormatter = new DateTimeFormatterBuilder().appendPattern("hh:mm").appendText(ChronoField.AMPM_OF_DAY, timeOfDay).toFormatter();
            LocalTime[] openingHours = hours.get(day);

            LocalTime time = LocalTime.parse(hour.toLowerCase(), timeFormatter);
            if(openingHours.length == 2) {
                LocalTime from = openingHours[0];
                LocalTime to = openingHours[1];

                if(time.isAfter(from) && time.isBefore(to)) {
                    list.add(restaurant);
                }
            } else {
                LocalTime fromFirst = openingHours[0];
                LocalTime toFirst = openingHours[1];

                LocalTime fromSecond = openingHours[2];
                LocalTime toSecond = openingHours[3];

                if(time.isAfter(fromFirst) && time.isBefore(toFirst) || time.isAfter(fromSecond) && time.isBefore(toSecond)) {
                    list.add(restaurant);
                }
            }
        }
        return listToArray(list);
    }

    public Restaurant[] getByRating(double rating) {
        List<Restaurant> list = new ArrayList<>();
        for(Restaurant restaurant : this.restaurants) {
            if(restaurant.getAverageRating() > rating) {
                list.add(restaurant);
            }
        }
        return listToArray(list);
    }

    public Restaurant[] getByNeighborhoodAndRating(String neighborhood, double rating) {
        List<Restaurant> list = new ArrayList<>();
        Restaurant[] neighborhoodRestaurants = getByNeighborhood(neighborhood);
        for(Restaurant restaurant : neighborhoodRestaurants) {
            if(restaurant.getAverageRating() > rating) {
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

    public Restaurant[] getByVicinity(String neighborhood) {
        List<Restaurant> list = new ArrayList<>();
        Restaurant[] neighborhoodRestaurants = getByNeighborhood(neighborhood);
        double[] hotelCoordinates = this.hotels.get(neighborhood.toLowerCase());
        double[] distances = new double[neighborhoodRestaurants.length];
        for(int i = 0; i < neighborhoodRestaurants.length; i++) {
            double[] restaurantCoordinates = neighborhoodRestaurants[i].getCoordinates();
            distances[i] = distanceBetween(hotelCoordinates, restaurantCoordinates);
        }
        distances = sortArrayAscendingDoubles(distances);
        for(int i = 0; i < distances.length; i++) {
            for(Restaurant restaurant : neighborhoodRestaurants) {
                double[] restaurantCoordinates = restaurant.getCoordinates();
                double distance = distanceBetween(hotelCoordinates, restaurantCoordinates);
                if(distance <= distances[i] && !list.contains(restaurant)) {
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
