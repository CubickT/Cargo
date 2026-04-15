package com.cargo.model;

import com.cargo.IO.CalculationParameters;

public record Result (
        ShapeModel cargo,
        CalculationParameters params,
        int[] maxDegrees,
        int possibleMode,
        double[] minPossibleOuter,
        double[] minPossibleInner,
        double[] minPossibleBottom,
        double[] minPossibleTop,
        boolean isPossible
) {}