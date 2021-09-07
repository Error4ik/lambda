package org.example.lambda.task3;

@FunctionalInterface
public interface ThreeFunction<S, T, U, R> {

    R apply(S s, T t, U u);
}
