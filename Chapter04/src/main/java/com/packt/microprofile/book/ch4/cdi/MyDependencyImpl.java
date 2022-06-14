package com.packt.microprofile.book.ch4.cdi;

import java.util.concurrent.atomic.AtomicInteger;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class MyDependencyImpl implements MyDependency {
    private static AtomicInteger INSTANCE_COUNTER = new AtomicInteger();
    private final int instanceId = INSTANCE_COUNTER.getAndIncrement();

    @Override
    public int getInstanceId() {
        System.out.println("com.packt.microprofile.book.ch4.cdi.MyDependencyImpl.getInstanceId() [return instanceId=" + instanceId + "=]");

        return instanceId;
    }
}
