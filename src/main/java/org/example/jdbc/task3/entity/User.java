package org.example.jdbc.task3.entity;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

public class User {

    private UUID id;

    private String name;

    private String surname;

    private Timestamp birthDay;

    public User() {
    }

    public User(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Timestamp getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Timestamp birthDay) {
        this.birthDay = birthDay;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(name, user.name) &&
                Objects.equals(surname, user.surname) &&
                Objects.equals(birthDay, user.birthDay);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, birthDay);
    }

    @Override
    public String toString() {
        return String.format(
                "User {id = %s, name = %s, surname = %s, birthDay = %s}", id, name, surname, birthDay);
    }
}
