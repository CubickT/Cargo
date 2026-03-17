package com.cargo.IO;

import java.util.Scanner;

public record CalculationParameters(double radius, double elevationOuter, double inner,
                                    double outer) implements ParameterInput {

    public static class ConsoleInput {
        private static final Scanner SCANNER = new Scanner(System.in);

        public static CalculationParameters read() {
            System.out.println("Введите радиус кривой");
            double radius = readDouble();
            System.out.println("Введите возвышение наружного рельса");
            double elevationOuter = readDouble();
            System.out.println("Введите внутреннее расстояние до сооружения");
            double inner = readDouble();
            System.out.println("Введите наружнее расстояние до сооружения");
            double outer = readDouble();
            return new CalculationParameters(radius, elevationOuter, inner, outer);
        }

        private static double readDouble() {
            while (true) {
                if (SCANNER.hasNextDouble()) {
                    return SCANNER.nextDouble();
                } else {
                    System.out.println("Ошибка, введите число");
                    SCANNER.next(); // пропустить некорректный ввод
                }
            }
        }
    }
}