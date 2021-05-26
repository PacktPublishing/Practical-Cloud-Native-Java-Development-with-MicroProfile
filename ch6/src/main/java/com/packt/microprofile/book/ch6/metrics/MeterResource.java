package com.packt.microprofile.book.ch6.metrics;

import org.eclipse.microprofile.metrics.Meter;
import org.eclipse.microprofile.metrics.MetricID;
import org.eclipse.microprofile.metrics.MetricRegistry;
import org.eclipse.microprofile.metrics.Tag;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

@ApplicationScoped
@Path("/meterResource")
public class MeterResource {

    @Inject
    MetricRegistry metricRegistry;

    private final String METER_METRIC_NAME = "meterMetric";
    private final Tag METER_TAG = new Tag("metricType", "meter");

    @GET
    @Path("/meter")
    public String getMeter() throws Exception {
        Meter meter = metricRegistry.meter(METER_METRIC_NAME, METER_TAG);
        meter.mark();
        return "Meter created/retrieved and marked by 1";
    }

    @GET
    @Path("/meter2")
    public String getMeter2(@QueryParam("value") @DefaultValue("1") int value) throws Exception {
        MetricID meterMetricID = new MetricID(METER_METRIC_NAME, METER_TAG);
        Meter meter = metricRegistry.meter(meterMetricID);
        meter.mark(value);
        return "Meter created/retrieved and marked by " + value;
    }
}