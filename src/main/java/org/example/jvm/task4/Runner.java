package org.example.jvm.task4;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class Runner {

    public static void main(String[] args) {
        List<Animal> animals = new ArrayList<>();
        CustomClassLoader loader = new CustomClassLoader();

        Class<?> catClazz = loader.findClass("org.example.jvm.task4.Cat");
        Class<?> dogClazz = loader.findClass("org.example.jvm.task4.Dog");

        try {
            animals.add((Animal) catClazz.getDeclaredConstructor().newInstance());
            animals.add((Animal) dogClazz.getDeclaredConstructor().newInstance());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        for (Animal animal : animals) {
            animal.play();
            animal.voice();
        }
    }
}
