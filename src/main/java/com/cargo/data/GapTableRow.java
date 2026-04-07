package com.cargo.data;

public class GapTableRow {
    private String type;
    private int heightIndex;
    private double[] innerDX;
    private double[] outerDX;
    private double[] bottomDX;
    private double[] topDX;

    public GapTableRow() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getHeightIndex() {
        return heightIndex;
    }

    public void setHeightIndex(int heightIndex) {
        this.heightIndex = heightIndex;
    }

    public double[] getInnerDX() {
        return innerDX;
    }

    public void setInnerDX(double[] innerDX) {
        this.innerDX = innerDX;
    }

    public double[] getOuterDX() {
        return outerDX;
    }

    public void setOuterDX(double[] outerDX) {
        this.outerDX = outerDX;
    }

    public double[] getBottomDX() {
        return bottomDX;
    }

    public void setBottomDX(double[] bottomDX) {
        this.bottomDX = bottomDX;
    }

    public double[] getTopDX() {
        return topDX;
    }

    public void setTopDX(double[] topDX) {
        this.topDX = topDX;
    }
}
