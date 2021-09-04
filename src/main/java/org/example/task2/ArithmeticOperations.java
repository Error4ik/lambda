package org.example.task2;

@FunctionalInterface
public interface ArithmeticOperations {

    static double divide(double a, double b) {
        return a / b;
    }

    static double multiply(double a, double b) {
        return a * b;
    }

    double calculate(double a, double b);

    default double sum(double a, double b) {
        return a + b;
    }

    default double subtract(double a, double b) {
        return a - b;
    }
}
