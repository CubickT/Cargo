package com.cargo.util;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Polygon;

public interface GeometricShape {
    Polygon getPoly();
    Coordinate[] getCoords();
}
