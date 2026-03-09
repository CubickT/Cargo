package com.cargo.ui;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Polygon;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame(Polygon bounds, Polygon cargo, Coordinate[][] gapCoords, String info){
        setTitle("Габаритность");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800,800);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(Color.LIGHT_GRAY);
        JLabel infoLabel = new JLabel(info);
        infoPanel.add(infoLabel);
        mainPanel.add(infoPanel, BorderLayout.NORTH);

        DrawPanel drawPanel = new DrawPanel(bounds, cargo, gapCoords);
        mainPanel.add(drawPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

}
