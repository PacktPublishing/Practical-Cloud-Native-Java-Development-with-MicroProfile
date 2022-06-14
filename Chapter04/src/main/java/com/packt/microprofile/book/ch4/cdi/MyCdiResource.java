package com.packt.microprofile.book.ch4.cdi;

import com.packt.microprofile.book.ch4.client.ThesaurusClient;
import com.packt.microprofile.book.ch4.thesaurus.NoSuchWordException;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@ApplicationScoped
@Path("/cdi")
@Produces("text/plain")
public class MyCdiResource {
    @Inject
    @Minimal
    MyDependency dependency;

    @Inject
    MyProducedDependency producedDependency;

    @GET
    public int getDependencyInstanceId() {
        int result = dependency.getInstanceId();

        System.out.println("com.packt.microprofile.book.ch4.cdi.MyCdiResource.getDependencyInstanceId(): [dependency=" + dependency + "=] [result=" + result + "=]");

        return result;
    }

    @GET
    @Path("/produced")
    public int getProducedDependencyRandomNum() {
        int result = producedDependency.getRandomNumber();

        System.out.println("com.packt.microprofile.book.ch4.cdi.MyCdiResource.getProducedDependencyRandomNum(): [producedDependency=" + producedDependency + "=] [result=" + result + "=]");

        return result;
    }

    @Inject
    @RestClient
    ThesaurusClient thesaurusClient;

    @GET
    @Path("/thesaurus/{word}")
    public String lookup(@PathParam("word") String word) {
        String result = "Sorry, that word is not found.";

        try {
            result = thesaurusClient.getSynonymsFor(word);
        } catch (NoSuchWordException ex) {
            // ignore, take default response
        }

        System.out.println("com.packt.microprofile.book.ch4.cdi.MyCdiResource.lookup(word=" + word + "=): [thesaurusClient=" + thesaurusClient + "=] [result=" + result + "=]");

        return result;
    }
}
