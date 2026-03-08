package com.cargo.util;

import org.locationtech.jts.geom.Coordinate;

public class GeometryUtils {

    public static Coordinate[] toCoordinates(double[][] points) {

        Coordinate[] coords = new Coordinate[points.length + 1];
        for (int i = 0; i < points.length; i++) {

            coords[i] = new Coordinate(points[i][0], points[i][1]);
        }
        coords[points.length] = new Coordinate(points[0][0], points[0][1]);
        return coords;
    }

}
