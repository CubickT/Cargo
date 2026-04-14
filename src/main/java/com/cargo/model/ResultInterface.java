package com.cargo.model;

public interface ResultInterface {

    void setPossibleMode(int possibleMode);
    void setMinPossibleOuter(double[] minPossibleOuter);
    void setMinPossibleInner(double[] minPossibleInner);
    void setMinPossibleBottom(double[] minPossibleBottom);
    void setMinPossibleTop(double[] minPossibleTop);
    void setIsPossible(boolean isPossible);

    int getPossibleMode();
    double[] getMinPossibleOuter();
    double[] getMinPossibleInner();
    boolean getIsPossible();


}
