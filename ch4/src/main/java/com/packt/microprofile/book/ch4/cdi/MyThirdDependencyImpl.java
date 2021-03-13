package com.packt.microprofile.book.ch4.cdi;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@Minimal
public class MyThirdDependencyImpl implements MyDependency {

    @Override
    public int getInstanceId() {
        return Integer.MIN_VALUE;
    }
}