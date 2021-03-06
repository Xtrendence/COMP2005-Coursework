package com.xtrendence.aut;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Testing {
    public Testing() { }

    public static void startUnitTests(boolean edgeCases, boolean cornerCases) {
        try {
            outputText("--- Starting Unit Testing w/ Mock Object ---", Color.BLUE);

            // Unit tests ensure that every small part of the software works as intended, while considering and testing edge cases and corner cases.
            UnitTesting testing = new UnitTesting();

            testing.testIteratorSize();
            testing.testSumIntegerArray();
            testing.testSortArrayAscendingDoubles();
            testing.testSortArrayDescending();
            testing.testReverse();
            testing.testDistanceBetween();
            testing.testGetHotels();
            testing.testGetHotelCoordinatesByNeighborhood();
            testing.testGetByCuisine();
            testing.testGetByCuisineAndNeighborhood();
            testing.testGetByDayAndHour();
            testing.testGetByNeighborhood();
            testing.testGetByRating();
            testing.testGetByNeighborhoodAndRating();
            testing.testGetByNeighborhoodAndSortByScore();
            testing.testGetByVicinity();

            if(edgeCases) {
                testing.testGetByNeighborhoodNonExistent();
                testing.testGetByNeighborhoodEmpty();
                testing.testGetByNeighborhoodNumber();
                testing.testGetByNeighborhoodAndRatingNegative();
                testing.testGetByDayAndHourInvalidDay();
                testing.testGetByDayAndHourInvalidHour();
            }

            if(cornerCases) {
                testing.testGetByCuisineAndNeighborhoodNonExistent();
                testing.testGetByNeighborhoodAndRatingNonExistentNegative();
            }

            outputText("--- Finished Unit Testing w/ Mock Object ---", Color.BLUE);
            outputText("--- Code Coverage & Results ---", Color.MAGENTA);
            outputText("Overall Methods: " + testing.overallMethods, Color.BLACK);
            outputText("Methods Tested: " + testing.testedMethods + " (" + (testing.testedMethods * 100) / testing.overallMethods + "%)", Color.BLACK);
            outputText("Methods Passed: " + testing.passedMethods + " (" + (testing.passedMethods * 100) / testing.overallMethods + "%)", new Color(0, 150, 0));
            outputText("Methods Failed: " + testing.failedMethods  + " (" + (100 - ((testing.passedMethods * 100) / testing.overallMethods)) + "%)", Color.RED);
        } catch(Exception | Error e) {
            outputText(e.getMessage(), Color.RED);
            e.printStackTrace();
        }
    }

    public static void startIntegrationTests() {
        try {
            outputText("--- Starting Integration Testing w/ Mock Object ---", Color.BLUE);

            // Integration tests ensure that the different methods and parts of the software work together.
            IntegrationTesting testing = new IntegrationTesting();

            testing.testHotelDistanceFromRestaurant();
            testing.testGetByNeighborhoodAndDayAndHour();
            testing.testGetByNeighborhoodAndDayAndHourAndRating();
            testing.testGetByVicinityAndCuisine();
            testing.testGetByNeighborhoodAndCuisineAndRating();
            testing.testGetByCuisineAndSortByScore();
            testing.testGetByRatingAndSortByScore();

            outputText("--- Finished Integration Testing w/ Mock Object ---", Color.BLUE);
            outputText("--- Code Coverage & Results ---", Color.MAGENTA);
            outputText("Overall Methods: " + testing.overallMethods, Color.BLACK);
            outputText("Methods Tested: " + testing.testedMethods + " (" + (testing.testedMethods * 100) / testing.overallMethods + "%)", Color.BLACK);
            outputText("Methods Passed: " + testing.passedMethods + " (" + (testing.passedMethods * 100) / testing.overallMethods + "%)", new Color(0, 150, 0));
            outputText("Methods Failed: " + testing.failedMethods  + " (" + (100 - ((testing.passedMethods * 100) / testing.overallMethods)) + "%)", Color.RED);
        } catch(Exception | Error e) {
            outputText(e.getMessage(), Color.RED);
            e.printStackTrace();
        }
    }

    public static void startFunctionalTests() {
        try {
            outputText("--- Starting Functional Testing w/ Real API ---", Color.BLUE);

            // Functional tests ensure that the software meets the requirements of the client.
            FunctionalTesting testing = new FunctionalTesting();

            // The arguments of the following methods can be changed in order to verify that the requirements are fully met.
            testing.testGetByCuisine("Asian");
            testing.testGetByCuisineAndNeighborhood("Manhattan", "Asian");
            testing.testGetByDayAndHour("Saturday", "5:30 PM");
            testing.testGetByNeighborhood("Manhattan");
            testing.testGetByRating(4.5);
            testing.testGetByNeighborhoodAndRating("Manhattan", 3.85);
            testing.testGetByNeighborhoodAndSortByScore("Manhattan");
            testing.testGetByVicinity("Brooklyn");

            outputText("--- Finished Functional Testing w/ Real API ---", Color.BLUE);
            outputText("--- Code Coverage & Results ---", Color.MAGENTA);
            outputText("Overall Methods: " + testing.overallMethods, Color.BLACK);
            outputText("Methods Tested: " + testing.testedMethods + " (" + (testing.testedMethods * 100) / testing.overallMethods + "%)", Color.BLACK);
        } catch(Exception | Error e) {
            outputText(e.getMessage(), Color.RED);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        if(args.length == 0 || args[0].equalsIgnoreCase("help")) {
            System.out.println("Methods:");
            System.out.println("help");
            System.out.println("startUnitTests");
            System.out.println("startUnitTests edgeCases");
            System.out.println("startUnitTests cornerCases");
            System.out.println("startUnitTests edgeCases cornerCases");
            System.out.println("startIntegrationTests");
            System.out.println("startFunctionalTests");
            System.out.println("\n");
            System.out.println("To run a command against the JAR file:");
            System.out.println("Examples:");
            System.out.println("java -jar Headless.jar startUnitTests");
            System.out.println("java -jar Headless.jar startUnitTests edgeCases");
            System.out.println("java -jar Headless.jar startUnitTests edgeCases cornerCases");
            System.out.println("java -jar Headless.jar startFunctionalTests");
            System.out.println("\n");
            System.out.println("If you're running the project directly, simply type a method from the list above:");
            System.out.println("Examples:");
            System.out.println("Please enter a command: startUnitTests");
            System.out.println("Please enter a command: startUnitTests edgeCases");
            System.out.println("Please enter a command: startUnitTests edgeCases cornerCases");
            System.out.println("Please enter a command: startFunctionalTests");
            System.out.println("\n");
            getInput();
        } else {
            processInput(args);
        }
    }

    public static void getInput() throws IOException {
        if(System.console() != null) {
            System.out.println("Please enter a command: ");
            String input = System.console().readLine();
            String[] inputStrings = input.split(" ");
            processInput(inputStrings);
        } else {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Please enter a command: ");
            String input = reader.readLine();
            String[] inputStrings = input.split(" ");
            processInput(inputStrings);
        }
    }
    
    public static void processInput(String[] args) throws IOException {
        if(args.length == 1 && args[0].equalsIgnoreCase("startUnitTests")) {
            startUnitTests(false, false);
        }
        if(args.length == 2 && args[0].equalsIgnoreCase("startUnitTests") && args[1].equalsIgnoreCase("edgeCases")) {
            startUnitTests(true, false);
        }
        if(args.length == 2 && args[0].equalsIgnoreCase("startUnitTests") && args[1].equalsIgnoreCase("cornerCases")) {
            startUnitTests(false, true);
        }
        if(args.length == 3 && args[0].equalsIgnoreCase("startUnitTests") && args[1].equalsIgnoreCase("edgeCases") && args[2].equalsIgnoreCase("cornerCases")) {
            startUnitTests(true, true);
        }
        if(args.length == 3 && args[0].equalsIgnoreCase("startUnitTests") && args[1].equalsIgnoreCase("cornerCases") && args[2].equalsIgnoreCase("edgeCases")) {
            startUnitTests(true, true);
        }
        if(args.length == 1 && args[0].equalsIgnoreCase("startIntegrationTests")) {
            startIntegrationTests();
        }
        if(args.length == 1 && args[0].equalsIgnoreCase("startFunctionalTests")) {
            startFunctionalTests();
        }
        getInput();
    }

    public static void outputText(String text, Color color) {
        System.out.println(text);
    }
}