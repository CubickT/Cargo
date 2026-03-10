package com.cargo.util;

import org.locationtech.jts.geom.Coordinate;

import java.util.ArrayList;

public class InitializationUtils {

    public static ArrayList<ArrayList<Coordinate[]>> zonesInitialization() {
        ArrayList<ArrayList<Coordinate[]>> zoneList = new ArrayList<>(2);
        ArrayList<Coordinate[]> bottomZone = bottomZoneListCreation();
        ArrayList<Coordinate[]> sideZone = sideZoneListCreation();

        zoneList.add(bottomZone);
        zoneList.add(sideZone);

        return zoneList;
    }


    public static ArrayList<Coordinate[]> bottomZoneListCreation() {
        ArrayList<Coordinate[]> bottomZone = new ArrayList<>(6);

        double[] xs = {1625, 1700, 1760, 1850, 2000, 2080, 2240};
        double[] ys = {480, 1230, 1399}; // y0, y1, y2

        double y0 = ys[0];
        double y1 = ys[1];
        double y2 = ys[2];

        for (int i = 0; i < xs.length - 1; i++) {
            double xLeft = xs[i];
            double xRight = xs[i + 1];
            double yLow = (i <= 1) ? y0 : y1; // первые два интервала — y0, остальные — y1
            double yHigh = y2;

            Coordinate[] coords = {
                    new Coordinate(xLeft, yLow),
                    new Coordinate(xRight, yLow),
                    new Coordinate(xRight, yHigh),
                    new Coordinate(xLeft, yHigh),
                    new Coordinate(xLeft, yLow) // замыкание
            };

            bottomZone.add(coords);
        }


        return bottomZone;
    }

    public static ArrayList<Coordinate[]> sideZoneListCreation() {
        ArrayList<Coordinate[]> sideZone = new ArrayList<>(6);

        double[] xs = {1625, 1700, 1760, 1850, 2000, 2080, 2240};
        double minY = 1400;
        double[] ys = {4000, 3700, 3400, 2800};

        for (int i = 0; i < xs.length - 1; i++) {
            double xLeft = xs[i];
            double xRight = xs[i + 1];
            double yLow = minY;
            double yLeftTop, yRightTop;

            if (i < 3) {
                yLeftTop = ys[0];
                yRightTop = ys[0];
            } else {
                yLeftTop = ys[i - 3];
                yRightTop = ys[i - 2];
            }

            Coordinate[] coords = {
                    new Coordinate(xLeft, yLow),       // левая нижняя
                    new Coordinate(xRight, yLow),      // правая нижняя
                    new Coordinate(xRight, yRightTop), // правая верхняя
                    new Coordinate(xLeft, yLeftTop),   // левая верхняя
                    new Coordinate(xLeft, yLow)        // замыкание
            };

            sideZone.add(coords);

        }

        return sideZone;

    }


}
