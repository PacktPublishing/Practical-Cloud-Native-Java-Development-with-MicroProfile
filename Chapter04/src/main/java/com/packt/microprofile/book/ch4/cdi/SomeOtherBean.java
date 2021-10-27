package com.packt.microprofile.book.ch4.cdi;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class SomeOtherBean {

    @Produces
    public MyProducedDependency produceDependency() {
        int intRandom = (int) (Math.random() * 10);

        System.out.println("com.packt.microprofile.book.ch4.cdi.SomeOtherBean.produceDependency() [return new MyProducedDependency(intRandom=" + intRandom + ");]");

        return new MyProducedDependency(intRandom);
    }
}
