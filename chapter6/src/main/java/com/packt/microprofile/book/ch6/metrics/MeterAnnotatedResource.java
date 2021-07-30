package com.packt.microprofile.book.ch6.metrics;

import org.eclipse.microprofile.metrics.annotation.Metered;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@ApplicationScoped
@Path("/meterAnnotatedResource")
public class MeterAnnotatedResource {

    private final String METER_METRIC_NAME = "annotatedMeterMetric";

    @GET
    @Path("/meter")
    @Metered(name=METER_METRIC_NAME, absolute=true, tags= {"metricType=meter"})
    public String getMeterWithAnnotations() {
        return "Meter created/retrieved and marked by 1 with annotations";
    }

}
