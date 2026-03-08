package com.cargo;

import com.cargo.ui.MainFrame;
import org.locationtech.jts.geom.*;

import java.util.Arrays;


public class Main {

    public static void main(String[] args) {

        GeometryFactory factory = new GeometryFactory();
        System.out.println("JTS работает! Фабрика создана: " + factory);

        double[][] bounds = {{0, 230}, {325, 230}, {325, 0}, {2925, 0}, {2925, 230}, {3250, 230}, {3250, 3770}, {2245, 5070}, {1005, 5070}, {0, 3770}};
        double[][] cargo = {{300, 300}, {3000, 300}, {3000, 3000}, {300, 3000}};

        Coordinate[] boundsCoord = toCoordinates(bounds);
        LinearRing boundsRing = factory.createLinearRing(boundsCoord);
        Polygon boundsPoly = factory.createPolygon(boundsRing);


        Coordinate[] cargoCoord = toCoordinates(cargo);
        LinearRing cargoRing = factory.createLinearRing(cargoCoord);
        Polygon cargoPoly = factory.createPolygon(cargoRing);

        System.out.println(Arrays.toString(boundsCoord));
        System.out.println(Arrays.toString(cargoCoord));

        System.out.println("Габарит: " + boundsPoly);
        System.out.println("Объект: " + cargoPoly);

        if (boundsPoly.contains(cargoPoly)) {
            System.out.println("Объект входит в габарит");
        } else {
            System.out.println("Объект не входит в габарит");
        }

        MainFrame frame = new MainFrame(boundsPoly, cargoPoly);
        frame.setVisible(true);


    }

    private static Coordinate[] toCoordinates(double[][] points) {

        Coordinate[] coords = new Coordinate[points.length + 1];
        for (int i = 0; i < points.length; i++) {

            coords[i] = new Coordinate(points[i][0], points[i][1]);
        }
        coords[points.length] = new Coordinate(points[0][0], points[0][1]);
        return coords;
    }
}