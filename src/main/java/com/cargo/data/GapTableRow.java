package com.cargo.data;

import java.util.List;

public class GapTableRow {
    private String type;
    private int heightIndex;
    private List<Integer> innerDX;
    private List<Integer> outerDX;

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

    public List<Integer> getInnerDX() {
        return innerDX;
    }

    public void setInnerDX(List<Integer> innerDX) {
        this.innerDX = innerDX;
    }

    public List<Integer> getOuterDX() {
        return outerDX;
    }

    public void setOuterDX(List<Integer> outerDX) {
        this.outerDX = outerDX;
    }

}
