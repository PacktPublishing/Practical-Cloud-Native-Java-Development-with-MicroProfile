package com.packt.microprofile.book.ch4.entityandparamproviders;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.stream.Collectors;

import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;

@Provider
@Consumes(MediaType.APPLICATION_JSON)
public class MyJsonReader implements MessageBodyReader<Person> {

    @Override
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return type.equals(Person.class) && mediaType.isCompatible(MediaType.APPLICATION_JSON_TYPE);
    }

    @Override
    public Person readFrom(Class<Person> type, Type genericType, Annotation[] annotations, MediaType mediaType,
            MultivaluedMap<String, String> httpHeaders, InputStream entityStream)
            throws IOException, WebApplicationException {
        String s = new BufferedReader(new InputStreamReader(entityStream)).lines().collect(Collectors.joining(" ")).trim();
        if (!s.startsWith("{") || !s.endsWith("}")) {
            throw new WebApplicationException(Response.status(400).build());
        }
        s = s.substring(1, s.length() - 1);
        Person p = new Person();
        for (String fields : s.split(",")) {
            fields = fields.trim();
            if (fields.startsWith("\"firstName\"")) {
                p.setFirstName(fields.substring(fields.indexOf("\"", "\"firstName\"".length()) + 1, fields.lastIndexOf("\"")));
            } else if (fields.startsWith("\"lastName\"")) {
                p.setLastName(fields.substring(fields.indexOf("\"", "\"lastName\"".length()) + 1, fields.lastIndexOf("\"")));
            } else if (fields.startsWith("\"age\"")) {
                p.setAge(Integer.parseInt(fields.substring(fields.indexOf(":")+1).trim()));
            } else if (fields.startsWith("\"favoriteColor\"")) {
                p.setFavoriteColor(Color.valueOf(
                        fields.substring(fields.indexOf("\"", "\"favoriteColor\"".length()) + 1, fields.lastIndexOf("\""))));
            }
        }
        entityStream.close();
        return p;
    }
}
