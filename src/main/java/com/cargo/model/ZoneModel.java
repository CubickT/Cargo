package com.cargo.model;

import org.locationtech.jts.geom.Coordinate;

import static com.cargo.util.GeometryUtils.toPoints;

public class ZoneModel extends ShapeModel {

    int zone;
    int degree;

    int zoneH;
    int zoneV;

    public ZoneModel(Coordinate[] coordsIn, int zoneH, int zoneV) {

        this.coords = coordsIn;
        this.points = toPoints(coords);
        this.ring = factory.createLinearRing(coords);
        this.poly = factory.createPolygon(ring);

        this.zoneH = zoneH;
        this.zoneV = zoneV;

    }

    @Override
    public int getZone() {
        return this.zone;
    }

    public int getDegree() {
        return this.degree;
    }

}
