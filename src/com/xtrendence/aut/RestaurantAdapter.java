package com.xtrendence.aut;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static com.xtrendence.aut.Utils.iteratorSize;

public class RestaurantAdapter {
    private String json;
    public RestaurantAdapter(String json) {
        this.json = json;
    }

    public Restaurant[] adapt() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(json);
        JsonNode restaurantsNode = jsonNode.get("restaurants");
        Iterator<JsonNode> restaurantIterator = restaurantsNode.elements();
        while(restaurantIterator.hasNext()) {
            JsonNode restaurant = restaurantIterator.next();

            int id = Integer.parseInt(String.valueOf(restaurant.get("id")));

            String name = String.valueOf(restaurant.get("name"));

            int score = Integer.parseInt(String.valueOf(restaurant.get("DOHMH_inspection_score")).replace("\"", ""));

            String neighborhood = String.valueOf(restaurant.get("neighborhood"));

            String photograph = String.valueOf(restaurant.get("photograph"));

            String address = String.valueOf(restaurant.get("address"));

            JsonNode location = restaurant.get("latlng");
            double latitude = Double.parseDouble(String.valueOf(location.get("lat")));
            double longitude = Double.parseDouble(String.valueOf(location.get("lng")));
            double[] coordinates = new double[]{latitude, longitude};

            String cuisine = String.valueOf(restaurant.get("cuisine_type"));

            JsonNode operating = restaurant.get("operating_hours");
            String monday = String.valueOf(operating.get("Monday")).replace("\"", "");
            String tuesday = String.valueOf(operating.get("Tuesday")).replace("\"", "");
            String wednesday = String.valueOf(operating.get("Wednesday")).replace("\"", "");
            String thursday = String.valueOf(operating.get("Thursday")).replace("\"", "");
            String friday = String.valueOf(operating.get("Friday")).replace("\"", "");
            String saturday = String.valueOf(operating.get("Saturday")).replace("\"", "");
            String sunday = String.valueOf(operating.get("Sunday")).replace("\"", "");

            HashMap<String, LocalTime[]> hours = new HashMap<>();
            hours.put("Monday", getHours(monday));
            hours.put("Tuesday", getHours(tuesday));
            hours.put("Wednesday", getHours(wednesday));
            hours.put("Thursday", getHours(thursday));
            hours.put("Friday", getHours(friday));
            hours.put("Saturday", getHours(saturday));
            hours.put("Sunday", getHours(sunday));

            JsonNode customerReviews = restaurant.get("reviews");
            Iterator<JsonNode> reviewIterator = customerReviews.elements();
            Review[] reviews = new Review[iteratorSize(reviewIterator)];
            int reviewIndex = 0;
            while(reviewIterator.hasNext()) {
                JsonNode review = reviewIterator.next();
                String reviewName = String.valueOf(review.get("name"));
                LocalDate reviewDate = LocalDate.parse(String.valueOf(review.get("date")));
                int reviewRating = Integer.parseInt(String.valueOf(review.get("rating")));
                String reviewComments = String.valueOf(review.get("comments"));
                Review customerReview = new Review(reviewName, reviewDate, reviewRating, reviewComments);
                reviews[reviewIndex] = customerReview;
            }
        }
        return null;
    }

    public LocalTime[] getHours(String hours) {
        Map<Long, String> timeOfDay = Map.of(0L, " am", 1L, " pm");
        DateTimeFormatter timeFormatter = new DateTimeFormatterBuilder().appendPattern("hh:mm").appendText(ChronoField.AMPM_OF_DAY, timeOfDay).toFormatter();

        String[] parts = hours.split(", ");
        if(parts.length == 2) {
            String[] spanFirst = parts[0].split(" - ");
            String[] spanSecond = parts[1].split(" - ");
            String fromFirst = spanFirst[0];
            String fromSecond = spanSecond[0];
            String toFirst = spanFirst[1];
            String toSecond = spanSecond[1];
        } else {
            String[] span = parts[0].split(" - ");
            String from = span[0];
            String to = span[1];
        }

        return null;
    }
}
