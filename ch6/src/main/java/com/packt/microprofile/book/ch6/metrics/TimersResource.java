package com.packt.microprofile.book.ch6.metrics;

import org.eclipse.microprofile.metrics.MetricRegistry;
import org.eclipse.microprofile.metrics.SimpleTimer;
import org.eclipse.microprofile.metrics.Timer;

import java.util.concurrent.Callable;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@ApplicationScoped
@Path("/timersResource")
public class TimersResource {

    @Inject
    MetricRegistry metricRegistry;

    private final String TIMER_METRIC_NAME = "timerMetric";
    private final String SIMPLETIMER_METRIC_NAME = "simpleTimerMetric";
    
    @GET
    @Path("/timer")
    public String getTimer() {

        Timer timer = metricRegistry.timer(TIMER_METRIC_NAME);
        Timer.Context timerContext = timer.time();
        timerContext.close();

        Runnable runnableTimer = () -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        // Time a Runnable
        timer.time(runnableTimer);
        return "Timer created/retrieved and recorded total elapsed time of " + timer.getElapsedTime();
    }

    @GET
    @Path("/simpleTimer")
    public String getSimpleTimer() throws Exception {

        SimpleTimer simpleTimer = metricRegistry.simpleTimer(SIMPLETIMER_METRIC_NAME);
        SimpleTimer.Context simpleTimerContext = simpleTimer.time();
        simpleTimerContext.close();

        // Time a Callable
        Callable<String> callable = () -> {
            Thread.sleep(2000);
            return "Finished Callable";
        };
        simpleTimer.time(callable);

        return "SimpleTimer created/retrieved and recorded total elapsed time of " + simpleTimer.getElapsedTime();
    }
}
