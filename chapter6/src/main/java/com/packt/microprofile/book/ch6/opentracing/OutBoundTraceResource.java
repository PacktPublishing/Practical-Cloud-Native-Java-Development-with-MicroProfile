package com.packt.microprofile.book.ch6.opentracing;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;



@ApplicationScoped
@Path("/outbound")
public class OutBoundTraceResource {
    
    @GET
    @Path("/tracing")
    public String tracing(){
        Client client = ClientBuilder.newClient();
        
        /*
         * Send a GET request to InboundTraceResource.
         * We'll not worry about the response.
         */
        client.target("http://localhost:9080/inbound/tracing").request().get();

        return "Sent outbound request";
    }


}
