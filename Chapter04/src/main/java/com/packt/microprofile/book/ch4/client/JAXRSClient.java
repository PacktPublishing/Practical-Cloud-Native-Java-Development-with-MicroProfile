package com.packt.microprofile.book.ch4.client;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.client.AsyncInvoker;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.packt.microprofile.book.ch4.thesaurus.NoSuchWordException;

@Path("/client/jaxrs")
public class JAXRSClient {

    private final static String BASE_URI = "http://localhost:9080/ch4/rest/thesaurus";

    @GET
    @Path("/{word}")
    public String synonymsFor(@PathParam("word") String word) throws NoSuchWordException {
        Client client = ClientBuilder.newBuilder().build();
        WebTarget target = client.target(BASE_URI).path(word);
        Builder builder = target.request(MediaType.TEXT_PLAIN);
        try (Response response = builder.get()) {
            int status = response.getStatus();
            switch (status) {
            case 200:
                return response.readEntity(String.class);
            case 404:
                throw new NoSuchWordException(word);
            default:
                throw new IllegalStateException("Unexpected response from server: " + status);
            }
        } finally {
            client.close();
        }
    }

    @GET
    @Path("/async/future/{word}")
    public String synonymsForAsyncFuture(@PathParam("word") String word)
            throws NoSuchWordException, InterruptedException, ExecutionException {
        Client client = ClientBuilder.newBuilder().build();
        WebTarget target = client.target(BASE_URI).path(word);
        Builder builder = target.request(MediaType.TEXT_PLAIN);
        AsyncInvoker invoker = builder.async();
        Future<Response> future = invoker.get();
        // do something else while waiting for the response...
        try (Response response = future.get()) {
            int status = response.getStatus();
            switch (status) {
            case 200:
                return response.readEntity(String.class);
            case 404:
                throw new NoSuchWordException(word);
            default:
                throw new IllegalStateException("Unexpected response from server: " + status);
            }
        } finally {
            client.close();
        }
    }

    @GET
    @Path("/async/callback/{words}")
    public String synonymsForAsyncCallback(@PathParam("words") String words) throws NoSuchWordException {
        StringBuffer sb = new StringBuffer();
        String[] wordsArr = words.split(",");

        CountDownLatch latch = new CountDownLatch(wordsArr.length);
        Client client = ClientBuilder.newBuilder().build();
        for (String word : wordsArr) {
            WebTarget target = client.target(BASE_URI).path(word);
            Builder builder = target.request(MediaType.TEXT_PLAIN);
            AsyncInvoker invoker = builder.async();
            invoker.get(new InvocationCallback<String>() {

                @Override
                public void completed(String response) {
                    sb.append(response + "\n");
                    latch.countDown();
                }

                @Override
                public void failed(Throwable th) {
                    th.printStackTrace();
                    latch.countDown();
                }

            });
        }
        try {
            latch.await(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
