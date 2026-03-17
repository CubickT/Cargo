package com.cargo.model;

public interface ResultInterface {

    void setPossibleMode(int possibleMode);
    void setMinPossibleOuter(double[] minPossibleOuter);
    void setMinPossibleInner(double[] minPossibleInner);
    void setIsPossible(boolean isPossible);

    int getPossibleMode();
    double[] getMinPossibleOuter();
    double[] getMinPossibleInner();
    boolean getIsPossible();


}
