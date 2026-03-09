package com.cargo.model;

import org.locationtech.jts.geom.Coordinate;

import static com.cargo.util.GeometryUtils.toPoints;

public class ZoneShape extends PolygonShape{

    int zone;
    int degree;

    public ZoneShape(Coordinate[] coordsIn, int zone,int degree){

        this.coords = coordsIn;
        this.points = toPoints(coords);
        this.ring = factory.createLinearRing(coords);
        this.poly = factory.createPolygon(ring);

        this.zone = zone;
        this.degree = degree;

    }

}
