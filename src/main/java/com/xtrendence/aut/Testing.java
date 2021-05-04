package com.xtrendence.aut;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;

public class Testing extends JFrame {
    public static Testing instance;
    private JButton buttonStartUnitTests;
    private JPanel panelMain;
    private JButton buttonStartIntegrationTests;
    private JButton buttonStartFunctionalTests;
    private JTextPane outputTests;
    private JScrollPane scrollOutput;
    private JCheckBox cornerCases;
    private JCheckBox edgeCases;

    public Testing() {
        this.setSize(1320, 720);
        this.setMinimumSize(new Dimension(1320, 720));
        this.setLocation(100, 100);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("API Testing");

        outputTests.setSize(100, outputTests.getHeight());
        outputTests.setBackground(new Color(240, 240, 240));
        outputTests.setForeground(new Color(75, 75, 75));
        outputTests.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        outputTests.setFont(outputTests.getFont().deriveFont(16f));

        buttonStartUnitTests.setOpaque(true);
        buttonStartUnitTests.setBackground(new Color(0,125,255));
        buttonStartUnitTests.setForeground(new Color(255,255,255));

        buttonStartIntegrationTests.setOpaque(true);
        buttonStartIntegrationTests.setBackground(new Color(0,125,255));
        buttonStartIntegrationTests.setForeground(new Color(255,255,255));

        buttonStartFunctionalTests.setOpaque(true);
        buttonStartFunctionalTests.setBackground(new Color(0,125,255));
        buttonStartFunctionalTests.setForeground(new Color(255,255,255));

        buttonStartUnitTests.addActionListener(actionEvent -> {
            outputTests.setText("");

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

                if(edgeCases.isSelected()) {
                    testing.testGetByNeighborhoodNonExistent();
                    testing.testGetByNeighborhoodEmpty();
                    testing.testGetByNeighborhoodNumber();
                    testing.testGetByNeighborhoodAndRatingNegative();
                    testing.testGetByDayAndHourInvalidDay();
                    testing.testGetByDayAndHourInvalidHour();
                }

                if(cornerCases.isSelected()) {
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
        });

        buttonStartIntegrationTests.addActionListener(actionEvent -> {
            outputTests.setText("");

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
        });

        buttonStartFunctionalTests.addActionListener(actionEvent -> {
            outputTests.setText("");

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
        });
    }

    public static void main(String[] args) {
        Testing testing = new Testing();
        instance = testing;
        testing.setVisible(true);
        testing.setContentPane(testing.panelMain);
    }

    public static void outputText(String text, Color color) {
        System.out.println(text);
        appendToPane(instance.outputTests, text + "\n\n", color);
        JScrollBar scrollBar = instance.scrollOutput.getVerticalScrollBar();
        scrollBar.setValue(scrollBar.getMaximum());
    }

    private static void appendToPane(JTextPane pane, String text, Color color) {
        StyleContext context = StyleContext.getDefaultStyleContext();
        AttributeSet attribute = context.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, color);

        attribute = context.addAttribute(attribute, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

        int length = pane.getDocument().getLength();
        pane.setCaretPosition(length);
        pane.setCharacterAttributes(attribute, false);
        pane.replaceSelection(text);
    }

}