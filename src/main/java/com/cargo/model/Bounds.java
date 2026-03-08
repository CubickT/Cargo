package com.cargo.model;

import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geom.Polygon;

public class Bounds {

    public Bounds() {

        GeometryFactory factory = new GeometryFactory();

        double[][] points;
        LinearRing boundsRing = factory.createLinearRing();
        Polygon boundsPoly = factory.createPolygon();


    }

}
