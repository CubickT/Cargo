package com.cargo.data;

public enum DegreesB {

    ZERO(0, 1650,5300),
    FIRST(1, 1700, 5300),
    SECOND(2, 1800, 5300),
    THIRD(3, 1850, 5300),
    OUT(4,0,0);

    final int code;
    final double size;
    final double height;

    DegreesB(int code, double size, double height) {
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

    public static DegreesB fromCode(int code) {
        for (DegreesB d : values()) {
            if (d.code == code) {
                return d;
            }
        }
        throw new IllegalArgumentException("Неизвестный код степени: " + code);
    }
}
