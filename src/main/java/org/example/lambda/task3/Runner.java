package org.example.lambda.task3;

public class Runner {

    public static void main(String[] args) {
        ThreeFunction<Integer, Double, Long, String> threeFunction = (x, y, z) -> String.valueOf(x * y * z);

        System.out.println(threeFunction.apply(10, 52.3, 9L));
    }
}
