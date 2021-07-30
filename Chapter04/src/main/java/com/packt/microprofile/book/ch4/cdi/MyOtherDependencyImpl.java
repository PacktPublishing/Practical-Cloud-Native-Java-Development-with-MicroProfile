package com.packt.microprofile.book.ch4.cdi;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@ApplicationScoped
@Named("max")
public class MyOtherDependencyImpl implements MyDependency {

    @Override
    public int getInstanceId() {
        return Integer.MAX_VALUE;
    }
}
