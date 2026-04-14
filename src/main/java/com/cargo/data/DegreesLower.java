package com.cargo.data;

public enum DegreesLower {

    ZERO(0, 1625,480),
    FIRST(1, 1700, 480),
    SECOND(2, 1800, 1230),
    THIRD(3, 1850, 1230),
    FORTH(4, 2000,1230),
    FIFTH(5,2080,1230),
    SIXTH(6, 2240,1230),
    OUT(4,0,0);

    final int code;
    final double size;
    final double height;

    DegreesLower(int code, double size, double height) {
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

    public static DegreesLower fromCode(int code) {
        for (DegreesLower d : values()) {
            if (d.code == code) {
                return d;
            }
        }
        throw new IllegalArgumentException("Неизвестный код степени: " + code);
    }
}
