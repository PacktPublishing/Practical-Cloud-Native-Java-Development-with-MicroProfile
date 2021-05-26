package com.packt.microprofile.book.ch6.health;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;
import org.eclipse.microprofile.health.Readiness;

@ApplicationScoped
public class CDIMethodProducerChecks {

    @Produces
    @Liveness
    HealthCheck livenessCDIMethodProducer() {
        return () -> HealthCheckResponse.named("cdiMemoryUsage").status(getMemUsage() < 0.9).build();
    }

    @Produces
    @Readiness
    HealthCheck readinessCDIMethodProducer() {
        return () -> HealthCheckResponse.named("cdiCpuUsage").status(getCpuUsage() < 0.9).build();
    }

    public Double getMemUsage() {
        return Utility.getMemUsage();
    }

    public Double getCpuUsage() {
        return Utility.getCpuUsage();
    }
}
