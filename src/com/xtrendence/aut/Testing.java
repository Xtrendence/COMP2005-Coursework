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
    private JCheckBox checkboxMock;
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

        buttonStartFunctionalTests.addActionListener(actionEvent -> {
            outputTests.setText("");

            try {
                FunctionalTesting testing = new FunctionalTesting(checkboxMock.isSelected());

                if(checkboxMock.isSelected()) {
                    outputText("--- Starting Test w/ Mock API ---", Color.BLUE);
                } else {
                    outputText("--- Starting Test w/ Real API ---", Color.BLUE);
                }

                testing.testGetByCuisine();
                testing.testGetByCuisineAndNeighborhood();
                testing.testGetByDayAndHour();
                testing.testGetByNeighborhood();
                testing.testGetByRating();
                testing.testGetByNeighborhoodAndRating();
                testing.testGetByNeighborhoodAndSortByScore();
                testing.testGetByVicinity();
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