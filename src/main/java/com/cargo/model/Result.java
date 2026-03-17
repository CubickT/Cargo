package com.cargo.model;

public class Result implements ResultInterface {

    int possibleMode;
    double[] minPossibleOuter;
    double[] minPossibleInner;
    boolean isPossible;

    @Override
    public void setPossibleMode(int possibleMode) {
        this.possibleMode = possibleMode;
    }

    @Override
    public void setMinPossibleOuter(double[] minPossibleOuter) {
        this.minPossibleOuter = minPossibleOuter;
    }

    @Override
    public void setMinPossibleInner(double[] minPossibleInner) {
        this.minPossibleInner = minPossibleInner;
    }

    @Override
    public void setIsPossible(boolean isPossible) {
        this.isPossible = isPossible;
    }

    @Override
    public int getPossibleMode() {
        return possibleMode;
    }

    @Override
    public double[] getMinPossibleOuter() {
        return minPossibleOuter;
    }

    @Override
    public double[] getMinPossibleInner() {
        return minPossibleInner;
    }

    @Override
    public boolean getIsPossible() {
        return isPossible;
    }
}
