package com.packt.microprofile.book.ch4.client;

import java.util.concurrent.CompletionStage;

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

import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;

@Path("/thesaurus/{word}")
@RegisterProvider(NoSuchWordResponseMapper.class)
@Consumes(MediaType.TEXT_PLAIN)
@Produces(MediaType.TEXT_PLAIN)
public interface ThesaurusAsyncClient {

    @GET
    CompletionStage<String> getSynonymsFor(@PathParam("word") String word);

    @POST
    CompletionStage<String> setSynonymsFor(@PathParam("word") String word, String synonyms);

    @PUT
    CompletionStage<String> updateSynonymsFor(@PathParam("word") String word,
        String synonyms);

    @DELETE
    CompletionStage<Boolean> deleteSynonyms(@PathParam("word") String word);

    @PATCH
    CompletionStage<String> addNewSynonymsFor(@PathParam("word") String word, String newSynonyms);
}
