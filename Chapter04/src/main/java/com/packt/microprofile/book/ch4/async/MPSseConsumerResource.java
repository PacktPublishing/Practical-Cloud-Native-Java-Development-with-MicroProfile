package com.packt.microprofile.book.ch4.async;

import java.net.URI;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

@Path("/test")
public class MPSseConsumerResource {

    @GET
    @Path("/sse")
    @Produces("text/plain")
    public CompletionStage<String> getCombinedSseString() {
        CompletableFuture<String> stage = new CompletableFuture<>();
        StringBuilder sb = new StringBuilder();
        SseClient client = RestClientBuilder.newBuilder()
                                            .baseUri(URI.create("http://localhost:8080/rest"))
                                            .build(SseClient.class);
        client.receiveSSEs().subscribe(new Subscriber<String>() {

            @Override
            public void onSubscribe(Subscription s) {
                s.request(3);
            }

            @Override
            public void onNext(String s) {
                sb.append(s).append(" ");
            }

            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
            }

            @Override
            public void onComplete() {
                stage.complete(sb.toString());
            }
        });
        return stage;
    }
}
