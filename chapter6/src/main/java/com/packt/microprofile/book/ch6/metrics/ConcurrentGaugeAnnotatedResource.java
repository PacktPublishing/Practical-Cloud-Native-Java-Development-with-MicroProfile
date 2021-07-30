package com.packt.microprofile.book.ch6.metrics;

import org.eclipse.microprofile.metrics.annotation.ConcurrentGauge;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@ApplicationScoped
@Path("/concurrentGaugeAnnotatedResource")
public class ConcurrentGaugeAnnotatedResource {
    
    private final String CONCURRENTGAUGE_METRIC_NAME = "annotatedConcurrentGaugeMetric";

    @GET
    @Path("/concurrentGauge")
    public String getConcurrentGauge(){
        ExecutorService executorService = Executors.newCachedThreadPool();

        Runnable sleeper = () -> sleeper(); 

        for (int i = 0; i < 10; i++) {
            executorService.submit(sleeper);
        }

        return "Concurrent Gauge created and invoked in parallel";
    }

    @ConcurrentGauge(name = CONCURRENTGAUGE_METRIC_NAME, absolute = true)
    public void sleeper() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
