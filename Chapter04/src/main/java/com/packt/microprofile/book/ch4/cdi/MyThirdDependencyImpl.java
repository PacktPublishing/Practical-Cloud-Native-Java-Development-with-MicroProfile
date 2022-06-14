package com.packt.microprofile.book.ch4.cdi;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@Minimal
public class MyThirdDependencyImpl implements MyDependency {

    @Override
    public int getInstanceId() {
        System.out.println("com.packt.microprofile.book.ch4.cdi.MyThirdDependencyImpl.getInstanceId() [return Integer.MIN_VALUE=" + Integer.MIN_VALUE + "=]");

        return Integer.MIN_VALUE;
    }
}