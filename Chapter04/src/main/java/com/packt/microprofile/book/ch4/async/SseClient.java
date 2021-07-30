package com.packt.microprofile.book.ch4.async;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.reactivestreams.Publisher;

@Path("/sse")
@Produces(MediaType.SERVER_SENT_EVENTS)
public interface SseClient {

    @GET
    Publisher<String> receiveSSEs();
}
