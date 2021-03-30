package com.packt.microprofile.book.ch4.jsonb;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;

public class Person {

    private String firstName;
    @JsonbTransient
    private String middleName;
    @JsonbProperty("familyName")
    private String lastName;
    private String favoriteColor;
    private int age;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @JsonbProperty("favouriteColour")
    public String getFavoriteColor() {
        return favoriteColor;
    }

    public void setFavoriteColor(String favoriteColor) {
        this.favoriteColor = favoriteColor;
    }

    public int getAge() {
        return age;
    }

    @JsonbProperty("yearsOld")
    public void setAge(int age) {
        this.age = age;
    }
}
