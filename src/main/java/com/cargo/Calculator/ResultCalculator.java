package com.cargo.Calculator;

import com.cargo.IO.CalculationParameters;
import com.cargo.data.DegreesH;
import com.cargo.data.GapTableRow;
import com.cargo.model.Result;

import java.util.Arrays;
import java.util.Map;

import static com.cargo.util.Utils.calculateHeightIndex;

public class ResultCalculator {

    public static Result finalCalculation(int[] maxDegree, CalculationParameters params, Map<String, GapTableRow> gapTable) {

        DegreesH degreeH = DegreesH.fromCode(maxDegree[0]);
        int code = degreeH.getCode();

        if (code == 0) {
            System.out.println("Груз габаритен");
        } else if (code == 7) {
            System.out.println("Груз абсолютно негабаритен");
        }

        double width = degreeH.getSize();
        double height = degreeH.getHeight();
        int heighIndex = calculateHeightIndex(height);

        String key = "byDegree|" + heighIndex;
        GapTableRow tableRow = gapTable.get(key);

        System.out.println("Степень - " + code);
        System.out.println("Полуширина - " + width);  //2240
        System.out.println("Высота - " + height);     //2800
        System.out.println("Индекс высоты - " + heighIndex);


        double radius = params.radius();
        double elevationOuter = params.elevationOuter();

        double innerDist = params.inner();
        double outerDist = params.outer();

        double[] innerDX = tableRow.getInnerDX();
        double[] outerDX = tableRow.getOuterDX();

        Offsets offsets = calculateOffsets(radius, height, elevationOuter);

        System.out.println("Внутреннее доп. смещение X - " + offsets.innerXOffset);
        System.out.println("Наружнее доп. смещение X - " + offsets.outerXOffset);


        double[] minInner = calculateMinDist(width, innerDX, offsets.innerXOffset);
        double[] minOuter = calculateMinDist(width, outerDX, offsets.outerXOffset);

        System.out.println("Внутренние " + Arrays.toString(minInner));
        System.out.println("Наружные " + Arrays.toString(minOuter));
        System.out.println("Внутреннее " + innerDist);
        System.out.println("Наружнее " + outerDist);

        int possibleMode = calculatePossibleMode(minInner, minOuter, innerDist, outerDist);
        boolean isPossible = possibleMode > 0;
        System.out.println("Допустимый режим хода - " + possibleMode);

        Result result = new Result();
        result.setMinPossibleInner(minInner);
        result.setMinPossibleOuter(minOuter);
        result.setPossibleMode(possibleMode);
        result.setIsPossible(isPossible);

        return result;

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

        Offsets(double innerXOffset, double outerXOffset) {
            this.innerXOffset = innerXOffset;
            this.outerXOffset = outerXOffset;
        }
    }
}
