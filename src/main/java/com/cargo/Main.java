package com.cargo;

import com.cargo.model.PolygonShape;
import com.cargo.ui.MainFrame;
import org.locationtech.jts.geom.*;

import java.util.Arrays;
import java.util.Scanner;

import static com.cargo.util.GeometryUtils.*;


public class Main {
    @SuppressWarnings("unused")
    static void main(String[] args) {

        GeometryFactory factory = new GeometryFactory();
        System.out.println("JTS работает! Фабрика создана: " + factory);

        double[][] cargoIn = picker();


        double[][] boundsIn = {{0, 150}, {1300, 150}, {1300, 380}, {1625, 380}, {1625, 4000}, {620, 5300}, {-620, 5300}, {-1625, 4000}, {-1625, 380}, {-1300, 380}, {-1300, 150}, {0, 150}};

        PolygonShape bounds = new PolygonShape(boundsIn);
        PolygonShape cargo = new PolygonShape(cargoIn);

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

        MainFrame frame = new MainFrame(boundsPoly, cargoPoly, gapCoords, info);
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

}