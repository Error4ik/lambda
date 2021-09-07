package org.example.lambda.task4;

import java.util.ArrayList;
import java.util.List;

public class Book {

    private String title;
    private int numberOfPage;
    private List<Author> authorList;

    public Book() {
        this.authorList = new ArrayList<>();
    }

    public Book(String title, int numberOfPage) {
        this.title = title;
        this.numberOfPage = numberOfPage;
        this.authorList = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Author> getAuthorList() {
        return authorList;
    }

    public void setAuthorList(List<Author> authorList) {
        this.authorList = authorList;
    }

    public int getNumberOfPage() {
        return numberOfPage;
    }

    public void setNumberOfPage(int numberOfPage) {
        this.numberOfPage = numberOfPage;
    }

    @Override
    public String toString() {
        return String.format("Book {title = %s, numberOfPage = %d}", title, numberOfPage);
    }
}
