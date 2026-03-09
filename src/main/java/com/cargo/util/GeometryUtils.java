package com.cargo.util;

import org.locationtech.jts.geom.*;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;

import java.awt.*;

import static java.lang.Math.abs;

public class GeometryUtils {

    static GeometryFactory factory = new GeometryFactory();

    public static Coordinate[] toCoordinates(double[][] pointsIn) {

        Coordinate[] coords = new Coordinate[pointsIn.length + 1];
        for (int i = 0; i < pointsIn.length; i++) {

            coords[i] = new Coordinate(pointsIn[i][0], pointsIn[i][1]);
        }
        coords[pointsIn.length] = new Coordinate(pointsIn[0][0], pointsIn[0][1]);
        return coords;
    }

    public static Point[] toPoints(Coordinate[] coords) {


        Point[] points = new Point[coords.length + 1];
        for (int i = 0; i < coords.length; i++) {
            points[i] = factory.createPoint(coords[i]);
        }
        return points;
    }

    public static Point[][] toPoints(Coordinate[][] coords) {


        Point[][] points = new Point[coords.length + 1][2];
        for (int i = 0; i < coords.length; i++) {
            points[i][0] = factory.createPoint(coords[i][0]);
            points[i][1] = factory.createPoint(coords[i][1]);
        }
        return points;
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

    public static Coordinate[] coordsOut(Coordinate[] coordsIn,Polygon boundsPoly){
        Point[] points = toPoints(coordsIn);
        Coordinate[] coordsOut = new Coordinate[coordsIn.length];

        for(int i = 0; i < points.length; i++){
            if(points[i] != null && coordsIn[i] != null &&!boundsPoly.contains(points[i])){
                coordsOut[i] = new Coordinate(coordsIn[i].x,coordsIn[i].y);
            }
        }
        return  coordsOut;

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

}
