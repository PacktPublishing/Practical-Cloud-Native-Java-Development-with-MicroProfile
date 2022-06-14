package com.packt.microprofile.book.ch4.async;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;

import com.packt.microprofile.book.ch4.entityandparamproviders.Color;
import com.packt.microprofile.book.ch4.entityandparamproviders.Person;

@Path("/person")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AsyncPersonService {
    static Map<Integer, Person> dataStore = new HashMap<>();
    static ExecutorService executor = Executors.newFixedThreadPool(5);

    public AsyncPersonService() {
        dataStore.put(1, new Person("Richard", "Smith", 21, Color.ORANGE));
    }

    @GET
    @Path("/sync/{id}")
    public Person getPersonFromDBSync(@PathParam("id") int id) throws InterruptedException, ExecutionException {
        Future<Person> someData = executor.submit(() -> getPerson(id));
        return someData.get();
    }

    @GET
    @Path("/async/{id}")
    public void getPersonFromDBAsync(@PathParam("id") int id, @Suspended AsyncResponse ar) {
        executor.submit(() -> {
            Optional<Person> p = Optional.ofNullable(getPerson(id));
            if (p.isPresent())
                ar.resume(p.get());
            else ar.resume(new NoSuchPersonException());
        });
    }

    private Person getPerson(int id) {
        return dataStore.get(id);
    }
}
