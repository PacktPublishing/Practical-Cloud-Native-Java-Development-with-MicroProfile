package com.packt.microprofile.book.ch6.metrics;

import org.eclipse.microprofile.metrics.Counter;
import org.eclipse.microprofile.metrics.Metadata;
import org.eclipse.microprofile.metrics.MetricID;
import org.eclipse.microprofile.metrics.MetricRegistry;
import org.eclipse.microprofile.metrics.MetricType;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.Tag;
import org.eclipse.microprofile.metrics.annotation.Metric;
import org.eclipse.microprofile.metrics.annotation.RegistryType;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@ApplicationScoped
@Path("/metricsResource")
public class MetricsResource {

    @Inject
    @RegistryType(type = MetricRegistry.Type.APPLICATION)
    MetricRegistry metricRegistry;

    @GET
    @Path("/")
    public String getResource() {
        return "Welcome to the Metrics playgorund";
    }

    /**
     * Sample Metadata, Tag and MetricID creation.
     */
    public void metadataTagMetricIDExample() {
        String metricName = "myMetric";
        Metadata metadata = Metadata.builder().withName(metricName).withType(MetricType.INVALID)
                .withDisplayName("Human readable display name")
                .withDescription("This metadata example" + " demonstrates how to create a" + " Metadata object")
                .withUnit(MetricUnits.NONE).build();

        Tag tag = new Tag("tagKey", "tagValue");
        Tag anotherTag = new Tag("anotherTag", "tagValue");

        MetricID metricID = new MetricID(metricName, tag, anotherTag);
    }

    /*
     * Sample injections using the @Metrics annotation
     */

    @Inject
    @Metric(name = "fieldInjectedCounter")
    Counter fieldInjectedCounter;

    Counter parameterInjectedCounter;

    @Inject
    public void setCounterMetric(@Metric(name = "parameterInjectedCounter") Counter parameterInjectedCounter) {
        this.parameterInjectedCounter = parameterInjectedCounter;
    }

}
