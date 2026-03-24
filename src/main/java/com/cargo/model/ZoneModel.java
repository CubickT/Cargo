package com.cargo.model;

import org.locationtech.jts.geom.Coordinate;

import static com.cargo.util.Utils.toPoints;

public class ZoneModel extends ShapeModel {


    int degreeH;
    int degreeV;

    public ZoneModel(String name, ObjectType type, Coordinate[] coordsIn, int degreeH, int degreeV) {

        super(name, type, coordinatesToDoubleArray(coordsIn));

        this.degreeH = degreeH;
        this.degreeV = degreeV;


    }

    private static double[][] coordinatesToDoubleArray(Coordinate[] coords) {
        double[][] result = new double[coords.length][2];
        for (int i = 0; i < coords.length; i++) {
            result[i][0] = coords[i].x;
            result[i][1] = coords[i].y;
        }
        return result;
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
