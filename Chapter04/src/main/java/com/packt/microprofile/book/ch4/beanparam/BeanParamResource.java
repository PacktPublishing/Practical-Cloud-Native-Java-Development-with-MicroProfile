package com.packt.microprofile.book.ch4.beanparam;

import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/beanparam/{path}")
@Produces(MediaType.TEXT_PLAIN)
public class BeanParamResource {

    @GET
    public Response get(@BeanParam ParamBean params) {
        System.out.println("id = " + params.getId());
        System.out.println("X-SomeHeader = " + params.someHeaderValue);
        System.out.println("path = " + params.pathParamValue);
        return Response.ok(params.toString()).build();
    }
}
