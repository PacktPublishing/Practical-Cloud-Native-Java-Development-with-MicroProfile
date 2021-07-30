package com.packt.microprofile.book.ch4.entityandparamproviders;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class MyJsonWriter implements MessageBodyWriter<Person> {

    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return type.equals(Person.class) && mediaType.isCompatible(MediaType.APPLICATION_JSON_TYPE);
    }

    @Override
    public void writeTo(Person p, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType,
            MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream)
            throws IOException, WebApplicationException {
        PrintStream ps = new PrintStream(entityStream);
        ps.println("{");
        ps.println(" \"firstName\": \"" + p.getFirstName() + "\",");
        ps.println(" \"lastName\": \"" + p.getLastName() + "\",");
        ps.println(" \"age\": "  + p.getAge() + ",");
        ps.println(" \"favoriteColor\":\"" + p.getFavoriteColor() + "\"");
        ps.println("}");
        ps.close();
    }
}