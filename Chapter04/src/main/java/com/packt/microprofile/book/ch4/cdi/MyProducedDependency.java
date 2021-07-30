package com.packt.microprofile.book.ch4.cdi;

public class MyProducedDependency {

    private final int randomNumber;

    MyProducedDependency(int randomNumber) {
        this.randomNumber = randomNumber;
    }

    public int getRandomNumber() {
        return randomNumber;
    }
}
