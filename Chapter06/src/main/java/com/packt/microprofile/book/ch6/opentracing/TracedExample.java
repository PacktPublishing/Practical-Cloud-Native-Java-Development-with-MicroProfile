package com.packt.microprofile.book.ch6.opentracing;

import javax.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.opentracing.Traced;

@ApplicationScoped
public class TracedExample {
    
    @Traced(operationName="epoch")
    public void epoch(){
        System.out.println("The current milliseconds since epoch is: " + System.currentTimeMillis());
     }
}
