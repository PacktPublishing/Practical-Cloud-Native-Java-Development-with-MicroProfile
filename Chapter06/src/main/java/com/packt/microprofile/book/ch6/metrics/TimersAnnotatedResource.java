package com.packt.microprofile.book.ch6.metrics;

import org.eclipse.microprofile.metrics.annotation.SimplyTimed;
import org.eclipse.microprofile.metrics.annotation.Timed;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@ApplicationScoped
@Path("/timersAnnotatedResource")
public class TimersAnnotatedResource {

    private final String ANNOTATED_TIMER_METRIC_NAME = "annotatedTimerMetric";
    private final String ANNOTATED_SIMPLETIMER_METRIC_NAME = "annotatedSimpleTimerMetric";

    @GET
    @Path("/timers")
    @Timed(name=ANNOTATED_TIMER_METRIC_NAME, absolute=true)
    @SimplyTimed(name= ANNOTATED_SIMPLETIMER_METRIC_NAME, absolute=true)
    public String getTimerWithAnnotations() {
        //some business logic to time
        return "@Timed and @SimplyTimed";
    }
    

}
