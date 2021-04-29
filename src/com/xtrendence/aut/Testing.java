package com.xtrendence.aut;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Testing extends JFrame {
    public static Testing instance;
    private JTextArea outputUnitTests;
    private JButton buttonStartUnitTests;
    private JPanel panelMain;
    private JButton buttonStartIntegrationTests;
    private JButton buttonStartFunctionalTests;
    private JTextArea outputIntegrationTests;
    private JTextArea outputFunctionalTests;
    private JCheckBox checkboxMock;
    private JScrollPane scrollUnit;
    private JScrollPane scrollIntegration;
    private JScrollPane scrollFunctional;

    public Testing() {
        this.setSize(1320, 720);
        this.setMinimumSize(new Dimension(1320, 720));
        this.setLocation(100, 100);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("API Testing");

        outputUnitTests.setBackground(new Color(240, 240, 240));
        outputUnitTests.setForeground(new Color(75, 75, 75));
        outputUnitTests.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        outputUnitTests.setFont(outputUnitTests.getFont().deriveFont(16f));

        outputIntegrationTests.setBackground(new Color(240, 240, 240));
        outputIntegrationTests.setForeground(new Color(75, 75, 75));
        outputIntegrationTests.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        outputIntegrationTests.setFont(outputIntegrationTests.getFont().deriveFont(16f));

        outputFunctionalTests.setBackground(new Color(240, 240, 240));
        outputFunctionalTests.setForeground(new Color(75, 75, 75));
        outputFunctionalTests.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        outputFunctionalTests.setFont(outputFunctionalTests.getFont().deriveFont(16f));

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
            FunctionalTesting testing = new FunctionalTesting(checkboxMock.isSelected());
            try {
                if(checkboxMock.isSelected()) {
                    outputFunctional("--- Starting Test w/ Mock API ---");
                } else {
                    outputFunctional("--- Starting Test w/ Real API ---");
                }
                testing.loadRestuarants();
                testing.testGetByCuisine();
            } catch(Exception e) {
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

    public static void outputUnit(String text) {
        instance.outputUnitTests.setText(instance.outputUnitTests.getText() + text + "\n\n");
        JScrollBar scrollBar = instance.scrollUnit.getVerticalScrollBar();
        scrollBar.setValue(scrollBar.getMaximum());
    }

    public static void outputIntegration(String text) {
        instance.outputIntegrationTests.setText(instance.outputIntegrationTests.getText() + text + "\n\n");
        JScrollBar scrollBar = instance.scrollIntegration.getVerticalScrollBar();
        scrollBar.setValue(scrollBar.getMaximum());
    }

    public static void outputFunctional(String text) {
        instance.outputFunctionalTests.setText(instance.outputFunctionalTests.getText() + text + "\n\n");
        JScrollBar scrollBar = instance.scrollFunctional.getVerticalScrollBar();
        scrollBar.setValue(scrollBar.getMaximum());
    }
}