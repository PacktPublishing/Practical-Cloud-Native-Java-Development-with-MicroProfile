package com.packt.microprofile.book.ch4.cdi;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class SomeOtherBean {

    @Produces
    public MyProducedDependency produceDependency() {
        return new MyProducedDependency((int) (Math.random() * 10));
    }
}
