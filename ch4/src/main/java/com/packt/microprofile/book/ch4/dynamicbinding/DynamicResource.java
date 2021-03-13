package com.packt.microprofile.book.ch4.dynamicbinding;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/dynamic")
@Produces(MediaType.TEXT_PLAIN)
@Consumes(MediaType.TEXT_PLAIN)
public class DynamicResource {

    private static String message;

    @GET
    public String getMessage() {
        return message;
    }

    @POST
    @Logged
    public String postMessage(String message) {
        DynamicResource.message = message;
        return "Updated message to: " + message;
    }
}
