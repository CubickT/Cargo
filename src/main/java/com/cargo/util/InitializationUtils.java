package com.cargo.util;

import com.cargo.model.ZoneModel;
import org.locationtech.jts.geom.Coordinate;

import java.util.ArrayList;

public class InitializationUtils {

    public static ZoneModel[] zonesInitialization() {

        ArrayList<Coordinate[]> bottomZoneCoords = bottomZoneListCreation();
        ArrayList<Coordinate[]> sideZoneCoords = sideZoneListCreation();
        ArrayList<Coordinate[]> topZoneCoords = topZoneListCreation();

        ZoneModel[] bottomZones = zoneModelsInitialization(bottomZoneCoords,"bottom");
        ZoneModel[] sideZones = zoneModelsInitialization(sideZoneCoords,"side");
        ZoneModel[] topZones = zoneModelsInitialization(topZoneCoords, "top");

        ZoneModel[] zones = new ZoneModel[bottomZones.length + sideZones.length + topZones.length];
        int index = 0;
        for (ZoneModel z : bottomZones) zones[index++] = z;
        for (ZoneModel z : sideZones)   zones[index++] = z;
        for (ZoneModel z : topZones)    zones[index++] = z;

        return zones;
    }


    public static ZoneModel[] zoneModelsInitialization(ArrayList<Coordinate[]> coordArray, String ZONE) {

        ZoneModel[] zoneModels = new ZoneModel[coordArray.size()];


        switch (ZONE) {
            case "bottom":
            case "top":
                for (int i = 0; i < coordArray.size(); i++) {
                    Coordinate[] coords = coordArray.get(i);
                    zoneModels[i] = new ZoneModel(coords, 0, i+1);
                }
                break;
            case "side":
                for (int i = 0; i < coordArray.size(); i++) {
                    Coordinate[] coords = coordArray.get(i);
                    zoneModels[i] = new ZoneModel(coords, i + 1, 0);
                }
                break;
        }
        return zoneModels;
    }

    public static ArrayList<Coordinate[]> bottomZoneListCreation() {
        ArrayList<Coordinate[]> bottomZone = new ArrayList<>(6);

        double[] xs = {1625, 1700, 1760, 1850, 2000, 2080, 2240};
        double[] ys = {480, 1230, 1399}; // y0, y1, y2

        double y0 = ys[0];
        double y1 = ys[1];
        double maxY = ys[2];

        for (int i = 0; i < xs.length - 1; i++) {
            double xLeft = xs[i];
            double xRight = xs[i + 1];
            double yLow = (i <= 1) ? y0 : y1; // первые два интервала — y0, остальные — y1

            Coordinate[] coords = {
                    new Coordinate(xLeft, yLow),
                    new Coordinate(xRight, yLow),
                    new Coordinate(xRight, maxY),
                    new Coordinate(xLeft, maxY),
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
            double yLeftTop, yRightTop;

            if (i < 3) {
                yLeftTop = ys[0];
                yRightTop = ys[0];
            } else {
                yLeftTop = ys[i - 3];
                yRightTop = ys[i - 2];
            }

            Coordinate[] coords = {
                    new Coordinate(xLeft, minY),       // левая нижняя
                    new Coordinate(xRight, minY),      // правая нижняя
                    new Coordinate(xRight, yRightTop), // правая верхняя
                    new Coordinate(xLeft, yLeftTop),   // левая верхняя
                    new Coordinate(xLeft, minY)        // замыкание
            };

            sideZone.add(coords);

        }

        return sideZone;

    }

    public static ArrayList<Coordinate[]> topZoneListCreation() {

        ArrayList<Coordinate[]> topZone = new ArrayList<>(3);

        double minY = 4000;
        double maxY = 5300;
        double[] bottomX = {1625, 1700, 1800, 1850};
        double[] topX = {620, 880, 1020, 1140};

        for (int i = 0; i < bottomX.length - 1; i++) {

            double xLeftT = topX[i];
            double xRightT = topX[i + 1];
            double xLeftB = bottomX[i];
            double xRightB = bottomX[i + 1];

            Coordinate[] coords = {
                    new Coordinate(xLeftB, minY),
                    new Coordinate(xRightB, minY),
                    new Coordinate(xRightT, maxY),
                    new Coordinate(xLeftT, maxY),
                    new Coordinate(xLeftB, minY) // замыкание
            };
            topZone.add(coords);
        }
        return topZone;
    }


}
