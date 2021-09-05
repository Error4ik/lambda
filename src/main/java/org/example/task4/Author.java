package org.example.task4;

import java.util.ArrayList;
import java.util.List;

public class Author {

    private String name;
    private short age;
    private List<Book> bookList;

    public Author() {
        this.bookList = new ArrayList<>();
    }

    public Author(String name, short age) {
        this.name = name;
        this.age = age;
        this.bookList = new ArrayList<>();
    }

    public void addBook(Book book) {
        this.bookList.add(book);
        book.getAuthorList().add(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public short getAge() {
        return age;
    }

    public void setAge(short age) {
        this.age = age;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    @Override
    public String toString() {
        return String.format("Author {name = %s, age = %d}", name, age);
    }
}
