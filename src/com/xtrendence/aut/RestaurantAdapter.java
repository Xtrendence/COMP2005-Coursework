package com.xtrendence.aut;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.*;

import static com.xtrendence.aut.Utils.iteratorSize;

public class RestaurantAdapter {
    private String json;
    public RestaurantAdapter(String json) {
        this.json = json;
    }

    /* Parses the JSON data and stores the data in Restaurant objects, which contain Review objects, then returns an array of Restaurant objects.
    *  @return Restaurant[] the array of Restaurant objects.
    */
    public Restaurant[] adapt() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(json);
        JsonNode restaurantsNode = jsonNode.get("restaurants");
        Iterator<JsonNode> restaurantIterator = restaurantsNode.elements();
        Restaurant[] restaurants = new Restaurant[iteratorSize(restaurantsNode.elements())];
        int restaurantIndex = 0;

        // Loops over all the values of the "restaurants" key.
        while(restaurantIterator.hasNext()) {
            JsonNode restaurant = restaurantIterator.next();

            int id = Integer.parseInt(String.valueOf(restaurant.get("id")));

            String name = String.valueOf(restaurant.get("name")).replace("\"", "");

            String inspectionScore = String.valueOf(restaurant.get("DOHMH_inspection_score")).replace("\"", "");
            int score;

            try {
                score = Integer.parseInt(inspectionScore);
            } catch(Exception e) {
                score = 28;
            }

            String neighborhood = String.valueOf(restaurant.get("neighborhood")).replace("\"", "");

            String photograph = String.valueOf(restaurant.get("photograph")).replace("\"", "");

            String address = String.valueOf(restaurant.get("address")).replace("\"", "");

            JsonNode location = restaurant.get("latlng");
            double latitude = Double.parseDouble(String.valueOf(location.get("lat")));
            double longitude = Double.parseDouble(String.valueOf(location.get("lng")));
            double[] coordinates = new double[]{latitude, longitude};

            String cuisine = String.valueOf(restaurant.get("cuisine_type")).replace("\"", "");

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
            Review[] reviews = new Review[iteratorSize(customerReviews.elements())];
            int reviewIndex = 0;

            // Loops over all the values of the "reviews" key.
            while(reviewIterator.hasNext()) {
                JsonNode review = reviewIterator.next();
                String reviewName = String.valueOf(review.get("name")).replace("\"", "");
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMMM d, u", Locale.ENGLISH);
                LocalDate reviewDate = LocalDate.parse(String.valueOf(review.get("date")).replace("\"", ""), dateFormatter);
                int reviewRating = Integer.parseInt(String.valueOf(review.get("rating")));
                String reviewComments = String.valueOf(review.get("comments")).replace("\"", "");
                Review customerReview = new Review(reviewName, reviewDate, reviewRating, reviewComments);
                reviews[reviewIndex] = customerReview;
                reviewIndex += 1;
            }

            // Instantiate the Restaurant object, and add it to the "restaurants" array.
            Restaurant restaurantObject = new Restaurant(id, name, score, neighborhood, photograph, address, coordinates, cuisine, hours, reviews);
            restaurants[restaurantIndex] = restaurantObject;
            restaurantIndex += 1;
        }
        return restaurants;
    }

    /* Returns an array of LocalTime objects, which contain the operating hours of a restaurant.
    *  @param hours The hours during which the restaurant is open.
    *  @return LocalTime[] the array of LocalTime objects.
    */
    public LocalTime[] getHours(String hours) {
        // Since some of the operating hours have the previous or next day as the value, this ensures those can be parsed.
        String[] abbreviations = new String[]{"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
        // Most operating hours in the JSON have an "am" and "pm", so the parser works by taking that into account.
        Map<Long, String> timeOfDay = Map.of(0L, " am", 1L, " pm");
        // Although the data is in the format h:mm at times, this is later adjusted to ensure they're all hh:mm.
        DateTimeFormatter timeFormatter = new DateTimeFormatterBuilder().appendPattern("hh:mm").appendText(ChronoField.AMPM_OF_DAY, timeOfDay).toFormatter();

        // If the restaurant is open the whole day, then it'd be open from 12 AM to 11:59 PM.
        if(hours.equalsIgnoreCase("open 24 hours")) {
            LocalTime localFrom = LocalTime.parse("12:00 am", timeFormatter);
            LocalTime localTo = LocalTime.parse("11:59 pm", timeFormatter);
            return new LocalTime[]{localFrom, localTo};
        }

        // If the restaurant is closed, then its operating hours are 0, which means it "opens" at 12 and closes at 12.
        if(hours.equalsIgnoreCase("closed")) {
            LocalTime localFrom = LocalTime.parse("12:00 am", timeFormatter);
            LocalTime localTo = LocalTime.parse("12:00 am", timeFormatter);
            return new LocalTime[]{localFrom, localTo};
        }

        // Some operating hours have two shifts.
        String[] parts = hours.split(", ");

        // If there are two shifts, then there are two time ranges to consider, the "from" and "to" of the first shift, and the "from" and "to" of the second.
        if(parts.length == 2) {
            String[] spanFirst = parts[0].split(" - ");
            String[] spanSecond = parts[1].split(" - ");

            String fromFirst = spanFirst[0];
            String fromSecond = spanSecond[0];

            // If the "from" is the name of a day of the week, then it is assumed that that day starts at 12 AM.
            if(Arrays.asList(abbreviations).contains(fromFirst)) {
                fromFirst = "12:00 am";
            }
            if(Arrays.asList(abbreviations).contains(fromSecond)) {
                fromFirst = "12:00 am";
            }

            String toFirst = spanFirst[1];
            String toSecond = spanSecond[1];

            if(Arrays.asList(abbreviations).contains(toFirst)) {
                toFirst = "11:59 pm";
            }
            if(Arrays.asList(abbreviations).contains(toSecond)) {
                toSecond = "11:59 pm";
            }

            int timeFromFirst = Integer.parseInt(fromFirst.split(":")[0]);
            int timeFromSecond = Integer.parseInt(fromSecond.split(":")[0]);

            int timeToFirst = Integer.parseInt(toFirst.split(":")[0]);
            int timeToSecond = Integer.parseInt(toSecond.split(":")[0]);

            if(timeFromFirst < 10) {
                fromFirst = "0" + fromFirst;
            }
            if(timeFromSecond < 10) {
                fromSecond = "0" + fromSecond;
            }
            if(timeToFirst < 10) {
                toFirst = "0" + toFirst;
            }
            if(timeToSecond < 10) {
                toSecond = "0" + toSecond;
            }

            LocalTime localFromFirst = LocalTime.parse(fromFirst, timeFormatter);
            LocalTime localToFirst = LocalTime.parse(toFirst, timeFormatter);
            LocalTime localFromSecond = LocalTime.parse(fromSecond, timeFormatter);
            LocalTime localToSecond = LocalTime.parse(toSecond, timeFormatter);

            return new LocalTime[]{localFromFirst, localToFirst, localFromSecond, localToSecond};
        } else {
            String[] span = parts[0].split(" - ");
            String from = span[0];
            String to = span[1];

            if(Arrays.asList(abbreviations).contains(from)) {
                from = "12:00 am";
            }
            if(Arrays.asList(abbreviations).contains(to)) {
                to = "11:59 pm";
            }

            int timeFrom = Integer.parseInt(from.split(":")[0]);
            int timeTo = Integer.parseInt(to.split(":")[0]);

            if(timeFrom < 10) {
                from = "0" + from;
            }
            if(timeTo < 10) {
                to = "0" + to;
            }

            LocalTime localFrom = LocalTime.parse(from, timeFormatter);
            LocalTime localTo = LocalTime.parse(to, timeFormatter);

            return new LocalTime[]{localFrom, localTo};
        }
    }
}