package com.cargo.data;

public enum DegreesT {

    ZERO(0, 1650,5300),
    FIRST(1, 1700, 480),
    SECOND(2, 1800, 480),
    THIRD(3, 1850, 1230),
    FORTH(4, 2000,1230),
    FIFTH(5,2080,1230),
    SIXTH(6, 2240,1230),
    OUT(7,0,0);

    final int code;
    final double size;
    final double height;

    DegreesT(int code, double size, double height) {
        this.code = code;
        this.size = size;

        this.height = height;
    }

    public int getCode() {
        return code;
    }

    public double getSize() {
        return size;
    }

    public double getHeight() {
        return height;
    }

    public static DegreesT fromCode(int code) {
        for (DegreesT d : values()) {
            if (d.code == code) {
                return d;
            }
        }
        throw new IllegalArgumentException("Неизвестный код степени: " + code);
    }

}
