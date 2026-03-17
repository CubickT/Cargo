package com.cargo.util;

import com.cargo.model.*;
import org.locationtech.jts.geom.*;

import static com.cargo.util.Utils.*;
import static java.lang.Math.abs;

public class GeometryUtils {

    public static Coordinate[] coordsOut(Coordinate[] coordsIn, Polygon boundsPoly) {
        Point[] points =
                toPoints(coordsIn);
        Coordinate[] coordsOut = new Coordinate[coordsIn.length];

        for (int i = 0; i < points.length; i++) {
            if (points[i] != null && coordsIn[i] != null && !boundsPoly.contains(points[i])) {
                coordsOut[i] = new Coordinate(coordsIn[i].x, coordsIn[i].y);
            }
        }
        return coordsOut;

    }

    public static Coordinate[][] coordsOut(Coordinate[][] coordsIn, Polygon boundsPoly) {

        Point[][] points = toPoints(coordsIn);
        Coordinate[][] coordsOut = new Coordinate[coordsIn.length][2];

        for (int i = 0; i < points.length; i++) {


            for (int j = 0; j < points[i].length; j++) {

                if (points[i][j] != null && coordsIn[i][j] != null && !boundsPoly.contains(points[i][j])) {
                    coordsOut[i][j] = new Coordinate(coordsIn[i][j].x, coordsIn[i][j].y);
                }
            }
        }
        return coordsOut;
    }

    public static Coordinate[][] gapCoords(Coordinate[] coords, Polygon boundsPoly) {

        Coordinate[][] gapCoords = new Coordinate[coords.length][2];

        double DELTA = 30;

        Envelope envelope = boundsPoly.getEnvelopeInternal();
        double minX = envelope.getMinX();
        double maxX = envelope.getMaxX();
        double minY = envelope.getMinY();
        double maxY = envelope.getMaxY();

        Geometry boundary = boundsPoly.getBoundary();

        for (int i = 0; i < coords.length; i++) {

            Coordinate[] lineHCoord = {
                    new Coordinate(minX - DELTA, coords[i].y),
                    new Coordinate(maxX + DELTA, coords[i].y)
            };
            LineString lineH = factory.createLineString(lineHCoord);

            Coordinate[] lineVCoord = {
                    new Coordinate(coords[i].x, minY - DELTA),
                    new Coordinate(coords[i].x, maxY + DELTA)
            };
            LineString lineV = factory.createLineString(lineVCoord);

            Coordinate[] intersectionsH = lineH.intersection(boundary).getCoordinates();
            Coordinate[] intersectionsV = lineV.intersection(boundary).getCoordinates();


            if (intersectionsH.length > 0) {
                Coordinate best = null;
                double minDistanceH = Double.MAX_VALUE;
                for (Coordinate h : intersectionsH) {

                    double diff = abs(coords[i].x - h.x);

                    if (diff < minDistanceH) {
                        minDistanceH = diff;
                        best = h;
                    }

                }
                gapCoords[i][0] = best;
            }
            if (intersectionsV.length > 0) {
                Coordinate best = null;
                double minDistanceV = Double.MAX_VALUE;
                for (Coordinate v : intersectionsV) {

                    double diff = abs(coords[i].y - v.y);

                    if (diff < minDistanceV) {
                        minDistanceV = diff;
                        best = v;
                    }

                }
                gapCoords[i][1] = best;
            }

        }

        return gapCoords;
    }

    public static int[][] calculateDegree(Coordinate[] coordsIn, ZoneModel[] zones, ShapeModel bounds) {

        Point[] pointsIn = toPoints(coordsIn);
        Polygon boundsPoly = bounds.getPoly();

        Polygon[] zonePolies = new Polygon[zones.length];
        int[][] result = new int[coordsIn.length][2];


        for (int i = 0; i < zones.length; i++) {
            zonePolies[i] = zones[i].getPoly();
        }

        for (int j = 0; j < coordsIn.length; j++) {
            boolean foundInZone = false;


            for (int i = 0; i < zones.length; i++) {
                if (zonePolies[i].contains(pointsIn[j])) {
                    result[j][0] = zones[i].getDegreeH();
                    result[j][1] = zones[i].getDegreeV();
                    foundInZone = true;
                    break;
                }
            }

            if (!foundInZone) {
                if (boundsPoly.contains(pointsIn[j])) {
                    result[j][0] = 0;
                    result[j][1] = 0;
                } else {

                    result[j][0] = 7;
                    result[j][1] = 4;
                }
            }
        }

        return result;
    }

    public static double[][] generateCirclePoints(int numPoints, double radius, double yOffset) {
        if (numPoints < 2) {
            throw new IllegalArgumentException("numPoints должно быть не меньше 2");
        }
        double[][] points = new double[numPoints][2];
        double angleStep = 2 * Math.PI / (numPoints - 1); // шаг для замыкания окружности
        for (int i = 0; i < numPoints; i++) {
            double angle = i * angleStep;
            points[i][0] = radius * Math.cos(angle);          // x без смещения
            points[i][1] = radius * Math.sin(angle) + yOffset; // y со смещением
        }
        return points;

    }
}
