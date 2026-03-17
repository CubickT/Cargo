package com.cargo.Calculator;

import com.cargo.IO.CalculationParameters;
import com.cargo.data.GapTableRow;
import com.cargo.model.Result;
import com.cargo.model.ShapeModel;
import com.cargo.model.ZoneModel;
import org.locationtech.jts.geom.Coordinate;

import java.util.Map;

import static com.cargo.IO.CalculationParameters.ConsoleInput.read;
import static com.cargo.data.GapTableReader.createGapTable;
import static com.cargo.Calculator.ResultCalculator.finalCalculation;
import static com.cargo.util.GeometryUtils.calculateDegree;
import static com.cargo.util.InitializationUtils.zonesInitialization;
import static com.cargo.util.Utils.coordAbs;
import static com.cargo.util.Utils.maxInArray;

public class Calculator {

    static Map<String, GapTableRow> gapTable;
    static ZoneModel[] zones;

    public Calculator() {
    }

    public static Result calculator(ShapeModel cargo, ShapeModel bounds) {
        gapTable = createGapTable();
        zones = zonesInitialization();

        Coordinate[] cargoABS = coordAbs(cargo.getCoords());

        int[][] degrees = calculateDegree(cargoABS, zones, bounds);
        int[] maxDegree = maxInArray(degrees);

        CalculationParameters params = read();

        return finalCalculation(maxDegree, params, gapTable);

    }

}
