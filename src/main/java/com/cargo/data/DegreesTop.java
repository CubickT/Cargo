package com.cargo.data;

public enum DegreesTop {

    ZERO(0, 1625,5300),
    FIRST(1, 1700, 5300),
    SECOND(2, 1800, 5300),
    THIRD(3, 1850, 5300),
    OUT(7,0,0);

    final int code;
    final double size;
    final double height;

    DegreesTop(int code, double size, double height) {
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

    public static DegreesTop fromCode(int code) {
        for (DegreesTop d : values()) {
            if (d.code == code) {
                return d;
            }
        }
        throw new IllegalArgumentException("Неизвестный код степени: " + code);
    }

}
