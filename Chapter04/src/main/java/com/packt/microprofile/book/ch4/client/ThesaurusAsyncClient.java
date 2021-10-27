package com.packt.microprofile.book.ch4.client;

import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.CompletionStage;

// info: This interface implements same methods as ThesaurusClient but without the exception declarations on the signature.
//       By this it can be used in by the rest client.

@Path("/{word}")
@RegisterProvider(NoSuchWordResponseMapper.class)
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
