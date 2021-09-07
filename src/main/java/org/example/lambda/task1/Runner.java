package org.example.lambda.task1;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Runner {
    public static void main(String[] args) {

        Comparator<Person> comparingPersonsByName = (o1, o2) -> o1.getName().compareTo(o2.getName());
        Comparator<Person> comparingPersonsByAge = (o1, o2) -> Integer.compare(o1.getAge(), o2.getAge());

        List<Person> persons = new ArrayList<>();
        persons.add(new Person("Oleg", 19));
        persons.add(new Person("Ksenia", 29));
        persons.add(new Person("Anna", 31));
        persons.add(new Person("Garry", 76));
        persons.add(new Person("Petya", 26));
        persons.add(new Person("Maria", 21));
        persons.add(new Person("Irina", 48));
        persons.add(new Person("Alex", 22));
        persons.add(new Person("Fedor", 19));
        persons.add(new Person("Mikhail", 28));

        System.out.println("Source list.");
        System.out.println("------------------------------");
        persons.forEach(System.out::println);
        System.out.println("------------------------------");

        System.out.println("Sorting by name.");
        System.out.println("------------------------------");
        persons.sort(comparingPersonsByName);
        persons.forEach(System.out::println);
        System.out.println("------------------------------");

        System.out.println("Sorting by age.");
        System.out.println("------------------------------");
        persons.sort(comparingPersonsByAge);
        persons.forEach(System.out::println);
        System.out.println("------------------------------");
    }
}
