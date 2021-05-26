package com.packt.microprofile.book.ch6.metrics;

import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Gauge;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@ApplicationScoped
@Path("/gaugeResource")
public class GaugeResource {

    @Gauge(name="time.since.epoch", unit = MetricUnits.MILLISECONDS)
    public long getGaugeWithAnnotations() {
        return System.currentTimeMillis();
    }

    @GET
    @Path("/gauge")
    public String getGauge(){
        return "Gauge Resource";
    }
    
}
