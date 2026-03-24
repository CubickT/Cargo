package com.cargo;

import com.cargo.model.Result;
import com.cargo.model.ShapeModel;
import com.cargo.model.ZoneModel;
import com.cargo.ui.MainFrame;
import com.cargo.data.GapTableRow;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.locationtech.jts.geom.*;
import org.locationtech.jts.geom.Polygon;

import java.util.Map;
import java.util.Scanner;

import static com.cargo.Calculator.Calculator.calculator;
import static com.cargo.data.GapTableReader.*;
import static com.cargo.util.GeometryUtils.*;
import static com.cargo.util.InitializationUtils.*;
import static com.cargo.util.Utils.*;


public class Main extends Application {
    @SuppressWarnings("unused")
    static void main(String[] args) {

        launch(args);

        Scanner scanner = new Scanner(System.in);
        GeometryFactory factory = new GeometryFactory();
        Map<String, GapTableRow> gapTable = createGapTable();

        ZoneModel[] zones = zonesInitialization();

        double[][] cargoIn = picker(scanner);
        Coordinate[] cargoCoords = toCoordinates(cargoIn);
        double[][] boundsIn = {{0, 150}, {1300, 150}, {1300, 380}, {1625, 380}, {1625, 4000}, {620, 5300}, {-620, 5300}, {-1625, 4000}, {-1625, 380}, {-1300, 380}, {-1300, 150}, {0, 150}};

        ShapeModel bounds = new ShapeModel("BOUNDS", ShapeModel.ObjectType.BOUNDS, boundsIn);
        ShapeModel cargo = new ShapeModel("CARGO", ShapeModel.ObjectType.CARGO, cargoIn);

        Polygon boundsPoly = bounds.getPoly();
        Polygon cargoPoly = cargo.getPoly();
        Coordinate[][] gapCoords = gapCoords(cargo.getCoords(), boundsPoly);

        String info = "";

        if (boundsPoly.contains(cargoPoly)) {
            info += "Объект входит в габарит";
        } else {
            info += "Объект не входит в габарит. Степень - ";
        }

        Result result = calculator(cargo,bounds);


        MainFrame frame = new MainFrame(boundsPoly, cargoPoly, gapCoords, zones, info);
        frame.setVisible(true);

    }

    public static double[][] picker(Scanner scanner) {
        double[][] cargoIn;
        int input;

        System.out.println("Выберите груз");
        System.out.println("1 - Габаритный прямоугольник");
        System.out.println("2 - Негабаритный многоугольник");
        System.out.println("3 - Груз из задачи");
        System.out.println("4 - Проверка производительности");

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
                    case 3:
                        cargoIn = new double[][]{{-2100, 1500}, {2100, 1500}, {2100, 2500}, {-2100, 2500}};
                        break;
                    case 4:
                        cargoIn = generateCirclePoints(2000000, 1800, 3000);
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
        return cargoIn;

    }


    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/App.fxml"));

        Scene scene = new Scene(root);

        stage.setTitle("Моё приложение");
        stage.setScene(scene);
        stage.show();


    }
}