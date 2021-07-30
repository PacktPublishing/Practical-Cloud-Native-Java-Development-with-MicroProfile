package com.packt.microprofile.book.ch6.metrics;

import org.eclipse.microprofile.metrics.Histogram;
import org.eclipse.microprofile.metrics.Metadata;
import org.eclipse.microprofile.metrics.MetricRegistry;
import org.eclipse.microprofile.metrics.MetricType;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.Snapshot;

import java.util.Random;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@ApplicationScoped
@Path("/histogramResource")
public class HistogramResource {

    @Inject
    MetricRegistry metricRegistry;

    private final String HISTOGRAM_METRIC_NAME = "histogramMetric";
    @GET
    @Path("/histogram")
    public String getHistogram()  {
      Metadata histogramMetadata = Metadata.builder()
      .withName(HISTOGRAM_METRIC_NAME)
      .withUnit(MetricUnits.MILLISECONDS)
      .withDescription("This histogram tracks random millesconds")
      .withType(MetricType.HISTOGRAM).build();
      
      Histogram histogram = metricRegistry.histogram(histogramMetadata);
      
      Random random = new Random();
      for (int i = 0; i < 1000 ; i++) {
          int randomInt = random.nextInt(1000);
          histogram.update(randomInt);
      }

      int count = (int) histogram.getCount(); //returns long value of count
      Snapshot snapshot = histogram.getSnapshot(); //rest of the stats
      
       return "Histogram created/retrieved and is tracking random milleseconds";
    }

}
