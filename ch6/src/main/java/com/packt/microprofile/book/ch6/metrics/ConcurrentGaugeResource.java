package com.packt.microprofile.book.ch6.metrics;

import org.eclipse.microprofile.metrics.ConcurrentGauge;
import org.eclipse.microprofile.metrics.MetricRegistry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@ApplicationScoped
@Path("/concurrentGaugeResource")
public class ConcurrentGaugeResource {

    @Inject
    MetricRegistry metricRegistry;
      
    private final String CONCURRENTGAUGE_METRIC_NAME = "concurrentGaugeMetric";
    @GET
    @Path("/concurrentGauge")
    public String getConcurrentGage(){
        ExecutorService executorService = Executors.newCachedThreadPool();

          Runnable sleeper = () -> {
              ConcurrentGauge concurrentGauge = metricRegistry.concurrentGauge(CONCURRENTGAUGE_METRIC_NAME);
              concurrentGauge.inc();
              try {
                  Thread.sleep(10000);
              } catch (InterruptedException e) {
                  e.printStackTrace();
              }
              concurrentGauge.dec();
          };

          for (int i = 0; i < 10; i++) {
              executorService.submit(sleeper);
          }
        
        return "Concurrent Gauge created and invoked in parallel";
    }

}
