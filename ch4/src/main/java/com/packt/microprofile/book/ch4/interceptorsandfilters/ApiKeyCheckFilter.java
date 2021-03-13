package com.packt.microprofile.book.ch4.interceptorsandfilters;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

@PreMatching
//@Provider
public class ApiKeyCheckFilter implements ContainerRequestFilter {
    private static final String API_KEY_HEADER = "X-API-KEY";
    private static final int MAX_REQUESTS_PER_INTERVAL = 10;
    private static final int INTERVAL = 5;
    private static final TimeUnit INTERVAL_UNIT = TimeUnit.MINUTES;

    private final ScheduledExecutorService executor = new ScheduledThreadPoolExecutor(1);
    private final Map<String, Integer> apiInvocations = new ConcurrentHashMap<>();

    public ApiKeyCheckFilter() {
        resetCounters();
        executor.scheduleAtFixedRate(this::resetCounters, INTERVAL, INTERVAL, INTERVAL_UNIT);
    }

    private void resetCounters() {
        apiInvocations.put("abc", 0);
        apiInvocations.put("def", 0);
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String apiKey = requestContext.getHeaderString(API_KEY_HEADER);
        // Did client forget to send an API KEY header?
        if (apiKey == null) {
            requestContext.abortWith(Response.status(Status.UNAUTHORIZED).build());
            return;
        }
        // Did the client send an API KEY that we don't know about?
        Integer currentInvocations = apiInvocations.computeIfPresent(apiKey, (k, v) -> v+1);
        if (currentInvocations == null) {
            requestContext.abortWith(Response.status(Status.FORBIDDEN).build());
            return;
        }
        // Has the client exceeded it's quota of requests?
        if (currentInvocations > MAX_REQUESTS_PER_INTERVAL) {
            requestContext.abortWith(Response.status(Status.TOO_MANY_REQUESTS)
                                             .header(HttpHeaders.RETRY_AFTER, INTERVAL_UNIT.toSeconds(INTERVAL))
                                             .build());
            return;
        }
        // proceed
    }
}
