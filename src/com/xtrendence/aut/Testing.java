package com.xtrendence.aut;

import javax.swing.*;
import java.awt.*;

public class Testing extends JFrame {
    private JTextArea outputUnitTests;
    private JButton buttonStartUnitTests;
    private JPanel panelMain;
    private JButton buttonStartIntegrationTests;
    private JButton buttonStartFunctionalTests;
    private JTextArea outputIntegrationTests;
    private JTextArea outputFunctionalTests;

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
    }

    public static void main(String[] args) {
        Testing testing = new Testing();
        testing.setVisible(true);
        testing.setContentPane(testing.panelMain);
    }
}