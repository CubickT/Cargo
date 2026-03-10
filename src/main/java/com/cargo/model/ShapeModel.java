package com.cargo.model;

import com.cargo.util.GeometricShape;
import org.locationtech.jts.geom.*;

import static com.cargo.util.GeometryUtils.*;

public class ShapeModel implements GeometricShape {

    GeometryFactory factory = new GeometryFactory();

    double[][] inputPoints;
    Point[] points;
    Coordinate[] coords;
    LinearRing ring;
    Polygon poly;

    public ShapeModel(double[][] pointsIn) {

        this.inputPoints = pointsIn;
        this.coords = toCoordinates(inputPoints);
        this.points = toPoints(coords);
        this.ring = factory.createLinearRing(coords);
        this.poly = factory.createPolygon(ring);

    }

    public ShapeModel() {
    }

    @Override
    public Polygon getPoly() {
        return this.poly;
    }

    @Override
    public Coordinate[] getCoords() {
        return this.coords;
    }

    @Override
    public int getDegreeH() {
        return 0;
    }

    @Override
    public int getDegreeV() {
        return 0;
    }
}
