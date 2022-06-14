package com.packt.microprofile.book.ch4.cdi;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@ApplicationScoped
@Named("max")
public class MyOtherDependencyImpl implements MyDependency {

    @Override
    public int getInstanceId() {
        System.out.println("com.packt.microprofile.book.ch4.cdi.MyOtherDependencyImpl.getInstanceId() [return Integer.MAX_VALUE=" + Integer.MAX_VALUE + "=]");

        return Integer.MAX_VALUE;
    }
}
