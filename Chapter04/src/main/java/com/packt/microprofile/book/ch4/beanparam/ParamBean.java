package com.packt.microprofile.book.ch4.beanparam;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

public class ParamBean {
    private int id;

    @QueryParam("id")
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @HeaderParam("X-SomeHeader")
    public String someHeaderValue;

    @PathParam("path")
    public String pathParamValue;

    @Override
    public String toString() {
        return "ID: " + id + " X-SomeHeader: " + someHeaderValue + " path: " + pathParamValue;
    }
}
