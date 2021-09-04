package org.example.task2;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.function.BinaryOperator;

public class Runner {

    public static void main(String[] args) {
        List<Person> persons = new ArrayList<>();
        persons.add(new Person("Oleg", 19));
        persons.add(new Person("Ksenia", 29));
        persons.add(new Person("Anna", 31));
        persons.add(new Person("Irina", 18));
        persons.add(new Person("Sergey", 54));

        System.out.println("Consumer: Print all person.");
        Consumer<Person> consumer = p -> System.out.println(p);
        persons.forEach(consumer);
        System.out.println("------------------------------");

        System.out.println("Function: Converting int to double.");
        Function<Integer, Double> function = x -> Double.valueOf(x);
        persons.forEach(x -> System.out.println(function.apply(x.getAge())));
        System.out.println("------------------------------");

        System.out.println("Predicate: Remove person who age < 20.");
        Predicate<Person> predicate = p -> p.getAge() < 20;
        persons.removeIf(predicate);
        persons.forEach(System.out::println);
        System.out.println("------------------------------");

        System.out.println("Supplier: Return pseudorandom double.");
        Supplier<Double> supplier = () -> Math.random();
        System.out.println(supplier.get());
        System.out.println("------------------------------");

        System.out.println("UnaryOperator: Argument raised to the power of the 3.");
        UnaryOperator<Double> unaryOperator = x -> Math.pow(x, 3);
        System.out.println(unaryOperator.apply(5.0));
        System.out.println("------------------------------");

        System.out.println("BinaryOperator: First argument raised to the power of the second argument.");
        BinaryOperator<Double> binaryOperator = (x, y) -> Math.pow(x, y);
        System.out.println(binaryOperator.apply(3.0, 4.0));
        System.out.println("------------------------------");

        System.out.println("My functional interface MultiplyOperation: With anonymous class.");
        ArithmeticOperations arithmeticOperations1 = new ArithmeticOperations() {
            @Override
            public double calculate(double a, double b) {
                return a - b;
            }
        };
        System.out.println(arithmeticOperations1.calculate(8.0, 3.0));
        System.out.println("------------------------------");

        System.out.println("My functional interface MultiplyOperation: With lambda.");
        ArithmeticOperations arithmeticOperations2 = (a, b) -> a * b;
        System.out.println(arithmeticOperations2.calculate(9.0, 7.0));
        System.out.println("------------------------------");

        System.out.println("My functional interface MultiplyOperation: Sum and Subtract methods.");
        System.out.println(arithmeticOperations1.sum(12.6, 4.3));
        System.out.println(arithmeticOperations1.subtract(16.7, 12.3));
        System.out.println("------------------------------");

        System.out.println("My functional interface MultiplyOperation: Static method divide.");
        System.out.println(ArithmeticOperations.divide(18, 3));
        System.out.println("------------------------------");

        System.out.println("My functional interface MultiplyOperation: Static method multiply.");
        System.out.println(ArithmeticOperations.multiply(6, 6));
        System.out.println("------------------------------");
    }
}
