package com.cargo.model;

import com.cargo.util.GeometricShape;
import org.locationtech.jts.geom.*;

import static com.cargo.util.Utils.*;

public class ShapeModel implements GeometricShape {

    public enum ObjectType {
        CARGO, BOUNDS, ZONES
    }

    GeometryFactory factory = new GeometryFactory();


    String name;
    ObjectType type;
    double[][] inputPoints;
    Point[] points;
    Coordinate[] coords;
    LinearRing ring;
    Polygon poly;

    public ShapeModel(String name, ObjectType type, double[][] pointsIn) {

        this.name = name;
        this.type = type;
        this.inputPoints = pointsIn;
        this.coords = toCoordinates(inputPoints);
        this.points = toPoints(coords);
        this.ring = factory.createLinearRing(coords);
        this.poly = factory.createPolygon(ring);

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
