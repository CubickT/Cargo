package com.cargo.model;

import org.locationtech.jts.geom.Coordinate;

import static com.cargo.util.GeometryUtils.toPoints;

public class ZoneModel extends ShapeModel {


    int degreeH;
    int degreeV;

    public ZoneModel(Coordinate[] coordsIn, int degreeH, int degreeV) {

        this.coords = coordsIn;
        this.points = toPoints(coords);
        this.ring = factory.createLinearRing(coords);
        this.poly = factory.createPolygon(ring);

        this.degreeH = degreeH;
        this.degreeV = degreeV;

    }

    @Override
    public int getDegreeH() {
        return degreeH;
    }

    @Override
    public int getDegreeV() {
        return degreeV;
    }
}
