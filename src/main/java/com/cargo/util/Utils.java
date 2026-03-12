package com.cargo.util;

import org.locationtech.jts.geom.*;

import static java.lang.Math.abs;

public class Utils {

    static GeometryFactory factory = new GeometryFactory();

    public static Coordinate[] toCoordinates(double[][] pointsIn) {

        Coordinate[] coords = new Coordinate[pointsIn.length + 1];
        for (int i = 0; i < pointsIn.length; i++) {

            coords[i] = new Coordinate(pointsIn[i][0], pointsIn[i][1]);
        }
        coords[pointsIn.length] = new Coordinate(pointsIn[0][0], pointsIn[0][1]);
        return coords;
    }

    public static Point[] toPoints(Coordinate[] coordsIn) {


        Point[] points = new Point[coordsIn.length];
        for (int i = 0; i < coordsIn.length; i++) {
            points[i] = factory.createPoint(coordsIn[i]);
        }
        return points;
    }

    public static Point[][] toPoints(Coordinate[][] coordsIn) {


        Point[][] points = new Point[coordsIn.length][2];
        for (int i = 0; i < coordsIn.length; i++) {
            points[i][0] = factory.createPoint(coordsIn[i][0]);
            points[i][1] = factory.createPoint(coordsIn[i][1]);
        }
        return points;
    }

    public static Coordinate[] coordAbs(Coordinate[] coordsIn) {
        Coordinate[] coordsAbs = new Coordinate[coordsIn.length];
        Point[] points = toPoints(coordsIn);

        for (int i = 0; i < coordsIn.length; i++) {
            coordsAbs[i] = new Coordinate(
                    abs(points[i].getX()),
                    abs(points[i].getY())
            );
        }
        return coordsAbs;
    }

    public static int[] maxInArray(int[][] array) {
        int max0 = 0;
        int max1 = 0;
        for (int[] ints : array) {

            if (ints[0] > max0) {
                max0 = ints[0];
            }
            if (ints[1] > max1) {
                max1 = ints[1];
            }

        }
        return new int[]{max0, max1};
    }

    public static int calculateHeightIndex(double height) {

        double[] tableHeights = {800, 1000, 1200, 1400, 1600, 1800, 2000, 2200, 2500, 2800, 3100, 3400, 3700, 4000, 4200, 4300, 4600, 4900, 5200, 5550};
        int heightIndex = 0;

        for (int i = 0; i < tableHeights.length; i++) {

            if (height >= tableHeights[i]) {
                heightIndex = i;
            }

        }
        return heightIndex;
    }

}
