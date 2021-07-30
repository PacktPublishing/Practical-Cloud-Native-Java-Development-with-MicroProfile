package com.packt.microprofile.book.ch4.dynamicbinding;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicInteger;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

@Logged
@Provider
public class LoggingFilter implements ContainerRequestFilter, ContainerResponseFilter {

    private static final AtomicInteger idCounter = new AtomicInteger();

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        int requestID = idCounter.incrementAndGet();
        requestContext.setProperty("request.id", requestID);
        String toLog = ">>> " + requestID + " " + requestContext.getRequest().getMethod() + " " + 
            requestContext.getUriInfo().getRequestUri() + " " + getAndReplaceEntity(requestContext);
        System.out.println(toLog);
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
            throws IOException {
        int requestID = (int) requestContext.getProperty("request.id");
        String toLog = "<<< " + requestID + " " + requestContext.getUriInfo().getRequestUri() + " " + 
            responseContext.getEntity();
        System.out.println(toLog);
    }

    private String getAndReplaceEntity(ContainerRequestContext requestContext) throws IOException {
        if (!requestContext.hasEntity())
            return null;
        byte[] b = new byte[1024];
        InputStream originalStream = requestContext.getEntityStream();
        int bytesRead = originalStream.read(b);
        StringBuilder sb = new StringBuilder();
        while (bytesRead >= 0) {
            sb.append(new String(b, 0, bytesRead));
            bytesRead = originalStream.read(b);
        }
        String entity = sb.toString();
        requestContext.setEntityStream(new ByteArrayInputStream(entity.getBytes()));
        return entity;
    }
}
