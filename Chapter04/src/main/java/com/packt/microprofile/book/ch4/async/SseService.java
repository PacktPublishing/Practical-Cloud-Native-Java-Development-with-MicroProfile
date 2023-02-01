package com.packt.microprofile.book.ch4.async;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseBroadcaster;
import javax.ws.rs.sse.SseEventSink;

@Path("/sse")
public class SseService {

    @GET
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public void send3TextEvents(@Context SseEventSink sink, @Context Sse sse) {
        Executors.newSingleThreadExecutor().submit(() -> {
            try (SseEventSink sinkToClose = sink) {
                sink.send(sse.newEventBuilder()
                             .mediaType(MediaType.TEXT_PLAIN_TYPE)
                             .data("foo")
                             .name("fooEvent")
                             .id("1")
                             .build());
                Thread.sleep(500);
                sink.send(sse.newEventBuilder()
                             .mediaType(MediaType.TEXT_PLAIN_TYPE)
                             .data("bar")
                             .name("barEvent")
                             .id("2")
                             .build());
                Thread.sleep(500);
                sink.send(sse.newEventBuilder()
                             .mediaType(MediaType.TEXT_PLAIN_TYPE)
                             .data("baz")
                             .name("bazEvent")
                             .id("3")
                             .build());
            } catch (InterruptedException ex) {}
        });
    }

    static SseBroadcaster broadcaster;
    static ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    private void startBroadcasting(Sse sse) {
        if (broadcaster == null) {
            broadcaster = sse.newBroadcaster();
            executor.scheduleAtFixedRate(() -> {
                broadcaster.broadcast(sse.newEventBuilder().mediaType(MediaType.TEXT_PLAIN_TYPE).data("ping").build());
                System.out.println("ping");
            }, 5, 5, TimeUnit.SECONDS);
        }
    }

    @GET
    @Path("/broadcast")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public void broadcast(@Context SseEventSink sink, @Context Sse sse) {
        startBroadcasting(sse);
        broadcaster.register(sink);
        broadcaster.broadcast(sse.newEventBuilder().mediaType(MediaType.TEXT_PLAIN_TYPE).data("new registrant").build());
    }
}
