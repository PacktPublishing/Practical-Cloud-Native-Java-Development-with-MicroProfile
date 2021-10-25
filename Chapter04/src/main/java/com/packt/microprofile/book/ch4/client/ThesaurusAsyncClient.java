package com.packt.microprofile.book.ch4.client;

import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.CompletionStage;

@RegisterProvider(NoSuchWordResponseMapper.class)
@Path("/thesaurus/async/{word}")
public interface ThesaurusAsyncClient {

    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    CompletionStage<String> getSynonymsFor(@PathParam("word") String word);

    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    CompletionStage<String> setSynonymsFor(@PathParam("word") String word, String synonyms);

    @PUT
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    CompletionStage<String> updateSynonymsFor(@PathParam("word") String word,
        String synonyms);

    @DELETE
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    CompletionStage<Boolean> deleteSynonyms(@PathParam("word") String word);

    @PATCH
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    CompletionStage<String> addNewSynonymsFor(@PathParam("word") String word, String newSynonyms);
}
