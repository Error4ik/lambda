package org.example.lambda.task4;

import java.util.Arrays;
import java.util.List;

public class PreparedData {

    private final Author author1 = new Author("Автор 1", (short) 50);
    private final Author author2 = new Author("Автор 2", (short) 36);
    private final Author author3 = new Author("Автор 3", (short) 67);
    private final Author author4 = new Author("Автор 4", (short) 25);

    private final Book book1 = new Book("Война и Мир", 1387);
    private final Book book2 = new Book("Алиса в стране чудес", 214);
    private final Book book3 = new Book("Анна Каренина", 98);
    private final Book book4 = new Book("1984", 198);
    private final Book book5 = new Book("Мертвые души", 315);
    private final Book book6 = new Book("Двенадцать стульев", 165);
    private final Book book7 = new Book("Преступление и наказание", 834);
    private final Book book8 = new Book("Идиот", 967);
    private final Book book9 = new Book("Преступление и наказание", 60);
    private final Book book10 = new Book("Собачье сердце", 1540);

    private final List<Author> authors;
    private final List<Book> books;

    public PreparedData() {
        booksForAuthors();
        this.authors = Arrays.asList(author1, author2, author3, author4);
        this.books = Arrays.asList(book1, book2, book3, book4, book5, book6, book7, book8, book9, book10);
    }

    public void booksForAuthors() {
        author1.addBook(book1);
        author1.addBook(book3);
        author1.addBook(book10);
        author1.addBook(book8);

        author2.addBook(book1);
        author2.addBook(book6);
        author2.addBook(book5);

        author3.addBook(book5);
        author3.addBook(book9);
        author3.addBook(book4);

        author4.addBook(book2);
        author4.addBook(book7);
        author4.addBook(book10);
        author4.addBook(book8);
    }

    public List<Book> getBooks() {
        return this.books;
    }

    public List<Author> getAuthors() {
        return this.authors;
    }
}
