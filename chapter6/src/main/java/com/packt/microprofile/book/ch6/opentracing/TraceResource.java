package com.packt.microprofile.book.ch6.opentracing;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.eclipse.microprofile.opentracing.Traced;

import io.opentracing.Tracer;

/**
 * 
 * This class is purely for reference 
 * and is only used as a reference in 
 * the chapter. For a more functional
 * example you may interact with the
 * {@link OutBoundTraceResource} and
 * {@link InBoundTraceResource} classes
 *
 */
@ApplicationScoped
@Path("/traceResource")
public class TraceResource {
    
    //Inject Tracer from the OpenTracing API
    @Inject
    io.opentracing.Tracer tracer;

    @GET
    @Path("/automaticTracing")
    @Traced(value=false)
    public String doNotTraceMe(){
        return "Do NOT trace me!";
    }
    
    @Traced(operationName="traceMe")
    public void traceMe(){
       System.out.println("Trace me!");
    }

}
