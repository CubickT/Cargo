package com.cargo;

import com.cargo.model.PolygonShape;
import com.cargo.ui.MainFrame;
import org.locationtech.jts.geom.*;

import java.util.Arrays;

import static com.cargo.util.GeometryUtils.gapPoints;


public class Main {
    @SuppressWarnings("unused")
    static void main(String[] args) {

        GeometryFactory factory = new GeometryFactory();
        System.out.println("JTS работает! Фабрика создана: " + factory);

        double[][] boundsIn = {{0, 230}, {325, 230}, {325, 0}, {2925, 0}, {2925, 230}, {3250, 230}, {3250, 3770}, {2245, 5070}, {1005, 5070}, {0, 3770}};
        double[][] cargoIn = {{300, 300}, {4000, 300}, {3000, 3000}, {300, 3000}};

        PolygonShape bounds = new PolygonShape(boundsIn);
        PolygonShape cargo = new PolygonShape(cargoIn);

        Polygon boundsPoly = bounds.getPoly();
        Polygon cargoPoly = cargo.getPoly();

        if (boundsPoly.contains(cargoPoly)) {
            System.out.println("Объект входит в габарит");
        } else {
            System.err.println("Объект НЕ входит в габарит");
        }

        System.out.println(Arrays.deepToString(gapPoints(cargo.getCoords(), boundsPoly)));

        MainFrame frame = new MainFrame(boundsPoly, cargoPoly);
        frame.setVisible(true);

    }

}