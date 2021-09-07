package org.example.lambda.task4;

import java.util.Comparator;
import java.util.List;

public class Runner {

    public static void main(String[] args) {
        PreparedData preparedData = new PreparedData();
        List<Author> listAuthor = preparedData.getAuthors();
        List<Book> listBook = preparedData.getBooks();

        System.out.println("Books with more than 200 pages.");
        listBook.stream()
                .filter(b -> b.getNumberOfPage() > 200)
                .forEach(System.out::println);
        System.out.println("--------------------------------------");

        System.out.println("Book with max pages.");
        Book bookMaxNumberOfPage = listBook.stream()
                .max(Comparator.comparing(Book::getNumberOfPage)).orElse(null);
        System.out.println(bookMaxNumberOfPage);
        System.out.println("--------------------------------------");

        System.out.println("Book with min pages.");
        Book bookMinNumberOfPage = listBook.stream()
                .min(Comparator.comparing(Book::getNumberOfPage)).orElse(null);
        System.out.println(bookMinNumberOfPage);
        System.out.println("--------------------------------------");

        System.out.println("Book with only one author.");
        listBook.stream()
                .filter(b -> b.getAuthorList().size() == 1)
                .forEach(System.out::println);
        System.out.println("--------------------------------------");

        System.out.println("Sort books by number of pages.");
        listBook.stream()
                .sorted(Comparator.comparingInt(Book::getNumberOfPage))
                .forEach(System.out::println);
        System.out.println("--------------------------------------");

        System.out.println("Sort books by title.");
        listBook.stream()
                .sorted(Comparator.comparing(Book::getTitle))
                .forEach(System.out::println);
        System.out.println("--------------------------------------");

        System.out.println("List of titles.");
        listBook.stream()
                .map(Book::getTitle)
                .forEach(System.out::println);
        System.out.println("--------------------------------------");

        System.out.println("List of authors.");
        listBook.stream()
                .flatMap(book -> book.getAuthorList().stream())
                .distinct()
                .forEach(System.out::println);
        System.out.println("--------------------------------------");

        System.out.println("Use the Optional type for determining the title of the biggest book of some author.");
        listBook.stream()
                .flatMap(book -> book.getAuthorList()
                        .stream()
                        .filter(author -> author.getName().equalsIgnoreCase("Автор 2")))
                .distinct()
                .map(author -> author.getBookList()
                        .stream()
                        .max(Comparator.comparing(Book::getNumberOfPage)))
                .forEach(System.out::println);
        System.out.println("--------------------------------------");
    }
}
