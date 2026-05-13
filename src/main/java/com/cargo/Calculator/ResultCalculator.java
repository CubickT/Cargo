package com.cargo.Calculator;

import com.cargo.IO.CalculationParameters;
import com.cargo.data.DegreesLower;
import com.cargo.data.DegreesSide;
import com.cargo.data.DegreesTop;
import com.cargo.data.GapTableRow;
import com.cargo.model.Result;
import com.cargo.model.ShapeModel;

import java.util.Arrays;
import java.util.Map;

import static com.cargo.util.Utils.*;

public class ResultCalculator {

    public static Result finalCalculation(ShapeModel cargo, ShapeModel bounds, int[] maxDegree, CalculationParameters params, Map<String, GapTableRow> gapTable) {

        DegreesSide degreeH = DegreesSide.fromCode(maxDegree[0]);
        DegreesTop degreeT = DegreesTop.fromCode(maxDegree[1]);
        DegreesLower degreeB = DegreesLower.fromCode(maxDegree[2]);

        double radius = params.radius();
        double elevationOuter = params.elevationOuter();

        double innerDist = params.inner();
        double outerDist = params.outer();

        System.out.println("Внутреннее Заданное - " + innerDist);
        System.out.println("Наружнее Заданное - " + outerDist);
        System.out.println("Заданный радиус - " + radius);
        System.out.println("Заданное возвышение рельса - " + elevationOuter);

        double widthH = degreeH.getSize();
        double heightH = degreeH.getHeight();
        int heightIndexH = calculateIndexHorizontal(heightH);

        double widthT = degreeT.getSize();
        double heightT = degreeT.getHeight();
        int heightIndexT = calculateIndexVertical(widthT);
//        System.out.println("ШИРИНА ВВЕРХНЕЙ ЗОНЫ + " + widthT);
//        System.out.println("ИНДЕКС ВВЕРХ + " + heightIndexT);

        double widthB = degreeB.getSize();
        double heightB = degreeB.getHeight();
        int heightIndexB = calculateIndexVertical(widthB);
//        System.out.println("ШИРИНА НИЖНЕЙ ЗОНЫ + " + widthB);
//        System.out.println("ИНДЕКС НИЗ + " + heightIndexB);

        String keyH = "byDegree|" + heightIndexH;
        GapTableRow tableRowH = gapTable.get(keyH);

        String keyT = "byDegreeVertical|" + heightIndexT;
        GapTableRow tableRowT = gapTable.get(keyT);

        String keyB = "byDegreeVertical|" + heightIndexB;
        GapTableRow tableRowB = gapTable.get(keyB);

        System.out.println("Степень - Н" + maxDegree[1]  + maxDegree[0] + maxDegree[2]);
//        System.out.println("Полуширина - " + widthH);  //2240
//        System.out.println("Высота - " + heightH);     //2800
//        System.out.println("Индекс высоты - " + heightIndexH);

        double[] innerDX = tableRowH.getInnerDX();
        double[] outerDX = tableRowH.getOuterDX();
        double[] DXTop = tableRowT.getTopDX();
        double[] DXBottom = tableRowB.getBottomDX();

//        System.out.println("Верхние зазоры - " + Arrays.toString(DXTop));
//        System.out.println("Нижние зазоры - " + Arrays.toString(DXBottom));

        Offsets offsets = calculateOffsets(radius, heightH, elevationOuter);

        System.out.println("Внутреннее доп. смещение X - " + offsets.innerXOffset);
        System.out.println("Наружнее доп. смещение X - " + offsets.outerXOffset);


        double[] minInner = calculateMinDist(widthH, innerDX, offsets.innerXOffset);
        double[] minOuter = calculateMinDist(widthH, outerDX, offsets.outerXOffset);
        double[] minBottom = calculateMinDisNoOffset(heightB, negateArray(DXBottom));
        double[] minTop = calculateMinDisNoOffset(heightT,DXTop);

        System.out.println("Верхние расстояния - " + Arrays.toString(minTop));

        System.out.println("Внутренние расстояния - " + Arrays.toString(minInner));
        System.out.println("Наружные расстояния - " + Arrays.toString(minOuter));

        System.out.println("Нижние расстояния - " + Arrays.toString(minBottom));

        int possibleMode = calculatePossibleMode(minInner, minOuter, innerDist, outerDist);
        boolean isPossible = possibleMode > 0;
        System.out.println("Допустимый режим хода - " + possibleMode);

        int[] maxDegrees = {maxDegree[1], maxDegree[0], maxDegree[2]};
        return new Result(cargo, bounds, params, maxDegrees, possibleMode,minOuter,minInner,minBottom,minTop,isPossible);

    }

    private static Offsets calculateOffsets(double radius, double height, double elevationOuter) {

        double wagonOutage;

        if (radius != 0) {
            wagonOutage = 36000 / radius;
        } else {
            wagonOutage = 0;
        }

        double innerXOffset = wagonOutage + height * (elevationOuter / 1600);
        double outerXOffset = wagonOutage - height * (elevationOuter / 1600);

        return new Offsets(innerXOffset, outerXOffset);
    }

    private static double[] calculateMinDist(double width, double[] DX, double offset) {
        double[] minDist = new double[DX.length];
        for (int i = 0; i < DX.length; i++) {
            minDist[i] = width + DX[i] + offset;
        }
        return minDist;
    }

    private static double[] calculateMinDisNoOffset(double height, double[] DX) {
        double[] minDist = new double[DX.length];
        for (int i = 0; i < DX.length; i++) {
            minDist[i] = height + DX[i];
        }
        return minDist;
    }

    private static int calculatePossibleMode(double[] minInner, double[] minOuter, double innerDist, double outerDist) {

        for (int i = 0; i < minInner.length ; i++) {

            if (minOuter[i] < outerDist && minInner[i] < innerDist) {
                return i + 1;
            }

        }
        return 0;
    }

    private static class Offsets {
        double innerXOffset;
        double outerXOffset;
        double topYOffset;
        double bottomYOffset;

        Offsets(double innerXOffset, double outerXOffset) {
            this.innerXOffset = innerXOffset;
            this.outerXOffset = outerXOffset;
        }
    }
}
