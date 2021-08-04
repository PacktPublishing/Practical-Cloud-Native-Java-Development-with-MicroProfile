package com.demo.rest;

import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.config.spi.ConfigSource;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@RequestScoped
@Path("/")
public class Resource {
    @Inject
    @ConfigProperty(name = "my_secret")
    private String secret;

    @GET
    @Path("secret")
    public String get() {
        return secret;
    }
}