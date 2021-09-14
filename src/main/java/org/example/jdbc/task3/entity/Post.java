package org.example.jdbc.task3.entity;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

public class Post {

    private UUID id;

    private UUID userId;

    private String text;

    private Timestamp dateOfCreation;

    public Post() {
    }

    public Post(UUID userId, String text) {
        this.userId = userId;
        this.text = text;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Timestamp getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(Timestamp dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(id, post.id) &&
                Objects.equals(userId, post.userId) &&
                Objects.equals(text, post.text) &&
                Objects.equals(dateOfCreation, post.dateOfCreation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, text, dateOfCreation);
    }

    @Override
    public String toString() {
        return String.format(
                "Post {id = %s, userId = %s, text = %s, dateOfCreation = %s}", id, userId, text, dateOfCreation);
    }
}
