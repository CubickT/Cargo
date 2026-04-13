package com.cargo.model;

import org.locationtech.jts.geom.Coordinate;

import static com.cargo.util.Utils.toPoints;

public class ZoneModel extends ShapeModel {


    int degreeH;
    int degreeT;
    int degreeB;

    public ZoneModel(Coordinate[] coordsIn, int degreeH, int degreeV, int degreeB) {

        this.coords = coordsIn;
        this.points = toPoints(coords);
        this.ring = factory.createLinearRing(coords);
        this.poly = factory.createPolygon(ring);

        this.degreeH = degreeH;
        this.degreeT = degreeV;
        this.degreeB = degreeB;

    }

    @Override
    public int getDegreeH() {
        return degreeH;
    }

    @Override
    public int getDegreeT() {
        return degreeT;
    }

    @Override
    public  int getDegreeB(){return degreeB;}
}
