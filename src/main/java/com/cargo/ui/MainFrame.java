package com.cargo.ui;

import org.locationtech.jts.geom.Polygon;

import javax.swing.*;

public class MainFrame extends JFrame {

    public MainFrame(Polygon bounds, Polygon cargo){
        setTitle("Габаритность");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800,800);
        setLocationRelativeTo(null);

        DrawPanel drawPanel = new DrawPanel(bounds, cargo);
        add(drawPanel);
    }

}
