package com.packt.microprofile.book.ch4.client;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.packt.microprofile.book.ch4.thesaurus.NoSuchWordException;
import com.packt.microprofile.book.ch4.thesaurus.WordAlreadyExistsException;

import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/{word}")
@RegisterProvider(NoSuchWordResponseMapper.class)
@RegisterRestClient(baseUri = "http://localhost:9080/ch4/rest/thesaurus")
@Consumes(MediaType.TEXT_PLAIN)
@Produces(MediaType.TEXT_PLAIN)
public interface ThesaurusClient {

    @GET
    String getSynonymsFor(@PathParam("word") String word)
        throws NoSuchWordException;

    @POST
    String setSynonymsFor(@PathParam("word") String word, 
        String synonyms) throws WordAlreadyExistsException;

    @PUT
    String updateSynonymsFor(@PathParam("word") String word,
        String synonyms) throws NoSuchWordException;

    @DELETE
    boolean deleteSynonyms(@PathParam("word") String word)
        throws NoSuchWordException;

    @PATCH
    String addNewSynonymsFor(@PathParam("word") String word, 
        String newSynonyms) throws NoSuchWordException;
}
