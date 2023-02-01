package com.packt.microprofile.book.ch4.client;

import com.packt.microprofile.book.ch4.thesaurus.NoSuchWordException;
import com.packt.microprofile.book.ch4.thesaurus.WordAlreadyExistsException;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/{word}")
@RegisterProvider(NoSuchWordResponseMapper.class)
@RegisterRestClient(baseUri = "http://localhost:9080/ch4/rest/thesaurus")
public interface ThesaurusClient {
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    String getSynonymsFor(@PathParam("word") String word)
        throws NoSuchWordException;

    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    String setSynonymsFor(@PathParam("word") String word,
        String synonyms) throws WordAlreadyExistsException;

    @PUT
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    String updateSynonymsFor(@PathParam("word") String word,
        String synonyms) throws NoSuchWordException;

    @DELETE
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    boolean deleteSynonyms(@PathParam("word") String word)
        throws NoSuchWordException;

    @PATCH
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    String addNewSynonymsFor(@PathParam("word") String word,
        String newSynonyms) throws NoSuchWordException;
}
