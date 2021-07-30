package com.packt.microprofile.book.ch4.entityandparamproviders;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/person")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PersonService {

    static List<Person> people = new ArrayList<>();

    @PostConstruct
    public void init() {
        Person p = new Person();
        p.setFirstName("John");
        p.setLastName("Doe");
        p.setAge(33);
        p.setFavoriteColor(Color.RED);
        people.add(p);
    }

    @GET
    @Path("/{id}")
    public Person getPerson(@PathParam("id") int id) {
        try {
            return people.get(id);
        } catch (IndexOutOfBoundsException ex) {
            throw new WebApplicationException(Response.status(404).entity("ID " + id + " not found.").build());
        }
    }

    @POST
    public int postPerson(Person person) {
        people.add(person);
        return people.lastIndexOf(person);
    }

    @PATCH
    @Path("/{id}")
    public Person updateFavoriteColor(@PathParam("id") int id, @QueryParam("color") Color color) {
        Person p = getPerson(id);
        p.setFavoriteColor(color);
        return p;
    }
}
