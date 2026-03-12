package com.cargo.data;

public enum DegreesH {

    ZERO(0, 1650,5300),
    FIRST(1, 1700, 4000),
    SECOND(2, 1800, 4000),
    THIRD(3, 1850, 4000),
    FOURTH(4, 2000, 3700),
    FIFTH(5, 2080, 3400),
    SIXTH(6, 2240, 2800),
    OUT(7,0,0);

    final int code;
    final double size;
    final double height;

    DegreesH(int code, double size, double height) {
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

    public static DegreesH fromCode(int code) {
        for (DegreesH d : values()) {
            if (d.code == code) {
                return d;
            }
        }
        throw new IllegalArgumentException("Неизвестный код степени: " + code);
    }

}


