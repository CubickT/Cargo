package com.cargo.model;

import com.cargo.util.GeometricShape;
import org.locationtech.jts.geom.*;

import static com.cargo.util.GeometryUtils.*;

public class PolygonShape implements GeometricShape {

    GeometryFactory factory = new GeometryFactory();

    double[][] points;
    Coordinate[] coords;
    LinearRing ring;
    Polygon poly;

    public PolygonShape(double[][] pointsIn) {

        this.points = pointsIn;
        this.coords = toCoordinates(points);
        this.ring = factory.createLinearRing(coords);
        this.poly = factory.createPolygon(ring);

    }

    @Override
    public Polygon getPoly() {
        return this.poly;
    }
}
