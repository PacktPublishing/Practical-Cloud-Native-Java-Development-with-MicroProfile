package com.packt.microprofile.book.ch4.cdi;

public class MyProducedDependency {

    private final int randomNumber;

    MyProducedDependency(int randomNumber) {
        System.out.println("CONSTRUCTOR: com.packt.microprofile.book.ch4.cdi.MyProducedDependency.MyProducedDependency(randomNumber=" + randomNumber + "=)");

        this.randomNumber = randomNumber;
    }

    public int getRandomNumber() {
        System.out.println("com.packt.microprofile.book.ch4.cdi.MyProducedDependency.getRandomNumber() [return randomNumber=" + randomNumber + "=]");

        return randomNumber;
    }
}
