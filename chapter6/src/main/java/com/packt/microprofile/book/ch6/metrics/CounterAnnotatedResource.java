package com.packt.microprofile.book.ch6.metrics;

import org.eclipse.microprofile.metrics.annotation.Counted;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@ApplicationScoped
@Path("/counterAnnotatedResource")
@Counted
public class CounterAnnotatedResource {
    
  @GET
  @Path("/getResource")
  public String getResource() {
      return "Counting the class";
  }
}

