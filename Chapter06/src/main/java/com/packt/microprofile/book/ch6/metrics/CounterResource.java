package com.packt.microprofile.book.ch6.metrics;

import org.eclipse.microprofile.metrics.Counter;
import org.eclipse.microprofile.metrics.Metadata;
import org.eclipse.microprofile.metrics.MetricID;
import org.eclipse.microprofile.metrics.MetricRegistry;
import org.eclipse.microprofile.metrics.MetricType;
import org.eclipse.microprofile.metrics.Tag;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@ApplicationScoped
@Path("/counterResource")
public class CounterResource {
    
    @Inject
    MetricRegistry metricRegistry;

    private final String COUNTER_METRIC_NAME = "counterMetric";
    private final Tag COUNTER_TAG = new Tag("metricType", "counter");

    @GET
    @Path("/counter1")
    public String getCounter1(){

        Metadata counterMetadata = Metadata.builder()
                .withName(COUNTER_METRIC_NAME)
                .withType(MetricType.COUNTER)
                .build();

        Counter counter = metricRegistry.counter(counterMetadata, COUNTER_TAG);
        counter.inc(); // increments by one

        return "A counter metric has been created and incremented "
                + "by 1, the total is now " + counter.getCount();
    }

    @GET
    @Path("/counter2")
    public String getCounter2(){
        MetricID counterMetricID = new MetricID(COUNTER_METRIC_NAME, COUNTER_TAG);
        Counter counter = metricRegistry.counter(counterMetricID);

        counter.inc(3);

        return "A counter metric was retrieve and incremented "
                + "by 3, the total  is now " + counter.getCount();
    }
}
