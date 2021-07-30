package com.packt.microprofile.book.ch6.opentracing;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.eclipse.microprofile.opentracing.Traced;

import io.opentracing.Tracer;

@ApplicationScoped
@Path("/inbound")
public class InBoundTraceResource {

    @Inject
    TracedExample tracedExample;
    
    @GET
    @Path("/tracing")
    public String waiting() throws InterruptedException{
        Thread.sleep(2000);
        tracedExample.epoch();
        return "Recieved in bound request and waited 2 seconds!";
    }

}
