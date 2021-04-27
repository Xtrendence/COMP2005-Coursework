package com.xtrendence.aut;

import javax.swing.*;
import java.awt.*;

public class Testing extends JFrame {
    private JTextArea output;
    private JButton buttonStart;
    private JPanel panelMain;

    public Testing() {
        this.setSize(1000, 600);
        this.setLocation(100, 100);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("API Testing");

        output.setBackground(new Color(240, 240, 240));
        output.setForeground(new Color(75, 75, 75));
        output.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        output.setFont(output.getFont().deriveFont(16f));

        buttonStart.setOpaque(true);
        buttonStart.setBackground(new Color(0,125,255));
        buttonStart.setForeground(new Color(255,255,255));
    }

    public static void main(String[] args) {
        Testing testing = new Testing();
        testing.setVisible(true);
        testing.setContentPane(testing.panelMain);
    }
}