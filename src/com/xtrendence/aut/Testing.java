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

                UnitTesting testing = new UnitTesting();

                testing.testGetHotelCoordinatesByNeighborhood();
                testing.testGetByCuisine();
                testing.testGetByCuisineAndNeighborhood();
                testing.testGetByDayAndHour();
                testing.testGetByNeighborhood();
                testing.testGetByRating();
                testing.testGetByNeighborhoodAndRating();
                testing.testGetByNeighborhoodAndSortByScore();
                testing.testGetByVicinity();

                outputText("--- Finished Unit Testing w/ Mock Object ---", Color.BLUE);
                outputText("--- Code Coverage & Results ---", Color.MAGENTA);
                outputText("Overall Methods: " + testing.overallMethods, Color.BLACK);
                outputText("Methods Tested: " + testing.testedMethods, Color.BLACK);
                outputText("Methods Passed: " + testing.passedMethods, new Color(0, 150, 0));
                outputText("Methods Failed: " + testing.failedMethods, Color.RED);
            } catch(Exception e) {
                outputText(e.getMessage(), Color.RED);
                e.printStackTrace();
            }
        });

        buttonStartFunctionalTests.addActionListener(actionEvent -> {
            outputTests.setText("");

            try {
                outputText("--- Starting Functional Testing w/ Real API ---", Color.BLUE);

                FunctionalTesting testing = new FunctionalTesting();

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
                outputText("Methods Tested: " + testing.testedMethods, Color.BLACK);
            } catch(Exception e) {
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
        appendToPane(instance.outputTests, text + "\n\n", color);
        JScrollBar scrollBar = instance.scrollOutput.getVerticalScrollBar();
        scrollBar.setValue(scrollBar.getMaximum());
    }

    private static void appendToPane(JTextPane pane, String text, Color color) {
        StyleContext context = StyleContext.getDefaultStyleContext();
        AttributeSet attribute = context.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, color);

        attribute = context.addAttribute(attribute, StyleConstants.FontFamily, "Lucida Console");
        attribute = context.addAttribute(attribute, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

        int length = pane.getDocument().getLength();
        pane.setCaretPosition(length);
        pane.setCharacterAttributes(attribute, false);
        pane.replaceSelection(text);
    }

}