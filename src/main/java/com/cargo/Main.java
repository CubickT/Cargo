package com.cargo;

import com.cargo.model.ShapeModel;
import com.cargo.model.ZoneModel;
import com.cargo.ui.MainFrame;
import org.locationtech.jts.geom.*;
import org.locationtech.jts.geom.Polygon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import static com.cargo.util.GeometryUtils.*;


public class Main {
    @SuppressWarnings("unused")
    static void main(String[] args) {

        GeometryFactory factory = new GeometryFactory();
        System.out.println("JTS работает! Фабрика создана: " + factory);

        ArrayList<ArrayList<Coordinate[]>> zoneList = new ArrayList<>(2);
        ArrayList<Coordinate[]> bottomZone = bottomZoneListCreation();
        ArrayList<Coordinate[]> sideZone = sideZoneListCreation();

        zoneList.add(bottomZone);
        zoneList.add(sideZone);

        ZoneModel[] zones = arrayZoneCreation(zoneList);
        System.out.println(Arrays.toString(zones));

        double[][] cargoIn = picker();
        double[][] boundsIn = {{0, 150}, {1300, 150}, {1300, 380}, {1625, 380}, {1625, 4000}, {620, 5300}, {-620, 5300}, {-1625, 4000}, {-1625, 380}, {-1300, 380}, {-1300, 150}, {0, 150}};

        ShapeModel bounds = new ShapeModel(boundsIn);
        ShapeModel cargo = new ShapeModel(cargoIn);

        Polygon boundsPoly = bounds.getPoly();
        Polygon cargoPoly = cargo.getPoly();
        Coordinate[][] gapCoords = gapCoords(cargo.getCoords(), boundsPoly);

        String info = "";

        if (boundsPoly.contains(cargoPoly)) {
            info += "Объект входит в габарит";
        } else {
            info += "Объект не входит в габарит";
        }

        System.out.println(Arrays.deepToString(gapCoords));

        MainFrame frame = new MainFrame(boundsPoly, cargoPoly, gapCoords,zones, info);
        frame.setVisible(true);

    }

    public static double[][] picker() {
        Scanner scanner = new Scanner(System.in);
        double[][] cargoIn;
        int input;

        System.out.println("Выберите груз");
        System.out.println("1 - Габаритный прямоугольник");
        System.out.println("2 - Негабаритный многоугольник");

        while (true) {

            if (scanner.hasNextInt()) {
                input = scanner.nextInt();

                switch (input) {
                    case 1:
                        cargoIn = new double[][]{{300, 300}, {300, 3000}, {-300, 3000}, {-300, 300}};
                        break;
                    case 2:
                        cargoIn = new double[][]{{0, 1000}, {1800, 1500}, {1800, 3000}, {500, 3000}, {500, 4000}, {-500, 4000}, {-500, 3000}, {-1800, 3000}, {-1800, 1500}, {0, 1000}};
                        break;

                    default:
                        System.out.println("Неверный номер. Введите число от 1 до 2");
                        continue;
                }

                break;
            } else {
                System.out.println("Ошибка: введите целое число.");
                scanner.next();
            }
        }

        scanner.close();
        return cargoIn;

    }

    public static ArrayList<Coordinate[]> bottomZoneListCreation() {
        ArrayList<Coordinate[]> bottomZone = new ArrayList<>(6);

        double x0 = 1625;
        double x1 = 1700;
        double x2 = 1760;
        double x3 = 1850;
        double x4 = 2000;
        double x5 = 2080;
        double x6 = 2240;

        double y0 = 480;
        double y1 = 1230;
        double y2 = 1399;



        Coordinate[] bottom1 = {
                new Coordinate(x0, y0),
                new Coordinate(x1, y0),
                new Coordinate(x1, y2),
                new Coordinate(x0, y2),
                new Coordinate(x0, y0)
        };

        Coordinate[] bottom2 = {
                new Coordinate(x1, y0),
                new Coordinate(x2, y0),
                new Coordinate(x2, y2),
                new Coordinate(x1, y2),
                new Coordinate(x1, y0)
        };

        Coordinate[] bottom3 = {
                new Coordinate(x2, y1),
                new Coordinate(x3, y1),
                new Coordinate(x3, y2),
                new Coordinate(x2, y2),
                new Coordinate(x2, y1)
        };

        Coordinate[] bottom4 = {
                new Coordinate(x3, y1),
                new Coordinate(x4, y1),
                new Coordinate(x4, y2),
                new Coordinate(x3, y2),
                new Coordinate(x3, y1)
        };

        Coordinate[] bottom5 = {
                new Coordinate(x4, y1),
                new Coordinate(x5, y1),
                new Coordinate(x5, y2),
                new Coordinate(x4, y2),
                new Coordinate(x4, y1)
        };

        Coordinate[] bottom6 = {
                new Coordinate(x5, y1),
                new Coordinate(x6, y1),
                new Coordinate(x6, y2),
                new Coordinate(x5, y2),
                new Coordinate(x5, y1)
        };


        bottomZone.add(bottom1);
        bottomZone.add(bottom2);
        bottomZone.add(bottom3);
        bottomZone.add(bottom4);
        bottomZone.add(bottom5);
        bottomZone.add(bottom6);

        return bottomZone;
    }

    public static ArrayList<Coordinate[]> sideZoneListCreation() {
        ArrayList<Coordinate[]> sideZone = new ArrayList<>(6);

        Coordinate[] side1 = {
                new Coordinate(1625, 1400),
                new Coordinate(1700, 1400),
                new Coordinate(1700, 4000),
                new Coordinate(880, 5300),
                new Coordinate(620, 5300),
                new Coordinate(1625, 4000),
                new Coordinate(1625, 1400)
        };
        sideZone.add(side1);
        return sideZone;
    }


}