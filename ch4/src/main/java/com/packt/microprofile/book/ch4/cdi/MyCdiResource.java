package com.packt.microprofile.book.ch4.cdi;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.packt.microprofile.book.ch4.client.ThesaurusClient;
import com.packt.microprofile.book.ch4.thesaurus.NoSuchWordException;

import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
@Path("/cdi")
public class MyCdiResource {

    @Inject
    @Minimal
    MyDependency dependency;

    @Inject
    MyProducedDependency producedDependency;

    @GET
    public int getDependencyInstanceId() {
        System.out.println(dependency);
        return dependency.getInstanceId();
    }

    @GET
    @Path("/produced")
    public int getProducedDependencyRandomNum() {
        System.out.println(producedDependency);
        return producedDependency.getRandomNumber();
    }

    @Inject
    @RestClient
    ThesaurusClient thesaurusClient;

    @GET
    @Path("/thesaurus/{word}")
    public String lookup(@PathParam("word") String word) {
        try {
            return thesaurusClient.getSynonymsFor(word);
        } catch (NoSuchWordException ex) {
            return "Sorry, that word is not found.";
        }
    }
}
